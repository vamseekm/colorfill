package com.cb.colorfill.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameData {

    public static final float WORLD_SCALE = 0.75f;
    public static final float WORLD_WIDTH = 720f * WORLD_SCALE;
    public static final float WORLD_HEIGHT = 1280f * WORLD_SCALE;
    public static ShapeRenderer SHAPE_RENDERER = new ShapeRenderer();

    private static BitmapFont font;

    public static BitmapFont xGetGameFont(){
        if (font == null){
            font = new BitmapFont();
            font.setColor(new Color(0/255f, 1/255f, 51/255f, 1));
        }
        return font;
    }

    private static BitmapFont smallFont;
    private static BitmapFont bigFont;
    private static BitmapFont gameFont;
    //private static Color fontColor = new Color(128/255f, 128/255f, 150/255f, 1);
    public static final Color FONT_COLOR = new Color(64*2.5f/255f, 64*2.5f/255f, 64*3.0f/255f, 1);
    //public static final Color BG_DARK_COLOR = new Color(250/255f, 250/255f, 250/255f, 1);
    public static final Color BG_DARK_COLOR = new Color(240/255f, 240/255f, 255/255f, 1);

    private static FreeTypeFontGenerator.FreeTypeFontParameter generateParams(float size){
        FreeTypeFontGenerator.FreeTypeFontParameter smallFontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        smallFontParams.size = (int)size;
        smallFontParams.color = FONT_COLOR;
        smallFontParams.spaceX = 1;
        return smallFontParams;
    }

    private static void generateFonts() {
        //FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Light.ttf"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Lato-Light.ttf"));
        smallFont = generator.generateFont(generateParams(30f*WORLD_SCALE));
        bigFont = generator.generateFont(generateParams(40f*WORLD_SCALE));
        gameFont = generator.generateFont(generateParams(80*WORLD_SCALE));
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

     public static BitmapFont GetGameFont(){
        if(gameFont == null){
            generateFonts();
        }
        return gameFont;
    }

}

