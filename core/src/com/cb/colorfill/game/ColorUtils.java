package com.cb.colorfill.game;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by VamseeKrishna on 011, 11 Jun 2016.
 */
public class ColorUtils {
    private Color[] colors = new Color[]{
            RGBToColor(255, 137, 137), //red
            RGBToColor(255, 208, 132), //organge
            RGBToColor(150, 227, 149), //green
            RGBToColor(154, 157, 253), //violet
            RGBToColor(233, 164, 233), //pink
            RGBToColor(127, 210, 255),  //blue
    };

    public Color getColorForCode(int colorCode) {
        return colors[colorCode];
    }

    public int getNumColors(){
        return colors.length;
    }

    public int randomColorCode(){
        int numColors = getNumColors();
        return (int)Math.floor(Math.random()*numColors);
    }

    public Color RGBToColor(int r, int g, int b){
        return new Color(r/255f, g/255f, b/255f, 1);
    }
}
