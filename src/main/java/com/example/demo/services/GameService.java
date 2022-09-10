package com.example.demo.services;

import com.example.demo.entity.Game;
import com.example.demo.entity.Player;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository){
        this.gameRepository=gameRepository;
    }

    public void save(Game game){
        gameRepository.save(game);
    }

    public Game findById(UUID id){
        return gameRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Game> getAll(){
        return (List<Game>) gameRepository.findAll();
    }

}
