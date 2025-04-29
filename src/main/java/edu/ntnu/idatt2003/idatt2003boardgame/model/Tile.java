package edu.ntnu.idatt2003.idatt2003boardgame.model;

import edu.ntnu.idatt2003.idatt2003boardgame.model.Player;
import java.util.ArrayList;
import java.util.List;

import edu.ntnu.idatt2003.idatt2003boardgame.model.effect.Effect;

public class Tile {
    
    private List<Player> players;
    private Effect effect;
    private final int number;
    private int[] coordinates;

    public Tile(int number) {
        this.number = number;
        this.effect = null;
        this.coordinates = null;
        this.players = new ArrayList<>();
    }

    public Tile(int number,int[] coordinates) {
        this.number = number;
        this.effect = null;
        this.coordinates = coordinates;
        this.players = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public int[] getCoordinates() {
        if (coordinates != null) {
            return coordinates;
        } else {
            throw new NullPointerException("Coordinates are not set for this tile.");
        }
    }

    public Effect getEffect() {
        return effect;
    }

    public List<Player> getPlayers() {
        return players;
    }


    public void addPlayer(Player recievedPlayer) {
        players.add(recievedPlayer);

    }

    public void popPlayer() {
        players.remove(0);
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
        
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }
}