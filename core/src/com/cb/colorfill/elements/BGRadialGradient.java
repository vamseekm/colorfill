package com.cb.colorfill.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.cb.colorfill.game.ColorFillGame;

/**
 * Created by VamseeKrishna on 002, 2 Jul 2016.
 */
public class BGRadialGradient extends Actor {

    private final Texture texture;

    public BGRadialGradient(ColorFillGame game){
        this.texture = game.gameData.getRadialTexture();
        this.setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, 0, 0, getWidth(), getHeight());
    }
}
