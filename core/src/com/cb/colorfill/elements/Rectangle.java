package com.cb.colorfill.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.GraphicsUtil;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by VamseeKrishna on 014, 14 Oct 2016.
 */

public class Rectangle extends Actor {

    private final ColorFillGame game;
    private Color buttonColor;

    public Rectangle(ColorFillGame game, int width, int height, int colorCode) {
        this(game, width, height, game.colorUtils.getColorForCode(colorCode));
    }

    public Rectangle(ColorFillGame game, int width, int height, Color buttonColor){
        this.game = game;
        this.setWidth(width);
        this.setHeight(height);
        this.buttonColor = buttonColor;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        batch.end();
        setOrigin(500, 500);
        float scaleX = getScaleX();
        float scaleY = getScaleY();
        float width = getWidth();
        float height = getHeight();
        float scaledWidth = width*scaleX;
        float scaledHeight = height*scaleY;
        float posX = getX() -  (scaledWidth - width)/2;
        float posY = getY() - (scaledHeight - height)/2;

        GraphicsUtil.enableBlending();
        ShapeRenderer renderer = game.gameData.SHAPE_RENDERER;
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(posX + scaledWidth/2, posY + scaledHeight/2, 0);
        posX = -scaledWidth/2;
        posY = -scaledHeight/2;
        renderer.rotate(0f, 0f, 1f, getRotation());
        Color color = getColor();
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(color.r, color.g, color.b, parentAlpha);
        renderer.rect(posX, posY, scaledWidth, scaledHeight);

        renderer.end();
        GraphicsUtil.disableBlending();
        batch.begin();
    }


}
