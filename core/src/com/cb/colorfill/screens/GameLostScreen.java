package com.cb.colorfill.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.cb.colorfill.elements.GameButton;
import com.cb.colorfill.elements.TextLabel;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.ColorUtils;
import com.cb.colorfill.levels.Level;

/**
 * Created by VamseeKrishna on 005, 5 Jul 2016.
 */
public class GameLostScreen extends GameScreen {
    private final Level level;
    private GameButton menuButton;
    private GameButton tryAgainButton;

    public GameLostScreen(ColorFillGame game, Level level) {
        super(game);
        System.out.println("Game fail screen created");
        this.level = level;

        TextLabel outOfMovesLabel = new TextLabel(game, "out of moves", 90, game.gameData.FONT_COLOR);
        addActor(outOfMovesLabel);
        setGridPosition(outOfMovesLabel, 1f/2, 4f/5);

        tryAgainButton = new GameButton(game, "play again", 45, ColorUtils.GREEN);
        addActor(tryAgainButton);
        setGridPosition(tryAgainButton, 1f/2, 1f/2);

        this.menuButton = new GameButton(game, "menu", 45, ColorUtils.VIOLET);
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
                    if(tryAgainButton != null && actor.isDescendantOf(tryAgainButton)){
                        level.setRemainingMoves(0);
                        level.setWon(false);
                        game.switchScreen(new ColorBoardScreen(game, level));
                    }
                }
            }
        });
    }
}
