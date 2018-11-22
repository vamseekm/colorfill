package com.cb.colorfill.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cb.colorfill.game.GameData;
import com.cb.colorfill.game.ColorFillGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		//GameData.setWorldScale(0.5f);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = (int)GameData.WORLD_WIDTH();
		//config.height = (int)GameData.WORLD_HEIGHT();
		config.width = 400;
		config.height = 800;
        config.samples = 4;
		new LwjglApplication(new ColorFillGame(), config);
	}
}
