package com.cb.colorfill.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.GameData;
import com.cb.colorfill.game.GameUtil;


public class ScoreLabel extends Actor {
    private final ColorFillGame game;
    private int moves = 0;
    private String movesText;
    GlyphLayout glyphLayout;


    public ScoreLabel(ColorFillGame game){
        this.game = game;
        updateMovesText();
        glyphLayout = new GlyphLayout();
        BitmapFont scoreFont = game.gameData.GetBigFont();
        glyphLayout.setText(scoreFont, moves+"");
        setSize(game.gameData.WORLD_WIDTH, glyphLayout.height);
        setTouchable(Touchable.disabled);

    }

    private void updateMovesText() {
        movesText = moves + "";
    }

    public void increaseMove(){
        moves += 1;
        updateMovesText();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        BitmapFont scoreFont = game.gameData.GetBigFont();
        glyphLayout.setText(scoreFont, movesText);

        float xPos = getX() + getWidth()/2 - glyphLayout.width/2;
        float yPos = getY();

        GameUtil.enableBlending();
        Color fontColor = game.gameData.FONT_COLOR;
        scoreFont.setColor(fontColor.r, fontColor.g, fontColor.b, parentAlpha);
        scoreFont.draw(batch, movesText, xPos , yPos);
        GameUtil.disableBlending();
    }
}
