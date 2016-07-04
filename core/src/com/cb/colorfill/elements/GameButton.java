package com.cb.colorfill.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.ColorUtils;
import com.cb.colorfill.game.GameData;

import java.util.Calendar;

/**
 * Created by VamseeKrishna on 020, 20 Jun 2016.
 */
public class GameButton extends Group {
    public static class Builder {
        private String text;
        private int fontSize;
        private float x = 0;
        private float y = 0;

        public Builder setText(String text){
            this.text = text;
            return this;
        }

        public Builder setFontSize(int fontSize){
            this.fontSize = fontSize;
            return this;
        }

        public GameButton build(ColorFillGame game){
            GameButton gameButton = new GameButton(game, text, fontSize);
            gameButton.setPosition(x, y);
            return gameButton;
        }


        public Builder setPosition(float x, float y) {
            this.x = x;
            this.y = y;
            return this;
        }
    }

    public static Builder Builder(){
        return new Builder();
    }
    private final ColorFillGame game;
    private final TextLabel buttonText;
    private ColorBox buttonBackground;

    public GameButton(ColorFillGame game, String text, int fontSize){
        this.game = game;
        buttonBackground = new ColorBox(game, ColorBox.ShapeType.DIAMOND, ColorUtils.VIOLET, -1, -1);
        this.buttonText = new TextLabel(game, text, fontSize,Color.WHITE);
        addActor(buttonBackground);
        addActor(buttonText);
        float buttonSize = buttonText.getWidth()*2f;
        buttonBackground.setBounds(0 - buttonSize/2, 0 - buttonSize/2, buttonSize, buttonSize);
        setSize(buttonSize, buttonSize);
        setupEvents();
    }

    public void setButtonSize(float buttonSize) {
        buttonBackground.setBounds(0 - buttonSize/2, 0 - buttonSize/2, buttonSize, buttonSize);
        setSize(buttonSize, buttonSize);
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
        //addAction(Actions.scaleTo(0.95f, 0.95f, 0.15f, Interpolation.exp5In));
        addAction(Actions.scaleTo(1 - GameData.SCALE_VALUE, 1 - GameData.SCALE_VALUE));
    }

    private void pressUp(){
        addAction(Actions.scaleTo(1.0f, 1.0f, GameData.BUMP_DURATION, Interpolation.exp5Out));
    }

    private static GlyphLayout glyphLayout = new GlyphLayout();


}

