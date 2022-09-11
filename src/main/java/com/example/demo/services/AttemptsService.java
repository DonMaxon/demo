package com.example.demo.services;

import com.example.demo.entity.Attempt;
import com.example.demo.entity.Game;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.AttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.List;
import java.util.UUID;

@Service
public class AttemptsService {
    private AttemptsRepository attemptsRepository;

    @Autowired
    public AttemptsService(AttemptsRepository attemptsRepository){
        this.attemptsRepository=attemptsRepository;
    }

    public void save(Attempt attempt){
        attemptsRepository.save(attempt);
    }

    public Attempt findById(UUID id){
        return attemptsRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public List<Attempt> getAll(){
        return (List<Attempt>) attemptsRepository.findAll();
    }
}
