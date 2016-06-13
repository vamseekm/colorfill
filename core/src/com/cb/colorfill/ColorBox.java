package com.cb.colorfill;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class ColorBox extends Actor {
    private int colorCode;
    private boolean processed = false;

    public ColorBox(int colorCode){
        this.colorCode = colorCode;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        ShapeRenderer renderer = GameData.SHAPE_RENDERER;
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(getX(), getY(), 0);
        Color color = ColorUtils.getColorForCode(colorCode);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        renderer.rect(0, 0, getWidth(), getHeight());
        renderer.end();
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
}
