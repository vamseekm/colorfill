package com.cb.colorfill.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.utils.ShortArray;
import com.cb.colorfill.elements.BGRadialGradient;
import com.cb.colorfill.elements.ColorBox;
import com.cb.colorfill.game.ColorFillGame;

/**
 * Created by VamseeKrishna on 002, 2 Jul 2016.
 */
public class TestScreen extends GameScreen {


    public TestScreen(ColorFillGame game) {
        super(game);
        ColorBox colorBox = new ColorBox(getGame(), ColorBox.ShapeType.STAR, getGame().colorUtils.RED, 256, 256);
        colorBox.setSize(512, 512);
        addActor(colorBox);
    }
}
