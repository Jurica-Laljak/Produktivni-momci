package hr.unizg.fer.ticket4ticket.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

//Manages authentication and security-related tasks (e.g., login, logout, OAuth2 callbacks, token generation)
//@RestController: This is a specialization of @Controller where the response body is directly returned as JSON or plain text
@RestController
@CrossOrigin
public class AuthenticationController {

    //Principal -> represents the currently authenticated user in your application
    //This will return all user info as a JSON, used for development
    @RequestMapping("/user")
    public Principal user(Principal user){
        return user;
    }
}
