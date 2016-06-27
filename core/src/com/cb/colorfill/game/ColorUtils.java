package com.cb.colorfill.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by VamseeKrishna on 011, 11 Jun 2016.
 */
public class ColorUtils {
    public static Color BLUE = RGBToColor(127, 210, 255);
    public static Color RED = RGBToColor(255, 137, 137);
    public static Color ORANGE = RGBToColor(255, 208, 132);
    public static Color GREEN = RGBToColor(150, 227, 149);
    public static Color VIOLET = RGBToColor(154, 157, 253);
    public static Color PINK = RGBToColor(233, 164, 233) ;
    public static Color[] colors = new Color[]{
            RED,
            ORANGE,
            GREEN,
            VIOLET,
            PINK,
            BLUE,
    };

    public Color getColorForCode(int colorCode) {
        return colors[colorCode];
    }

    public static int getNumColors(){
        return colors.length;
    }

    public int randomColorCode(){
        int numColors = getNumColors();
        return (int)Math.floor(Math.random()*numColors);
    }

    public static Color RGBToColor(int r, int g, int b){
        return new Color(r/255f, g/255f, b/255f, 1);
    }
}
