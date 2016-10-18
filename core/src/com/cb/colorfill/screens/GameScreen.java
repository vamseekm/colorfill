package com.cb.colorfill.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.GameData;

/**
 * Created by VamseeKrishna on 020, 20 Jun 2016.
 */
public class GameScreen extends Group {
    ColorFillGame game;
    private boolean screenPaused = false;
    BitmapFont font = new BitmapFont();

    public GameScreen(ColorFillGame game){
        this.game = game;
    }

    public void backButtonPressed(){

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            backButtonPressed();
        }
        if (GameData.DRAW_GRID) {
            batch.end();
            ColorFillGame game = getGame();
            ShapeRenderer shapeRenderer = game.gameData.SHAPE_RENDERER;
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
            shapeRenderer.translate(getX(), getY(), 0);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            Color color = Color.RED;
            shapeRenderer.setColor(color.r, color.g, color.b, parentAlpha);
            for (float x = 0; x <= game.gameData.WORLD_WIDTH; x += game.gameData.WORLD_WIDTH / 10) {
                shapeRenderer.line(x, 0, x, game.gameData.WORLD_HEIGHT);
            }
            for (float y = 0; y <= game.gameData.WORLD_HEIGHT; y += game.gameData.WORLD_HEIGHT / 10) {
                shapeRenderer.line(0, y, game.gameData.WORLD_HEIGHT, y);
            }
            shapeRenderer.end();
            batch.begin();
        }
        super.draw(batch, parentAlpha);
        if(GameData.DEBUG) {
            font.draw(batch, "FPS:" + Gdx.graphics.getFramesPerSecond(), 100, 100);
        }
        //Gdx.graphics.setTitle(""+Gdx.graphics.getFramesPerSecond());
    }


    private static final float TRANSITION_DURATION = 0.3f;

    public void showScreen(){
        addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(TRANSITION_DURATION, Interpolation.exp5In)));
    }

    public void destroyScreen(){
        addAction(Actions.sequence(Actions.fadeOut(TRANSITION_DURATION, Interpolation.exp5Out), Actions.removeActor(this)));
    }

    public ColorFillGame getGame(){
        return game;
    }

    public void setGridPosition(Actor actor, float x, float y) {
        actor.setPosition(x*GameData.WORLD_WIDTH, y*GameData.WORLD_HEIGHT);
    }

    public void pause(){
        this.screenPaused = true;
    }

    public void resume(){
        this.screenPaused = false;
    }

    public boolean isPaused(){
        return screenPaused;
    }
}
