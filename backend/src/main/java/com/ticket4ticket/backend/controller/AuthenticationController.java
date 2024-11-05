package com.ticket4ticket.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//Manages authentication and security-related tasks (e.g., login, logout, OAuth2 callbacks, token generation)
//@RestController: This is a specialization of @Controller where the response body is directly returned as JSON or plain text
@RestController
public class AuthenticationController {

    //Principal -> represents the currently authenticated user in your application
    //This will return all of the user info as a JSON
    @RequestMapping("/user")
    public Principal user(Principal user){
        return user;
    }
}
