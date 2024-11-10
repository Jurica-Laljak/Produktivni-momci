package hr.unizg.fer.ticket4ticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringConfig {

    //Every user can access the login page -> registry.requestMatchers("/login").permitAll()
    //If they aren't authenticated they will always be redirected to the /login -> response.sendRedirect("/login");
    //For now any authenticated user can go to ANY path ->  registry.anyRequest().authenticated()
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection for development
            .authorizeHttpRequests(registry -> {

                registry.requestMatchers("/login", "/oauth2/**").permitAll();//the login path is accessible without any authentication
                registry.requestMatchers("/api/oglasi/list/**").permitAll(); // the list of oglasi is accessible without any authentication
                registry.requestMatchers("/api/oglasi/*/izvodaci").permitAll(); // the list of Izvodac for an Oglas is accessible without any authentication
                registry.requestMatchers("/api/ulaznice/*").permitAll(); // information on Ulaznica is accessible without any authentication (for example getting an Ulaznica by Id)
                registry.requestMatchers("/api/zanrovi").permitAll(); // the list of all Zanr-s is accessible without any authentication
                registry.requestMatchers("/api/zanrovi/*").permitAll(); // getting Zanr by Id is accessible without any authentication
                registry.requestMatchers("/api/preference/oglasi/filter").permitAll(); // filtering (searching) for Oglasi is accessible without any authentication
                registry.anyRequest().authenticated(); //any other path requires authentication
                //registry.anyRequest().permitAll(); // Allow all requests without authentication for testing COMMENT OUT!

            })
            .oauth2Login(oauth2login-> {
                oauth2login
                        .loginPage("/login")
                        .defaultSuccessUrl("/api/korisnici/profileSetupCheck", true); // Redirect to /api/korisnici/profileSetupCheck after successful login
            })
                //this part redirects the user to the login page if they don't have access to the other url
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                        response.sendRedirect("/login");
        });
        }).build();

    }
}
