package com.cb.colorfill.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cb.colorfill.game.ColorUtils;
import com.cb.colorfill.game.GameData;
import com.cb.colorfill.game.GameUtil;


public class ColorBox extends Actor {
    private int colorCode;
    private boolean processed = false;

    public ColorBox(int colorCode){
        this.colorCode = colorCode;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //System.out.println(parentAlpha);
        batch.end();
        GameUtil.enableBlending();
        ShapeRenderer renderer = GameData.SHAPE_RENDERER;
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(getX(), getY(), 0);
        Color color = ColorUtils.getColorForCode(colorCode);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        //renderer.setColor(color);
        renderer.setColor(color.r, color.g, color.b, parentAlpha);
        renderer.rect(0, 0, getWidth(), getHeight());
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
}
