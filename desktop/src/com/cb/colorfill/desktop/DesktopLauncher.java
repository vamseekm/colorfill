package com.cb.colorfill.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cb.colorfill.game.GameData;
import com.cb.colorfill.game.ColorFillGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int)GameData.WORLD_WIDTH;
		config.height = (int)GameData.WORLD_HEIGHT;
        config.samples = 4;
		new LwjglApplication(new ColorFillGame(), config);
	}
}
