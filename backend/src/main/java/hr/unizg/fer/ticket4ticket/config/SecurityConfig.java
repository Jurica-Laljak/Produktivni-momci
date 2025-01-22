package hr.unizg.fer.ticket4ticket.config;

import hr.unizg.fer.ticket4ticket.security.RestAuthenticationEntryPoint;
import hr.unizg.fer.ticket4ticket.security.filtter.JwtAuthenticationFilter;
import hr.unizg.fer.ticket4ticket.security.handler.CustomAccessDeniedHandler;
import hr.unizg.fer.ticket4ticket.security.handler.OAuth2LoginSuccessHandler;
import hr.unizg.fer.ticket4ticket.security.oauth2.HttpCookieOAuth2AutherizationRequestRepository;
import hr.unizg.fer.ticket4ticket.service.AdministratorService;
import hr.unizg.fer.ticket4ticket.service.KorisnikService;
import hr.unizg.fer.ticket4ticket.service.RoleService;
import hr.unizg.fer.ticket4ticket.service.impl.JwtTokenServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final JwtTokenServiceImpl jwtTokenService;
    private final KorisnikService korisnikService;
    private final RoleService roleService;
    private final AdministratorService administratorService;
    private final HttpCookieOAuth2AutherizationRequestRepository httpCookieOAuth2AutherizationRequestRepository;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection for development

            .authorizeHttpRequests(registry -> {

                registry.requestMatchers("/*", "/assets/*", "/data/*", "/login**", "/oauth2/**").permitAll();//the login path is accessible without any authentication
                registry.requestMatchers("/api/oglasi/list/**").permitAll(); // the list of oglasi is accessible without any authentication
                registry.requestMatchers("/api/oglasi/*/izvodaci").permitAll(); // the list of Izvodac for an Oglas is accessible without any authentication
                registry.requestMatchers("/api/ulaznice/*").permitAll(); // information on Ulaznica is accessible without any authentication (for example getting an Ulaznica by Id)
                registry.requestMatchers("/api/zanrovi").permitAll(); // the list of all Zanr-s is accessible without any authentication
                registry.requestMatchers("/api/zanrovi/*").permitAll(); // getting Zanr by Id is accessible without any authentication
                registry.requestMatchers("/api/preference/oglasi/filter").permitAll(); // filtering (searching) for Oglasi is accessible without any authentication
                registry.anyRequest().authenticated(); //any other path requires authentication
            })
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authEndpoint ->
                                authEndpoint
                                        .authorizationRequestRepository(httpCookieOAuth2AutherizationRequestRepository))
                        .redirectionEndpoint(redirect ->
                                redirect
                                        .baseUri("/login/oauth2/code/google"))
                        .successHandler(new OAuth2LoginSuccessHandler(jwtTokenService, korisnikService, roleService, administratorService, httpCookieOAuth2AutherizationRequestRepository))
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(jwtTokenService.getSecretKey()).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
