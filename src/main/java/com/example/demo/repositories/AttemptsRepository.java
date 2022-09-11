package com.example.demo.repositories;

import com.example.demo.entity.Attempt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AttemptsRepository extends CrudRepository<Attempt, UUID> {
}
