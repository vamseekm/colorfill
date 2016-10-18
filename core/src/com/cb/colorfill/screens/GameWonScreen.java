package com.cb.colorfill.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.cb.colorfill.elements.GameButton;
import com.cb.colorfill.elements.TextLabel;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.ColorUtils;
import com.cb.colorfill.levels.Level;


public class GameWonScreen extends GameScreen {

    private final Level level;
    private final GameButton menuButton;

    public GameWonScreen(ColorFillGame game, Level level){
        super(game);
        this.level = level;
        level.storeScore();
        TextLabel wonLabel = new TextLabel(game, "You Won! in " + level.getUserMoves() + " Moves", 75, game.gameData.FONT_COLOR);
        addActor(wonLabel);
        setGridPosition(wonLabel, 1f/2, 4f/5);

        int bestScore = level.getBestScore();
        if(bestScore > 0){
            TextLabel bestScoreLabel = new TextLabel(game, "Your all time best moves " + bestScore, 50, game.gameData.FONT_COLOR);
            addActor(bestScoreLabel);
            setGridPosition(bestScoreLabel, 1f/2, 3.5f/5);
        }

        menuButton = new GameButton(game, "menu", 45, ColorUtils.VIOLET);
        addActor(menuButton);
        setGridPosition(menuButton, 1f/2, 1f/5);

        setupEvents();
    }

    private void setupEvents() {
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                if (hit(x, y, true) != null){
                    return true;
                }
                return false;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Actor actor = hit(x, y, true);
                if(actor != null){
                    if(actor.isDescendantOf(menuButton)){
                        game.switchScreen(new MenuScreen(game));
                    }
                }
            }
        });
    }
}
