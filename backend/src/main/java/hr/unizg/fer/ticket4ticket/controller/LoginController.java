package hr.unizg.fer.ticket4ticket.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//Manages the display of pages (e.g., serving HTML views like home, profile, error pages).
//@Controller: This is for handling views, and when you return a string, it expects that string to be the name of a view (e.g., an HTML or JSP page) to be rendered
@Controller
public class LoginController {

    //testing login page
    @GetMapping("/login")
    public String login(OAuth2AuthenticationToken token, Model model){
        return "loginPage";
    }

}
