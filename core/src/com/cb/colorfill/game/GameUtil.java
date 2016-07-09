package com.cb.colorfill.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Created by VamseeKrishna on 027, 27 Jun 2016.
 */
public class GameUtil {
    public static int maxMoves(int rows, int cols, int ncolors){
        return (int)Math.floor(25.0f*((rows+cols)*ncolors)/((14+14)*6));
    }

    public static void saveScreenshot(int move){
        String fileName = "move-" + move + ".png";
        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
        Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
        PixmapIO.writePNG(Gdx.files.local(fileName), pixmap);
        pixmap.dispose();
    }

}
