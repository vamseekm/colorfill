package com.cb.colorfill.levels;

/**
 * Created by VamseeKrishna on 027, 27 Jun 2016.
 */
public abstract class Level {
    private final String levelName;
    private final int moves;


    private final int size;
    private final int originX;
    private final int originY;

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
}
