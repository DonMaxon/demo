package com.example.demo.controllers;

import com.example.demo.entity.Game;
import com.example.demo.entity.Player;
import com.example.demo.services.GameService;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    //TODO: swap hidden num in game
    public String newGame(@RequestParam("player") UUID uuid, Model model){
        Player player = playerService.findById(uuid);
        model.addAttribute("player", player);
        MutableInt value = new MutableInt();
        model.addAttribute("value", value);
        Game game = player.findNotEndedGame();
        if (game == null) {
            game = new Game(UUID.randomUUID(), 7328, 0, 0, 0, playerService.findById(uuid));
            //Game game = new Game(0, 0, 0, playerService.findById(uuid));
            gameService.save(game);
        }
        model.addAttribute("game", game);
        return "game";
    }

    @PostMapping("/game")
    public String game(@RequestParam("player") UUID uuid,
                       @ModelAttribute MutableInt value,  Model model){
        Player player = playerService.findById(uuid);
        Game game = player.findNotEndedGame();
        model.addAttribute("player", player);
        game.setAttemptsNumber(game.getAttemptsNumber()+1);
        if (value.getValue()==game.getHiddenNumber()){
            game.setBullsNumber(4);
            game.setCowsNumber(0);
            gameService.save(game);
            player.setRating(player.newScore());
            playerService.save(player);
            return "game_over";
        }
        else{
            if (value.value>999 && value.value<10000) {
                gameService.save(game);
                Pair<Integer, Integer> numOfBullsAndCows = game.compareAnswerWithResult(game.getHiddenNumber(), value.value);
                game.setCowsNumber(numOfBullsAndCows.getSecond());
                game.setBullsNumber(numOfBullsAndCows.getFirst());
            }
            model.addAttribute("game", game);
            model.addAttribute("player", player);
            model.addAttribute("value", value);
        }
        model.addAttribute("value", value);
        return "game";
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
