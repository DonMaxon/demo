package com.example.demo.entity;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Player")
public class Player implements UserDetails, CredentialsContainer {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private double rating;

    @OneToMany(mappedBy = "player",  cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Game> games;

    public Player() {
        games = new ArrayList<>();
    }

    public Player(UUID id) {
        this.id = id;
        games = new ArrayList<>();
        rating = Double.POSITIVE_INFINITY;
    }

    public Player(UUID id, String login, String password, List<Game> games) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.games = games;
        rating = Double.POSITIVE_INFINITY;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(login, player.login) && Objects.equals(password, player.password) && Objects.equals(games, player.games);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, games);
    }

    public double newScore(){
        double numOfAttempts = 0;
        for (int i =0; i< games.size(); ++i){
            numOfAttempts+=games.get(i).getAttemptsNumber();
        }
        return numOfAttempts/games.size();
    }

    public Game findNotEndedGame(){
        for (int i =0; i < games.size(); ++i){
            if (games.get(i).getBullsNumber()!=4){
                return games.get(i);
            }
        }
        return null;
    }
}
