package com.ticket4ticket.backend.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


//Manages the display of pages (e.g., serving HTML views like home, profile, error pages).
//@Controller: This is for handling views, and when you return a string, it expects that string to be the name of a view (e.g., an HTML or JSP page) to be rendered
@Controller
public class LoginController {



    //The OAuth2 token gives the details of the logged in user, and we will pass that into the view model object
    //Model is a Spring framework interface used to pass data from the controller to the view.
    //The Model is populated with user information obtained from the OAuth2AuthenticationToken
    //@GetMapping("/profile")
    //public String profile(OAuth2AuthenticationToken token, Model model){
      //  model.addAttribute("name", token.getPrincipal().getAttribute("name"));
       // model.addAttribute("email", token.getPrincipal().getAttribute("email"));
        //model.addAttribute("photo", token.getPrincipal().getAttribute("picture"));

        //When the controller returns "userProfile", it passes the Model data to the userProfile view, making the attributes available there.
      //  return "userProfile";
    //}

    @GetMapping("/login")
    public String login(OAuth2AuthenticationToken token, Model model){
        return "loginPage";
    }

}
