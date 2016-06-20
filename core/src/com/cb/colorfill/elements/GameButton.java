package com.cb.colorfill.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.cb.colorfill.game.GameData;

/**
 * Created by VamseeKrishna on 020, 20 Jun 2016.
 */
public class GameButton extends Actor {
    private final String text;
    public GameButton(String text){
        this.text = text;
        BitmapFont scoreFont = GameData.GetGameFont();
        glyphLayout.setText(scoreFont, text);
        setSize(glyphLayout.width, glyphLayout.height);
        setupEvents();
    }

    private void setupEvents() {
        this.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pressDown();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pressUp();
            }
        });
    }

    private void pressDown(){
        ScaleToAction scaleToAction = new ScaleToAction();
        scaleToAction.setScale(1.1f);
        scaleToAction.setDuration(0.15f);
        scaleToAction.setInterpolation(Interpolation.exp5In);
        addAction(scaleToAction);
        System.out.println("Press down");
    }

    private void pressUp(){
        ScaleToAction scaleToAction = new ScaleToAction();
        scaleToAction.setScale(1.0f);
        scaleToAction.setDuration(0.3f);
        scaleToAction.setInterpolation(Interpolation.bounceOut);
        addAction(scaleToAction);
        System.out.println("press up");
    }

    private static GlyphLayout glyphLayout = new GlyphLayout();

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        BitmapFont scoreFont = GameData.GetGameFont();

        float width        = getWidth();
        float height       = getHeight();
        float scaledWidth  = width*getScaleX();
        float scaledHeight = height*getScaleY();
        float widthDiff    = scaledWidth - width;
        float xPos = getX() -  widthDiff/2;
        float yPos = getY() + scaledHeight;

        scoreFont.getData().setScale(getScaleX(), getScaleY());
        scoreFont.draw(batch, text, xPos, yPos);

        /*
        System.out.println("Pos:        " + xPos + "/" + yPos);
        System.out.println("Size:       " + getWidth() + "/" + getHeight());
        System.out.println("Scaled Size:" + getWidth()*getScaleX() + "/" + getHeight()*getScaleY());
        System.out.println("Diff:       " + widthDiff + "/"+ heightDiff);
        System.out.println("Origin:     " + getOriginX() + "/" + getOriginY());
        //Matrix4 transformMatrix = batch.getTransformMatrix();
        transformMatrix.idt();
        //transformMatrix.scale(getScaleX(), getScaleY(), 1);
        transformMatrix.translate(-getOriginX(), -getOriginY(), 0);
        //transformMatrix.translate(-widthDiff, -heightDiff, 0);
        //transformMatrix.translate(getWidth()/2, getHeight()/2, 0);
        //transformMatrix.translate(scaledWidth/2, scaledHeight/2, 0);
        batch.setTransformMatrix(transformMatrix);
        //System.out.println(getScaleX() + "/" +  getScaleY());
        //System.out.println(getWidth()+"/"+getHeight());
        batch.end();
        ShapeRenderer shapeRenderer = GameData.SHAPE_RENDERER;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getX() - widthDiff/2, getY() - heightDiff/2, scaledWidth, scaledHeight);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.circle(xPos, yPos, 10);
        shapeRenderer.end();
        batch.begin();
        */

    }
}

