package com.cmpt276.scoreapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Game {

    LocalDateTime creationTime;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("(@yyyy-MM-dd HH:mm)");

    int playerWon=0;
    int playerCount=0;
    int maxScore;

    public boolean playerOneWin = false;
    public boolean playerTwoWin = false;
    public boolean playerThreeWin = false;
    public boolean playerFourWin = false;


    ArrayList<Integer> scores = new ArrayList<Integer>();

    public Game(int score1) {
        playerCount=1;
        this.creationTime = LocalDateTime.now();
        playerOneWin = true;
        scores.add(score1);

        this.maxScore = score1;
        if (score1 == this.maxScore){
            this.playerOneWin = true;
        }

    }

    public Game(int score1, int score2) {
        playerCount=2;
        this.creationTime = LocalDateTime.now();
        //findWinner(score1,score2,0,0);
        scores.add(score1);
        scores.add(score2);

        this.maxScore = Math.max(score1,score2);
        if (score1 == this.maxScore){
            this.playerOneWin = true;
        }
        if (score2 == this.maxScore){
            this.playerTwoWin = true;
        }

    }

    public Game(int score1, int score2, int score3) {
        playerCount=3;
        this.creationTime = LocalDateTime.now();
        //findWinner(score1,score2,score3,0);
        scores.add(score1);
        scores.add(score2);
        scores.add(score3);

        this.maxScore = Math.max(score1,Math.max(score2,score3));
        if (score1 == this.maxScore){
            this.playerOneWin = true;
        }
        if (score2 == this.maxScore){
            this.playerTwoWin = true;
        }
        if (score3 == this.maxScore){
            this.playerThreeWin = true;
        }

    }

    public Game(int score1, int score2, int score3, int score4) {
        playerCount=4;
        this.creationTime = LocalDateTime.now();
        //findWinner(score1,score2,score3,score4);
        scores.add(score1);
        scores.add(score2);
        scores.add(score3);
        scores.add(score4);

        this.maxScore = Math.max(score1,Math.max(score2,Math.max(score3,score4)));
        if (score1 == this.maxScore){
            this.playerOneWin = true;
        }
        if (score2 == this.maxScore){
            this.playerTwoWin = true;
        }
        if (score3 == this.maxScore){
            this.playerThreeWin = true;
        }
        if (score4 == this.maxScore){
            this.playerFourWin = true;
        }
    }

    public void findWinner(int score1, int score2, int score3, int score4){
        this.maxScore = Math.max(score1,Math.max(score2,Math.max(score3,score4)));
        if (score1 == this.maxScore){
            this.playerOneWin = true;
        }
        if (score2 == this.maxScore){
            this.playerTwoWin = true;
        }
        if (score3 == this.maxScore){
            this.playerThreeWin = true;
        }
        if (score4 == this.maxScore){
            this.playerFourWin = true;
        }
    }

    public int getScore (int pos){
        return this.scores.get(pos);
    }

    public int getPlayerCount(){
        return this.playerCount;
    }

    public String getCreationTime() {
        return dtf.format(creationTime);
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public int getPlayerWon() {
        return playerWon;
    }

    public void setPlayerWon(int playerWon) {
        this.playerWon = playerWon;
    }

    public int getMaxScore(){
        return maxScore;
    }

    public String getMaxScoreString() {
        return Integer.toString(maxScore);
    }

}
