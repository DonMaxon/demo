package com.example.demo.entity;

import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Game")
public class Game {

    @Id
    private UUID id;

    @Column(nullable = false)
    private int hiddenNumber;

    @Column(nullable = false)
    private int attemptsNumber;

    @Column(nullable = false)
    private int cowsNumber;

    @Column(nullable = false)
    private int bullsNumber;

    @JoinColumn(name = "player")
    @ManyToOne
    private Player player;

    public Game() {
    }

    public Game(int attemptsNumber, int cowsNumber, int bullsNumber, Player player) {
        this.id=UUID.randomUUID();
        this.hiddenNumber = generateHiddenNumber();
        this.attemptsNumber = attemptsNumber;
        this.cowsNumber = cowsNumber;
        this.bullsNumber = bullsNumber;
        this.player = player;
    }

    public Game(UUID id, int hiddenNumber, int attemptsNumber, int cowsNumber,
                int bullsNumber, Player player) {
        this.id = id;
        this.hiddenNumber = hiddenNumber;
        this.attemptsNumber = attemptsNumber;
        this.cowsNumber = cowsNumber;
        this.bullsNumber = bullsNumber;
        this.player = player;
    }

    public Game(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getHiddenNumber() {
        return hiddenNumber;
    }

    public void setHiddenNumber(int hiddenNumber) {
        this.hiddenNumber = hiddenNumber;
    }

    public int getAttemptsNumber() {
        return attemptsNumber;
    }

    public void setAttemptsNumber(int attemptsNumber) {
        this.attemptsNumber = attemptsNumber;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", hiddenNumber=" + hiddenNumber +
                ", attemptsNumber=" + attemptsNumber +
                ", cowsNumber=" + cowsNumber +
                ", bullsNumber=" + bullsNumber +
                ", player=" + player.getLogin() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return hiddenNumber == game.hiddenNumber && attemptsNumber == game.attemptsNumber && cowsNumber == game.cowsNumber && bullsNumber == game.bullsNumber && Objects.equals(id, game.id) && Objects.equals(player, game.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hiddenNumber, attemptsNumber, cowsNumber, bullsNumber, player);
    }

    private int generateHiddenNumber(){
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

    public Pair<Integer, Integer> compareAnswerWithResult(int result, int answer){
        String res = Integer.toString(result);
        String ans = Integer.toString(answer);
        Integer bulls = 0;
        Integer cows = 0;
        for (int i =0; i < 4; ++i){
            if (res.indexOf(ans.charAt(i))!=-1){
                cows++;
            }
        }
        for (int i =0; i < 4; ++i){
            if (res.charAt(i)==ans.charAt(i)){
                cows--;
                bulls++;
            }
        }
        return Pair.of(bulls, cows);
    }
}
