package com.example.demo.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Attempt")
public class Attempt {

    @Id
    private UUID id;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private int cowsNumber;

    @Column(nullable = false)
    private int bullsNumber;

    @JoinColumn(name = "player")
    @ManyToOne
    private Game game;

    public Attempt() {
        this.id=UUID.randomUUID();
    }

    public Attempt(int number, int cowsNumber, int bullsNumber, Game game) {
        this.id=UUID.randomUUID();
        this.number = number;
        this.cowsNumber = cowsNumber;
        this.bullsNumber = bullsNumber;
        this.game = game;
    }

    public Attempt(UUID id, int number, int cowsNumber, int bullsNumber, Game game) {
        this.id=UUID.randomUUID();
        this.id = id;
        this.number = number;
        this.cowsNumber = cowsNumber;
        this.bullsNumber = bullsNumber;
        this.game = game;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCowsNumber() {
        return cowsNumber;
    }

    public void setCowsNumber(int cowsNumber) {
        this.cowsNumber = cowsNumber;
    }

    public int getBullsNumber() {
        return bullsNumber;
    }

    public void setBullsNumber(int bullsNumber) {
        this.bullsNumber = bullsNumber;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "id=" + id +
                ", number=" + number +
                ", cowsNumber=" + cowsNumber +
                ", bullsNumber=" + bullsNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attempt attempt = (Attempt) o;
        return number == attempt.number && cowsNumber == attempt.cowsNumber && bullsNumber == attempt.bullsNumber && Objects.equals(id, attempt.id) && Objects.equals(game, attempt.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, cowsNumber, bullsNumber, game);
    }
}
