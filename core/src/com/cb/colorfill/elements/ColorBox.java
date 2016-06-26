package com.cb.colorfill.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.ColorUtils;
import com.cb.colorfill.game.GameData;
import com.cb.colorfill.game.GameUtil;


public class ColorBox extends Actor {

    private Color buttonColor;

    public enum ShapeType {
        DIAMOND,
        SQUARE,
        STAR,
    }
    private ShapeType shape;
    private final int row;
    private final int col;
    private final ColorFillGame game;
    private int iter = 0;
    private int colorCode;
    private boolean processed = false;
    private boolean diamond = false;
    private int newColorCode;
    Action changeColor;

    public ColorBox(ColorFillGame game, ShapeType shape, int colorCode, int row, int col){
        this(game, shape, game.colorUtils.getColorForCode(colorCode), row, col);
        this.colorCode = colorCode;
        /*
        this.game = game;
        this.shape = shape;
        this.colorCode = colorCode;
        this.row = row;
        this.col = col;
        changeColor = new Action(){
            public boolean act(float delta){
                setColorCode(newColorCode);
                return true;
            }
        };*/
    }

    public ColorBox(ColorFillGame game, int colorCode){
        this(game, ShapeType.SQUARE, game.colorUtils.getColorForCode(colorCode), -1, -1);
        this.colorCode = colorCode;
    }
    public ColorBox(ColorFillGame game, ShapeType shape, int colorCode){
        this(game, shape, game.colorUtils.getColorForCode(colorCode), -1, -1);
        this.colorCode = colorCode;
    }

    public ColorBox(ColorFillGame game, ShapeType shape, Color buttonColor, int row, int col){
        this.game = game;
        this.shape = shape;
        this.buttonColor = buttonColor;
        this.row = row;
        this.col = col;
         changeColor = new Action(){
            public boolean act(float delta){
                setColorCode(newColorCode);
                return true;
            }
        };
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
        //Color color = game.colorUtils.getColorForCode(colorCode);
        Color color = buttonColor;
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color.r, color.g, color.b, parentAlpha);
        if(shape == ShapeType.DIAMOND || shape == ShapeType.STAR) {
            renderer.triangle(
                    //bottom
                    posX + scaledWidth / 2, posY,
                    //right
                    posX + scaledWidth, posY + scaledHeight / 2,
                    //top
                    posX + scaledWidth / 2, posY + scaledHeight
            );
            renderer.triangle(
                    //bottom
                    posX + scaledWidth / 2, posY,
                    //left
                    posX, posY + scaledHeight / 2,
                    //top
                    posX + scaledWidth / 2, posY + scaledHeight
            );
        }

        if(shape == ShapeType.STAR){
            float starLength = (float)Math.sqrt(Math.pow(scaledWidth/2, 2) + Math.pow(scaledHeight/2, 2));
            float offsetLength = (scaledWidth - starLength)/2;
            renderer.rect(posX + offsetLength, posY + offsetLength, starLength, starLength);
        }

        if (shape == ShapeType.SQUARE){
            renderer.rect(posX, posY, scaledWidth, scaledHeight);
        }
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
        this.buttonColor = game.colorUtils.getColorForCode(colorCode);
    }

    public void setProcessed(boolean val){
        this.processed = val;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void bumpAnim(final int newColorCode) {
        float delay = 0.075f*iter;
        this.newColorCode = newColorCode;

        addAction(
            Actions.sequence(
                Actions.delay(delay),
                Actions.scaleTo(1+SCALE_VALUE, 1+SCALE_VALUE, BUMP_DURATION, Interpolation.exp10In),
                changeColor,
                Actions.scaleTo(1.0f         , 1.0f         , BUMP_DURATION, Interpolation.exp10Out)
            )
        );
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

    private static final float BUMP_DURATION = 0.3f;
    private static final float SCALE_VALUE = 0.2f;

    public void foreverBump(){
        addAction(
            Actions.forever(
                Actions.sequence(
                    Actions.scaleTo(1 + SCALE_VALUE, 1 + SCALE_VALUE, BUMP_DURATION, Interpolation.exp10In),
                    Actions.scaleTo(1 - SCALE_VALUE, 1 - SCALE_VALUE, BUMP_DURATION, Interpolation.exp10Out),
                    Actions.delay(BUMP_DURATION*4)
                )
            )
        );
    }


    public void setShape(ShapeType shape){
        this.shape = shape;
    }

    private boolean clickable = false;
    public void setClickable(boolean value) {
        if(isClickable() == false){
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
        this.clickable = value;
    }

    private void pressDown(){
        addAction(Actions.scaleTo(1 - SCALE_VALUE, 1 - SCALE_VALUE));
    }

    private void pressUp(){
        addAction(
                Actions.sequence(
                        Actions.scaleTo(1, 1, BUMP_DURATION, Interpolation.exp10Out)
                )
        );

    }


    public boolean isClickable(){
        return this.clickable;
    }

}

