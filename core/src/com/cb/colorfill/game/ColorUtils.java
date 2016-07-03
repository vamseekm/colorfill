package com.cb.colorfill.game;

import com.badlogic.gdx.graphics.Color;

import java.util.Arrays;

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
    public static Color INACTIVE_MENUITEM_BG = RGBToColor(240, 240, 255);
    public static Color[] colors = new Color[]{
            //game box colors
            RED,
            ORANGE,
            GREEN,
            VIOLET,
            PINK,
            BLUE,

            //other colors
            INACTIVE_MENUITEM_BG,
    };

    public Color getColorForCode(int colorCode) {
        return colors[colorCode];
    }

    public int getCodeForColor(Color color){
        int code = Arrays.asList(colors).indexOf(color);
        if(code == -1){
            return Arrays.asList(colors).indexOf(INACTIVE_MENUITEM_BG);
        }
        return code;
    }

    public static int getNumColors(){
        return 6;
    }

    public int randomColorCode(){
        int numColors = getNumColors();
        return (int)Math.floor(Math.random()*numColors);
    }

    public static Color RGBToColor(int r, int g, int b){
        return new Color(r/255f, g/255f, b/255f, 1);
    }
}
