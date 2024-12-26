package hr.unizg.fer.ticket4ticket.security.handler;

import hr.unizg.fer.ticket4ticket.entity.Korisnik;
import hr.unizg.fer.ticket4ticket.repository.KorisnikRepository;
import hr.unizg.fer.ticket4ticket.security.oauth2.HttpCookieOAuth2AutherizationRequestRepository;
import hr.unizg.fer.ticket4ticket.service.impl.JwtTokenServiceImpl;
import hr.unizg.fer.ticket4ticket.utils.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String REDIRECT_PARAM_COOKIE_NAME = "redirect_uri";

    private final JwtTokenServiceImpl jwtTokenService;

    private final KorisnikRepository userInfoRepository;
    private final HttpCookieOAuth2AutherizationRequestRepository httpCookieOAuth2AutherizationRequestRepository;

    public OAuth2LoginSuccessHandler(JwtTokenServiceImpl jwtTokenService, KorisnikRepository userInfoRepository, HttpCookieOAuth2AutherizationRequestRepository httpCookieOAuth2AutherizationRequestRepository) {
        this.jwtTokenService = jwtTokenService;
        this.userInfoRepository = userInfoRepository;
        this.httpCookieOAuth2AutherizationRequestRepository = httpCookieOAuth2AutherizationRequestRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        String googleId = user.getAttribute("sub");

        String token = jwtTokenService.createTokenFromUsername(googleId);

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
        Korisnik existingUser = userInfoRepository.findByGoogleId(googleId);

        if (existingUser != null) {
            return "/UserHome";
        }

        // User does not exist, populate KorisnikDto with user information
        Korisnik noviKorisnik = new Korisnik();
        noviKorisnik.setImeKorisnika(name);
        noviKorisnik.setPrezimeKorisnika(surname);
        noviKorisnik.setEmailKorisnika(email);
        noviKorisnik.setFotoKorisnika(photo);
        noviKorisnik.setGoogleId(googleId);

        // Create a new user using createKorisnik method
        userInfoRepository.save(noviKorisnik);

        // Redirect to ChooseGenres after successful creation of the new user
        return "/ChooseGenres";
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
