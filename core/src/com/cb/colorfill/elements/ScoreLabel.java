package com.cb.colorfill.elements;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.cb.colorfill.game.ColorFillGame;


public class ScoreLabel extends Group {

    private final ColorFillGame game;
    private final TextLabel textLabel;
    private int totalMoves;
    private int remainingMoves;
    private String movesText;
    GlyphLayout glyphLayout;
    private int move;


    public ScoreLabel(ColorFillGame game, int totalMoves){
        this.game = game;
        this.totalMoves = totalMoves;
        this.remainingMoves = totalMoves;
        this.textLabel = new TextLabel(game, "", 90, game.gameData.FONT_COLOR);
        setTouchable(Touchable.disabled);
        addActor(textLabel);
        updateMovesText();
        textLabel.setPosition(getX() + game.gameData.WORLD_WIDTH()/2, getY());
    }


    private void updateMovesText() {
        movesText = remainingMoves + "";
        textLabel.setText(movesText);
        textLabel.updateDimensions();
    }

    public void doMove(){
        remainingMoves -= 1;
        updateMovesText();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    public int getRemainingMoves () {
        return remainingMoves;
    }
}
