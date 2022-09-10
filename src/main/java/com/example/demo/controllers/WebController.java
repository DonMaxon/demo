package com.example.demo.controllers;

import com.example.demo.entity.Game;
import com.example.demo.entity.Player;
import com.example.demo.services.GameService;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.ArrayUtils;


import java.util.*;

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

    @GetMapping("sign_up")
    public String newClient(Model model) {
        model.addAttribute("player", new Player());
        return "sign_up";
    }
    @PostMapping("sign_up")
    public String newClientCreate(Model model, @ModelAttribute Player player) {
        if (playerService.loadUserByUsername(player.getUsername())!=null){
            return "sign_up";
        }
        player.setId(UUID.randomUUID());
        player.setGames(new ArrayList<>());
        playerService.save(player);
        return "login";
    }

    @GetMapping("/list_of_games")
    public String newPlayer(Model model){
        Player player = (Player)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("player", player);
        return "list_of_games";
    }

    @GetMapping("/rating")
    public String rating(@RequestParam("player") UUID uuid, Model model){
        Player player = playerService.findById(uuid);
        model.addAttribute("player", player);
        List<Player> players =  playerService.getAll();
        Collections.sort(players, Comparator.comparingDouble(Player::getRating));
        model.addAttribute("players", players);
        return "rating";
    }

    @GetMapping("/new_game")
    public String newGame(@RequestParam("player") UUID uuid, Model model){
        Player player = playerService.findById(uuid);
        model.addAttribute("player", player);
        MutableInt value = new MutableInt();
        model.addAttribute("value", value);
        Game game = new Game(getHiddenNumber(), 0, 0, 0, playerService.findById(uuid));
        model.addAttribute("game", game);
        return "game";
    }

    @PostMapping("/new_game")
    public String game(@RequestParam("player") UUID uuid, Model model){
        Player player = playerService.findById(uuid);
        model.addAttribute("player", player);
        MutableInt value = new MutableInt();
        model.addAttribute("value", value);
        return "game";
    }

    private int getHiddenNumber(){
        Integer[] arrayForNumbers=new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<Integer> arrayOfNumbers = new ArrayList(Arrays.asList(arrayForNumbers));
        int result = 0;
        int index = 1+(int)(Math.random()*((9-1)+1));
        result=arrayOfNumbers.get(index);
        arrayOfNumbers.remove(index);
        for (int i =0; i < 3; ++i) {
            index = (int) (Math.random() * (9-i));
            result=result*10+arrayOfNumbers.get(index);
            arrayOfNumbers.remove(index);
        }
        return result;
    }

    private class MutableInt{
        private int value;

        public MutableInt(){
            this.value = 0;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }


}
