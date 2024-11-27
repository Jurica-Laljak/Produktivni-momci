package hr.unizg.fer.ticket4ticket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestClient;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    @Value("${frontend.url}")
    private String frontendUrl;

    //Every user can access the login page -> registry.requestMatchers("/login").permitAll()
    //If they aren't authenticated they will always be redirected to the /login -> response.sendRedirect("/login");
    //For now any authenticated user can go to ANY path ->  registry.anyRequest().authenticated()
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection for development

            .authorizeHttpRequests(registry -> {

                registry.requestMatchers("/", "/login**", "/oauth2/**").permitAll();//the login path is accessible without any authentication
                registry.requestMatchers("/api/oglasi/list/**").permitAll(); // the list of oglasi is accessible without any authentication
                registry.requestMatchers("/api/oglasi/*/izvodaci").permitAll(); // the list of Izvodac for an Oglas is accessible without any authentication
                registry.requestMatchers("/api/ulaznice/*").permitAll(); // information on Ulaznica is accessible without any authentication (for example getting an Ulaznica by Id)
                registry.requestMatchers("/api/zanrovi").permitAll(); // the list of all Zanr-s is accessible without any authentication
                registry.requestMatchers("/api/zanrovi/*").permitAll(); // getting Zanr by Id is accessible without any authentication
                registry.requestMatchers("/api/preference/oglasi/filter").permitAll(); // filtering (searching) for Oglasi is accessible without any authentication
                registry.anyRequest().authenticated(); //any other path requires authentication

            })
            .oauth2Login(oauth2login-> {
                oauth2login
                        .defaultSuccessUrl("/api/korisnici/profileSetupCheck", true); // Redirect to /api/korisnici/profileSetupCheck after successful login
            })
//                this part redirects the user to the login page if they don't have access to the other url
                .exceptionHandling(exceptionHandling ->
                {exceptionHandling.authenticationEntryPoint((request, response, authException) -> response.setStatus(200));})
                .build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl, "https://ticket4ticket-backend.onrender.com")); // Add your frontend URL
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "Access-Control-Allow-Methods"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        configuration.setAllowCredentials(true); // Enable for cookies, sessions

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply globally
        source.registerCorsConfiguration("/oauth2/**", configuration); // Allow OAuth2 paths
        return source;
    }
}
