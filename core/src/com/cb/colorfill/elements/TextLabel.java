package com.cb.colorfill.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.GraphicsUtil;

/**
 * Created by VamseeKrishna on 026, 26 Jun 2016.
 */
public class TextLabel extends Actor {

    private String text;
    private final int size;
    private final ColorFillGame game;
    private final Color textColor;
    private GlyphLayout glyphLayout = new GlyphLayout();
    private BitmapFont textFont;


    public TextLabel(ColorFillGame game, String text, int size, Color textColor){
        this.game = game;
        this.text = text;
        this.size = size;
        this.textColor = textColor;
        textFont = game.gameData.generateFont(size, textColor);
        glyphLayout.setText(textFont, text);
        setSize(glyphLayout.width, glyphLayout.height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        GraphicsUtil.enableBlending();
        BitmapFont scoreFont = game.gameData.GetGameFont();

        float width        = getWidth();
        float height       = getHeight();
        float scaledWidth  = width*getScaleX();
        float scaledHeight = height*getScaleY();
        float widthDiff    = scaledWidth - width;
        float xPos = getX() - scaledWidth/2;
        float yPos = getY() + scaledHeight/1.4f;

        scoreFont.getData().setScale(getScaleX(), getScaleY());
        //Color fontColor = game.gameData.FONT_COLOR;
        textFont.setColor(textColor.r, textColor.g, textColor.b, parentAlpha);
        textFont.draw(batch, text, xPos, yPos);
        GraphicsUtil.disableBlending();
    }

    public void updateDimensions() {
        glyphLayout.setText(textFont, text);
        setSize(glyphLayout.width, glyphLayout.height);
        //setPosition(-glyphLayout.width/2, glyphLayout.height/2);
    }

    public void setText(String text) {
        this.text = text;
    }
}
