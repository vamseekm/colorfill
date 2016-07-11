package com.cb.colorfill.levels.classic;

import com.cb.colorfill.game.ColorUtils;
import com.cb.colorfill.game.GameUtil;
import com.cb.colorfill.levels.Level;

/**
 * Created by VamseeKrishna on 027, 27 Jun 2016.
 */
public class ClassicLevel extends Level {

    public enum Difficulty {
        EASY,
        NORMAL,
        HARD
    }

    private ClassicLevel(String name, int boardSize, int originX, int originY, int totalMoves ){
        super(name, boardSize, originX, originY, totalMoves);
    }

    public static Level createLevel(Difficulty difficulty){
        int totalMoves = 15;
        int boardSize = 8;
        int originX = 0;
        int originY = 0;
        String levelName = "";

        if(difficulty == Difficulty.NORMAL) {
            levelName = "classic.normal";
        }

        if(difficulty == Difficulty.HARD){
            levelName = "classic.hard";
            totalMoves -= 2;
        }

        if (difficulty == Difficulty.EASY){
            levelName = "classic.easy";
            totalMoves += 2;
        }

        return new ClassicLevel(levelName, boardSize, originX, originY, totalMoves);
    }

}
