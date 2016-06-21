package com.cb.colorfill.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.cb.colorfill.elements.GameButton;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.GameData;

/**
 * Created by VamseeKrishna on 020, 20 Jun 2016.
 */
public class MenuScreen extends GameScreen{

    private final GameButton playButton;

    public MenuScreen(ColorFillGame game){
        super(game);
        //drawGrid(true);
        playButton = new GameButton(game, "PLAY");
        playButton.setPosition(game.gameData.WORLD_WIDTH/2 - playButton.getWidth()/2, game.gameData.WORLD_HEIGHT/2 - playButton.getHeight()/2);
        addActor(playButton);
        setupEvents();
    }

    private void setupEvents() {
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (hit(x, y, true) != null){
                    return true;
                }
                return false;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Actor actor = hit(x, y, true);
                if(actor == playButton){
                    startGame();
                }
            }
        });
    }

    private void startGame() {
        getGame().switchScreen(new ColorBoardScreen(getGame(), 9));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
