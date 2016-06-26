package com.cb.colorfill.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.ColorUtils;
import com.cb.colorfill.game.GameData;
import com.cb.colorfill.game.GameUtil;

/**
 * Created by VamseeKrishna on 020, 20 Jun 2016.
 */
public class GameButton extends Group {
    private final ColorFillGame game;
    private final TextLabel buttonText;
    private ColorBox buttonBackground;

    public GameButton(ColorFillGame game, String text){
        this.game = game;
        buttonBackground = new ColorBox(game, ColorBox.ShapeType.DIAMOND, ColorUtils.VIOLET, -1, -1);
        this.buttonText = new TextLabel(game, "play", 75,Color.WHITE);
        addActor(buttonBackground);
        addActor(buttonText);
        float buttonSize = buttonText.getWidth()*2f;
        buttonBackground.setBounds(0 - buttonSize/2, 0 - buttonSize/2, buttonSize, buttonSize);
        setSize(buttonSize, buttonSize);
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
        //addAction(Actions.scaleTo(0.95f, 0.95f, 0.15f, Interpolation.exp5In));
        addAction(Actions.scaleTo(1 - GameData.SCALE_VALUE, 1 - GameData.SCALE_VALUE));
    }

    private void pressUp(){
        addAction(Actions.scaleTo(1.0f, 1.0f, GameData.BUMP_DURATION, Interpolation.exp5Out));
    }

    private static GlyphLayout glyphLayout = new GlyphLayout();

        /*
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        GameUtil.enableBlending();
        BitmapFont scoreFont = game.gameData.GetGameFont();

        float width        = getWidth();
        float height       = getHeight();
        float scaledWidth  = width*getScaleX();
        float scaledHeight = height*getScaleY();
        float widthDiff    = scaledWidth - width;
        float xPos = getX() -  widthDiff/2;
        float yPos = getY() + scaledHeight;

        scoreFont.getData().setScale(getScaleX(), getScaleY());
        Color fontColor = game.gameData.FONT_COLOR;
        scoreFont.setColor(fontColor.r, fontColor.g, fontColor.b, parentAlpha);
        scoreFont.draw(batch, text, xPos, yPos);
        GameUtil.disableBlending();
    }
        */
}

