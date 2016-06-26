package com.cb.colorfill.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.cb.colorfill.elements.GameButton;
import com.cb.colorfill.elements.TextLabel;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.GameData;
import com.cb.colorfill.levels.Level;
import com.cb.colorfill.levels.LevelEasy;

/**
 * Created by VamseeKrishna on 020, 20 Jun 2016.
 */
public class MenuScreen extends GameScreen{

    private final GameButton playButton;
    private final TextLabel titleLabel;

    public MenuScreen(ColorFillGame game){
        super(game);
        this.titleLabel = new TextLabel(game, "color fill", 100, game.gameData.FONT_COLOR);
        titleLabel.setPosition(GameData.WORLD_WIDTH/2, GameData.WORLD_HEIGHT*4/5);
        addActor(titleLabel);
        //drawGrid(true);
        playButton = new GameButton(game, "play");
        playButton.setPosition(game.gameData.WORLD_WIDTH/2, game.gameData.WORLD_HEIGHT/2);
        addActor(playButton);
        setupEvents();
    }

    private void setupEvents() {
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (hit(x, y, true) != null){
                    System.out.println("hit ok");
                    return true;
                }
                return false;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Actor actor = hit(x, y, true);
                if(actor != null) {
                    System.out.println("Down:" + actor);
                    Group parent = actor.getParent();
                    if (parent == playButton) {
                        startGame(new LevelEasy());
                    }
                }
            }
        });
    }

    private void startGame(Level level) {
        getGame().switchScreen(new ColorBoardScreen(getGame(), level));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
