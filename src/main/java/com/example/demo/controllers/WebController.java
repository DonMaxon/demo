package com.example.demo.controllers;

import com.example.demo.services.GameService;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    GameService gameService;

    @Autowired
    PlayerService playerService;

    @GetMapping("/")
    public String login(Model model) {
        return "login";
    }

    /*@GetMapping("/login")
    public String signIn(Model model) {
        return "redirect:/list_of_games";
    }*/

    @GetMapping("/sign_up")
    public String signUp(Model model) {
        return "sign_up";
    }



    @GetMapping("/list_of_games")
    public String newPlayer(Model model){
        return "list_of_games";
    }
}
