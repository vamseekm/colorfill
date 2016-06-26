package com.cb.colorfill.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.GraphicsUtil;


public class ScoreLabel extends Actor {
    private final ColorFillGame game;
    private int totalMoves;
    private String movesText;
    GlyphLayout glyphLayout;


    public ScoreLabel(ColorFillGame game, int totalMoves){
        this.game = game;
        this.totalMoves = totalMoves;
        updateMovesText();
        glyphLayout = new GlyphLayout();
        BitmapFont scoreFont = game.gameData.GetBigFont();
        glyphLayout.setText(scoreFont, totalMoves+"");
        setSize(game.gameData.WORLD_WIDTH, glyphLayout.height);
        setTouchable(Touchable.disabled);

    }

    private void updateMovesText() {
        movesText = totalMoves + "";
    }

    public void doMove(){
        totalMoves -= 1;
        updateMovesText();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        BitmapFont scoreFont = game.gameData.GetBigFont();
        glyphLayout.setText(scoreFont, movesText);

        float xPos = getX() + getWidth()/2 - glyphLayout.width/2;
        float yPos = getY();

        GraphicsUtil.enableBlending();
        Color fontColor = game.gameData.FONT_COLOR;
        scoreFont.setColor(fontColor.r, fontColor.g, fontColor.b, parentAlpha);
        scoreFont.draw(batch, movesText, xPos , yPos);
        GraphicsUtil.disableBlending();
    }
}
