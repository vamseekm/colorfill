package com.cb.colorfill.game;

/**
 * Created by VamseeKrishna on 027, 27 Jun 2016.
 */
public class GameUtil {
    public static int maxMoves(int rows, int cols, int ncolors){
        return (int)Math.floor(25.0f*((rows+cols)*ncolors)/((14+14)*6));
    }
}
