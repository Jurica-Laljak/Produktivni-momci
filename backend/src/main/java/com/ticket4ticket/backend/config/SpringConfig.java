package com.ticket4ticket.backend.config;


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
            //.csrf(csrf -> csrf.disable()) // Disable CSRF protection for testing 1) POSTMAN
            .authorizeHttpRequests(registry -> {
            registry.requestMatchers("/login", "/oauth2/**").permitAll(); //the home path is accessible without any authentification 2) NORMAL
            registry.anyRequest().authenticated(); //any other path requires authentication 2) NORMAL

            //registry.anyRequest().permitAll(); // Allow all requests without authentication for testing 1) POSTMAN


            })
            .oauth2Login(oauth2login-> {
                oauth2login
                        .loginPage("/login")
                        .defaultSuccessUrl("/api/korisnici/profile", true); // Redirect to /api/korisnici/profile after successful login
            })
                //this part redirects the user to the login page if they dont have acess to the other url
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                        response.sendRedirect("/login");
        });
        }).build();

    }
}
