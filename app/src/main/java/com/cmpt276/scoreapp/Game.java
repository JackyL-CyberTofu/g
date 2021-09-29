package com.cmpt276.scoreapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Game {

    LocalDateTime creationTime;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("(@yyyy-MM-dd HH:mm)");
    String time;

    int playerWon=0;
    int playerCount=0;
    int maxScore;

    public boolean tie = false;
    public boolean playerOneWin = false;
    public boolean playerTwoWin = false;

    int playerOneScore = 0;
    int playerTwoScore = 0;

    int playerOneCards = 0;
    int playerTwoCards = 0;

    int playerOnePoints = 0;
    int playerTwoPoints = 0;

    int playerOneWager = 0;
    int playerTwoWager = 0;

    boolean isEdit = false;

    ArrayList<Integer> scores = new ArrayList<Integer>();

    public Game(int cards1, int cards2, int points1, int points2, int wager1, int wager2, int score1, int score2) {

        playerCount=2;
        this.creationTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.time = creationTime.format(formatter);
        //findWinner(score1,score2,0,0);
        this.playerOneCards = cards1;
        this.playerOnePoints = points1;
        this.playerOneWager = wager1;
        this.playerOneScore = score1;

        this.playerTwoCards = cards2;
        this.playerTwoPoints = points2;
        this.playerTwoWager = wager2;
        this.playerTwoScore = score2;

        this.maxScore = Math.max(score1, score2);
        if (maxScore == score1 && score1 == score2){
            this.tie = true;
        }
        if (this.maxScore == score1){
            this.playerOneWin = true;
        }
        else {
            this.playerTwoWin = true;
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

    public String getScore1(){
        return Integer.toString(playerOneScore);
    }

    public String getScore2(){
        return Integer.toString(playerTwoScore);
    }

}
