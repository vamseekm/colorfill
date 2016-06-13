package com.cb.colorfill;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameData {

    public static final float WORLD_SCALE = 1f;
    public static final float WORLD_WIDTH = 720f * WORLD_SCALE;
    public static final float WORLD_HEIGHT = 1280f * WORLD_SCALE;
    public static ShapeRenderer SHAPE_RENDERER = new ShapeRenderer();

    private static BitmapFont font;
    public static BitmapFont GetGameFont(){
        if (font == null){
            font = new BitmapFont();
            font.setColor(new Color(0/255f, 1/255f, 51/255f, 1));
        }
        return font;
    }

    private static BitmapFont smallFont;
    private static BitmapFont bigFont;
    private static Color fontColor = new Color(128/255f, 128/255f, 150/255f, 1);

    private static FreeTypeFontGenerator.FreeTypeFontParameter generateParams(float size){
        FreeTypeFontGenerator.FreeTypeFontParameter smallFontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        smallFontParams.size = (int)size;
        smallFontParams.color = fontColor;
        smallFontParams.spaceX = 1;
        return smallFontParams;
    }

    private static void generateFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Light.ttf"));
        smallFont = generator.generateFont(generateParams(30f*WORLD_SCALE));
        bigFont = generator.generateFont(generateParams(40f*WORLD_SCALE));
        generator.dispose();
    }

    public static BitmapFont GetSmallFont(){
        if(smallFont == null){
            generateFonts();
        }
        return smallFont;
    }

    public static BitmapFont GetBigFont(){
        if(bigFont == null){
            generateFonts();
        }
        return bigFont;
    }
}

