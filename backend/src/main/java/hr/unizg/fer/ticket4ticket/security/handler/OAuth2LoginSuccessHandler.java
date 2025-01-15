package hr.unizg.fer.ticket4ticket.security.handler;

import hr.unizg.fer.ticket4ticket.dto.KorisnikDto;
import hr.unizg.fer.ticket4ticket.security.oauth2.HttpCookieOAuth2AutherizationRequestRepository;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import hr.unizg.fer.ticket4ticket.service.RoleService;
import hr.unizg.fer.ticket4ticket.service.impl.JwtTokenServiceImpl;
import hr.unizg.fer.ticket4ticket.utils.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String REDIRECT_PARAM_COOKIE_NAME = "redirect_uri";

    private final JwtTokenServiceImpl jwtTokenService;

    private final KorisnikService korisnikService;
    private final RoleService roleService;
    private final HttpCookieOAuth2AutherizationRequestRepository httpCookieOAuth2AutherizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        String googleId = user.getAttribute("sub");

        KorisnikDto existingUser = korisnikService.findKorisnikByGoogleId(googleId);

        List<String> roles = new ArrayList<>();

        if (existingUser != null) {
            for (Long role : existingUser.getRoleIds()) {
                roles.add(roleService.getRoleById(role).getRole());
            }
        }
        else  {
            roles.add("ROLE_USER");
        }

        String token = jwtTokenService.createToken(googleId, roles);

        String targetUrl = UriComponentsBuilder.fromUriString(determineTargetUrl(request, response, authentication) + determineProfileStateAndReturnAddress(user))
                .queryParam("token", token).build().toUriString();

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        
        this.setDefaultTargetUrl(targetUrl);
        this.setAlwaysUseDefaultTargetUrl(true);

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AutherizationRequestRepository.removeAuthorizationRequestCookie(request, response);
    }

    public String determineProfileStateAndReturnAddress(OAuth2User user) {

        // Retrieve user details from OAuth token
        String googleId = user.getAttribute("sub");
        String name = user.getAttribute("given_name");
        String surname = user.getAttribute("family_name");
        String email = user.getAttribute("email");
        String photo = user.getAttribute("picture");

        // Check if the Korisnik exists, and if not returns an empty KorisnikDto
        KorisnikDto existingUser = korisnikService.findKorisnikByGoogleId(googleId);

        if (existingUser != null) {
            return "/";
        }

        // User does not exist, populate KorisnikDto with user information
        KorisnikDto noviKorisnik = KorisnikDto.builder()
                .imeKorisnika(name)
                .prezimeKorisnika(surname)
                .emailKorisnika(email)
                .fotoKorisnika(photo)
                .googleId(googleId)
                .roleIds(Set.of(roleService.getRoleByName("ROLE_USER").getIdRole()))
                .prikazujObavijesti(true)
                .build();

        // Create a new user using createKorisnik method
        korisnikService.createKorisnik(noviKorisnik);

        // Redirect to ChooseGenres after successful creation of the new user
        return "/chooseGenres";
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            try {
                throw new BadRequestException("Unauthorized Redirect URI");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }

        return redirectUri.orElse(getDefaultTargetUrl());

    }

    private boolean isAuthorizedRedirectUri(String url) {
        URI clientRedirectUri = URI.create(url);

        return true;
    }
}
