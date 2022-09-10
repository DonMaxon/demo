package com.example.demo.services;

import com.example.demo.entity.Player;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository=playerRepository;
    }

    public void save(Player player){
        playerRepository.save(player);
    }

    public Player findById(UUID id){
        return playerRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Player> getAll(){
        return (List<Player>) playerRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player = playerRepository.findByLogin(username);

        return player;
    }
}
