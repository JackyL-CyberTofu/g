package com.cmpt276.scoreapp;
import com.cmpt276.scoreapp.models.Game;


import java.util.ArrayList;

public class GameManager {

    public ArrayList<Game> games = new ArrayList<Game>();
    public int gameCount = 0;

    // Singleton Support
    private static GameManager instance;
    private GameManager(){
        //Private to prevent anyone else from instantiating
    }
    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    public void addGame(Game game){

        games.add(game);
        gameCount = gameCount+1;
    }

    public int getGameCounant(){
        return gameCount;
    }

    public Game getGame(int index){
        return games.get(index);
    }

    public void deleteGame(int index){

        games.remove(index-1);
        gameCount = gameCount-1;
    }

}
