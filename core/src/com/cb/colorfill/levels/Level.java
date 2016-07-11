package com.cb.colorfill.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by VamseeKrishna on 027, 27 Jun 2016.
 */
public abstract class Level {
    private final String levelName;
    private final int moves;


    private final int size;
    private final int originX;
    private final int originY;
    private int remainingMoves;
    private boolean won = false;

    public Level(String levelName,  int size, int originX, int originY, int moves){
        this.levelName = levelName;
        this.size = size;
        this.originX = originX;
        this.originY = originY;
        this.moves = moves;
    }

    public String getLevelName() {
        return levelName;
    }

    public int getMoves() {
        return moves;
    }

    public int getSize() {
        return size;
    }

    public int getOriginX() {
        return originX;
    }

    public int getOriginY() {
        return originY;
    }

    public void setRemainingMoves(int remainingMoves) {
        this.remainingMoves = remainingMoves;
    }

    public int getUserMoves(){
        return this.moves - remainingMoves;
    }

    public void setWon(boolean val){
        this.won = val;
    }

    public boolean isWon() {
        return won;
    }

    public void storeScore() {
        Preferences levels = Gdx.app.getPreferences("levels");
        String scoreKey = bestScoreKey();
        storeBestScore(getUserMoves());
    }

    public String bestScoreKey(){
        return getLevelName() + ".best_score";
    }

    public Preferences getLevelsPreferences(){
        return Gdx.app.getPreferences("levels");
    }

    public int getBestScore(){
        String scoreKey = bestScoreKey();
        Preferences levels = getLevelsPreferences();
        return levels.getInteger(scoreKey, -1);
    }

    public void storeBestScore(int score){
        int bestScore = getBestScore();
        Preferences levels = getLevelsPreferences();
        if(bestScore == -1 || bestScore > score){
            levels.putInteger(bestScoreKey(), score);
        }
        levels.flush();
    }
}
