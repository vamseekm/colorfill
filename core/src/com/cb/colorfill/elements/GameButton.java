package com.cb.colorfill.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.cb.colorfill.game.GameData;
import com.cb.colorfill.game.GameUtil;

/**
 * Created by VamseeKrishna on 020, 20 Jun 2016.
 */
public class GameButton extends Actor {
    private final String text;
    public GameButton(String text){
        this.text = text;
        BitmapFont scoreFont = GameData.GetGameFont();
        glyphLayout.setText(scoreFont, text);
        setSize(glyphLayout.width, glyphLayout.height);
        setupEvents();
    }

    private void setupEvents() {
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pressDown();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pressUp();
            }
        });
    }

    private void pressDown(){
        addAction(Actions.scaleTo(1.1f, 1.1f, 0.15f, Interpolation.exp5In));
    }

    private void pressUp(){
        addAction(Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.exp5Out));
    }

    private static GlyphLayout glyphLayout = new GlyphLayout();

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        GameUtil.enableBlending();
        BitmapFont scoreFont = GameData.GetGameFont();

        float width        = getWidth();
        float height       = getHeight();
        float scaledWidth  = width*getScaleX();
        float scaledHeight = height*getScaleY();
        float widthDiff    = scaledWidth - width;
        float xPos = getX() -  widthDiff/2;
        float yPos = getY() + scaledHeight;

        scoreFont.getData().setScale(getScaleX(), getScaleY());
        Color fontColor = GameData.FONT_COLOR;
        scoreFont.setColor(fontColor.r, fontColor.g, fontColor.b, parentAlpha);
        scoreFont.draw(batch, text, xPos, yPos);
        GameUtil.disableBlending();
    }
}

