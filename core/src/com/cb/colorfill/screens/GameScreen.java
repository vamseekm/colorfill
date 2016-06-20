package com.cb.colorfill.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.GameData;

/**
 * Created by VamseeKrishna on 020, 20 Jun 2016.
 */
public class GameScreen extends Group {
    ColorFillGame game;
    private boolean drawGrid = false;
    public GameScreen(ColorFillGame game){
        this.game = game;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //System.out.println(parentAlpha);
        if (drawGrid) {
            batch.end();

            ShapeRenderer shapeRenderer = GameData.SHAPE_RENDERER;
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
            shapeRenderer.translate(getX(), getY(), 0);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            for (float x = 0; x <= GameData.WORLD_WIDTH; x += GameData.WORLD_WIDTH / 10) {
                shapeRenderer.line(x, 0, x, GameData.WORLD_HEIGHT);
            }
            for (float y = 0; y <= GameData.WORLD_HEIGHT; y += GameData.WORLD_HEIGHT / 10) {
                shapeRenderer.line(0, y, GameData.WORLD_HEIGHT, y);
            }
            shapeRenderer.end();
            batch.begin();
        }
    }

    public void drawGrid(boolean val){
        this.drawGrid = val;
    }

    public void showScreen(){
        System.out.println("fade in");
        addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f, Interpolation.exp5In)));
    }

    public void destroyScreen(){
        addAction(Actions.sequence(Actions.fadeOut(1f, Interpolation.exp5Out), Actions.removeActor(this)));
    }

    public ColorFillGame getGame(){
        return game;
    }
}
