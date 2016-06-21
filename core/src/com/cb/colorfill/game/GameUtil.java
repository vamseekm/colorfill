package com.cb.colorfill.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by VamseeKrishna on 020, 20 Jun 2016.
 */
public class GameUtil {
    public static void enableBlending(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void disableBlending(){
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
