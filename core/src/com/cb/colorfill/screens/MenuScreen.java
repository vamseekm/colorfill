package com.cb.colorfill.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.cb.colorfill.elements.GameButton;
import com.cb.colorfill.elements.TextLabel;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.GameData;
import com.cb.colorfill.levels.Level;
import com.cb.colorfill.levels.classic.ClassicLevel;

import java.util.Map;

/**
 * Created by VamseeKrishna on 020, 20 Jun 2016.
 */
public class MenuScreen extends GameScreen{

    private final GameButton playButton;
    private final TextLabel titleLabel;
    private ClassicLevel.Difficulty difficulty;

    private GameButton easyButton;
    private GameButton normalButton;
    private GameButton hardButton;

    public MenuScreen(ColorFillGame game) {
        super(game);
        this.titleLabel = new TextLabel(game, "color fill", 100, game.gameData.FONT_COLOR);
        titleLabel.setPosition(GameData.WORLD_WIDTH / 2, GameData.WORLD_HEIGHT * 4 / 5);
        addActor(titleLabel);
        playButton   = createButton("play",   75, 1.0f/2,   1.0f/2);
        easyButton   = createButton("easy",   35, 1.0f*1/6, 1.0f/5);
        normalButton = createButton("normal", 35, 1.0f*3/6, 1.0f/5);
        hardButton   = createButton("hard",   35, 1.0f*5/6, 1.0f/5);
        easyButton.setButtonSize(normalButton.getWidth());
        hardButton.setButtonSize(normalButton.getWidth());
        setDifficulty(ClassicLevel.Difficulty.NORMAL);
        setupEvents();
    }

    private GameButton createButton(String label, int fontSize, float x, float y) {
        GameButton button = GameButton.Builder()
                .setFontSize(fontSize)
                .setText(label)
                .setPosition(x*game.gameData.WORLD_WIDTH, y*game.gameData.WORLD_HEIGHT)
                .build(game);
        addActor(button);
        return button;
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
                    if(actor.isDescendantOf(playButton)){
                        startGame(ClassicLevel.createLevel(difficulty));
                    }
                    if(actor.isDescendantOf(easyButton)){
                        setDifficulty(ClassicLevel.Difficulty.EASY);
                    }
                    if(actor.isDescendantOf(normalButton)){
                        setDifficulty(ClassicLevel.Difficulty.NORMAL);
                    }
                    if(actor.isDescendantOf(hardButton)){
                        setDifficulty(ClassicLevel.Difficulty.HARD);
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

    public void setDifficulty(ClassicLevel.Difficulty difficulty) {
        this.difficulty = difficulty;
        difficultyButtonApply(easyButton, difficulty == ClassicLevel.Difficulty.EASY);
        difficultyButtonApply(normalButton, difficulty == ClassicLevel.Difficulty.NORMAL);
        difficultyButtonApply(hardButton, difficulty == ClassicLevel.Difficulty.HARD);
    }

    private void difficultyButtonApply(GameButton button, boolean val) {
        if(val){
            button.addAction(Actions.alpha(1.0f, 1f));
        }else{
            button.addAction(Actions.alpha(0.8f, 1f));
        }
    }
}
