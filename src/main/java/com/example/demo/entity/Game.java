package com.example.demo.entity;

import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Game")
public class Game {

    @Id
    private UUID id;

    @Column(nullable = false)
    private int hiddenNumber;

    @Column(nullable = false)
    private boolean isOver;

    @JoinColumn(name = "player")
    @ManyToOne
    private Player player;

    @OneToMany(mappedBy = "game",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attempt> attempts;

    public Game() {
        this.id=UUID.randomUUID();
        isOver=false;
        attempts=new ArrayList<>(0);
    }

    public Game(Player player) {
        this.id=UUID.randomUUID();
        this.hiddenNumber = generateHiddenNumber();
        this.isOver=false;
        this.player = player;
        attempts=new ArrayList<>(0);
    }

    public Game(UUID id, int hiddenNumber, Player player) {
        this.id = id;
        this.hiddenNumber = hiddenNumber;
        this.isOver=false;
        this.player = player;
        attempts=new ArrayList<>(0);
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
        return
                attempts.size();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Attempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<Attempt> attempts) {
        this.attempts = attempts;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public int getBullsNumber(){
        if (attempts.size()>0) {
            return attempts.get(attempts.size()-1).getBullsNumber();
        }
        else{
            return 0;
        }
    }

    public int getCowsNumber(){
        if (attempts.size()>0) {
            return attempts.get(attempts.size()-1).getCowsNumber();
        }
        else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", hiddenNumber=" + hiddenNumber +
                ", isOver=" + isOver +
                ", player=" + player +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return hiddenNumber == game.hiddenNumber && isOver == game.isOver && Objects.equals(id, game.id) && Objects.equals(player, game.player) && Objects.equals(attempts, game.attempts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hiddenNumber, isOver, player, attempts);
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

    public void addAttempt(Attempt attempt){
        attempts.add(attempt);
    }
}
