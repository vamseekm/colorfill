package com.cb.colorfill.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameData {

    public static final float BUMP_DURATION = 0.3f;

    private static float _WORLD_SCALE = (float)Gdx.graphics.getWidth()/720f;
    public static void setWorldScale(float scale){
        _WORLD_SCALE = scale;
    }
    public static float WORLD_SCALE(){
        return _WORLD_SCALE;
    }
    //public static float WORLD_SCALE = 1.50f;
    public static float WORLD_WIDTH(){
      return 720f * WORLD_SCALE();
    }

    public static float WORLD_HEIGHT(){
      return 1280f * WORLD_SCALE();
    }
    public ShapeRenderer SHAPE_RENDERER = new ShapeRenderer();

    public static final boolean DEBUG = false;
    public static final boolean DRAW_GRID = false;

    private BitmapFont font;


    private BitmapFont smallFont;
    private BitmapFont bigFont;
    private BitmapFont gameFont;
    //public final Color FONT_COLOR = new Color(64*3f/255f, 64*3f/255f, 64*3.5f/255f, 1);
    public final Color FONT_COLOR = new Color(64*3f/255f, 64*3f/255f, 64*3f/255f, 1);
    //public final Color BG_DARK_COLOR = new Color(240/255f, 240/255f, 255/255f, 1);
    public final Color BG_DARK_COLOR = new Color(240/255f, 240/255f, 240/255f, 1);

    private FreeTypeFontGenerator.FreeTypeFontParameter generateParams(float size){
        return generateParams(size, FONT_COLOR);
    }

    private FreeTypeFontGenerator.FreeTypeFontParameter generateParams(float size, Color textColor){
        FreeTypeFontGenerator.FreeTypeFontParameter smallFontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        smallFontParams.size = (int)size;
        smallFontParams.color = textColor;
        smallFontParams.spaceX = 1;
        return smallFontParams;
    }

    private void generateFonts() {
        smallFont = generateFont(40, FONT_COLOR);
        bigFont = generateFont(80, FONT_COLOR);
        gameFont = generateFont(120, FONT_COLOR);
    }

    public BitmapFont generateFont(float size, Color textColor){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/JosefinSans-Light.ttf"));
        BitmapFont bitmapFont = generator.generateFont(generateParams(size * WORLD_SCALE(), textColor));
        generator.dispose();
        return bitmapFont;
    }

    public BitmapFont GetSmallFont(){
        if(smallFont == null){
            generateFonts();
        }
        return smallFont;
    }

    public BitmapFont GetBigFont(){
        if(bigFont == null){
            generateFonts();
        }
        return bigFont;
    }

     public BitmapFont GetGameFont(){
        if(gameFont == null){
            generateFonts();
        }
        return gameFont;
    }
    public static final float SCALE_VALUE = 0.2f;



    Texture bgRadialTexture;
    public Texture getRadialTexture(){
        if(bgRadialTexture == null) {
            int width = (int)WORLD_WIDTH();
            int height = (int)WORLD_HEIGHT();
            Color centerColor = Color.WHITE;
            Color cornorColor = BG_DARK_COLOR;
            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
            int ox = width / 2;
            int oy = height / 2;
            int maxDistnace = (int) Math.floor(distance(0, 0, ox, oy));
            Color[] gradientScale = new Color[maxDistnace + 1];
            for (int i = 0; i <= maxDistnace; i++) {
                gradientScale[i] = centerColor.cpy().lerp(cornorColor, 1.0f * i / maxDistnace);
            }
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int distance = (int) Math.floor(distance(x, y, ox, oy));
                    if (distance > maxDistnace) {
                        distance = maxDistnace;
                    }
                    Color color = gradientScale[distance];
                    pixmap.drawPixel(x, y, Color.rgba8888(color));
                }
            }
            bgRadialTexture = new Texture(pixmap);
            pixmap.dispose();
        }
        return bgRadialTexture;
    }

    public float distance(int x1, int y1, int x2, int y2){
        int xdiff = x1 - x2;
        int ydiff = y1 - y2;
        return (float)Math.sqrt(xdiff*xdiff + ydiff*ydiff);
    }

}

