package com.example.demo.repositories;

import com.example.demo.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends CrudRepository<Player, UUID> {

    Player findByLogin(String login);

}
