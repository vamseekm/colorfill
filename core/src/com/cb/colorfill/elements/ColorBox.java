package com.cb.colorfill.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.ColorUtils;
import com.cb.colorfill.game.GameData;
import com.cb.colorfill.game.GameUtil;


public class ColorBox extends Actor {
    private final int row;
    private final int col;
    private final ColorFillGame game;
    private int iter = 0;
    private int colorCode;
    private boolean processed = false;
    private int newColorCode;
    Action changeColor;

    public ColorBox(ColorFillGame game, int colorCode, int row, int col){
        this.game = game;
        this.colorCode = colorCode;
        this.row = row;
        this.col = col;
        changeColor = new Action(){
            public boolean act(float delta){
                setColorCode(newColorCode);
                return true;
            }
        };
    }

    public ColorBox(ColorFillGame game, int colorCode){
        this(game, colorCode, -1, -1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //System.out.println(parentAlpha);
        batch.end();
        float scaleX = getScaleX();
        float scaleY = getScaleY();
        float width = getWidth();
        float height = getHeight();
        float scaledWidth = width*scaleX;
        float scaledHeight = height*scaleY;
        float posX = getX() -  (scaledWidth - width)/2;
        float posY = getY() - (scaledHeight - height)/2;

        GameUtil.enableBlending();
        ShapeRenderer renderer = game.gameData.SHAPE_RENDERER;
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        //renderer.translate(getX() + posX, getY() + posY, 0);
        Color color = game.colorUtils.getColorForCode(colorCode);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        //renderer.setColor(color);
        renderer.setColor(color.r, color.g, color.b, parentAlpha);
        renderer.rect(posX, posY, scaledWidth, scaledHeight);
        renderer.end();
        GameUtil.disableBlending();
        batch.begin();
    }

    @Override
    public String toString() {
        return "ColorBox(" + colorCode+")";
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public void setProcessed(boolean val){
        this.processed = val;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void bumpAnim(final int newColorCode) {
        float delay = 0.050f*iter;
        this.newColorCode = newColorCode;

        addAction(Actions.sequence(Actions.delay(delay), Actions.scaleTo(1.2f, 1.2f, 0.15f, Interpolation.exp5In), changeColor, Actions.scaleTo(1.0f, 1.0f, 0.15f, Interpolation.exp5Out)));
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setIter(int iter){
        this.iter = iter;
    }

    public int getIter(){
        return iter;
    }
}

