package com.cb.colorfill.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cb.colorfill.screens.ColorBoardScreen;
import com.cb.colorfill.screens.GameScreen;
import com.cb.colorfill.screens.MenuScreen;

public class ColorFillGame extends Game{

	Stage stage;
	BitmapFont font;
	com.cb.colorfill.screens.GameScreen currentScreen;

	@Override
	public void create() {
		font = new BitmapFont();
		stage = new Stage(new FitViewport(GameData.WORLD_WIDTH, GameData.WORLD_HEIGHT));
		//currentScreen = new ColorBoardScreen(this, 8);
        switchScreen(new MenuScreen(this));
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render() {
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();
		Color brightColor = Color.WHITE;
		Color darkColor = GameData.BG_DARK_COLOR;

		Gdx.gl.glViewport(0, 0, screenWidth, screenHeight);
		Gdx.gl.glClearColor(250/ 255f, 250/ 255f, 250/ 255f, 1);

		ShapeRenderer shapeRenderer = GameData.SHAPE_RENDERER;
		shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        shapeRenderer.setTransformMatrix(stage.getBatch().getTransformMatrix());
        shapeRenderer.translate(0, 0, 0);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(0, 0, screenWidth, screenHeight, darkColor, darkColor, brightColor, brightColor);

		shapeRenderer.end();

		stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true);
		float delta = Gdx.graphics.getDeltaTime();
		stage.act(delta);
		stage.draw();
		Batch batch = stage.getBatch();
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, GameData.WORLD_HEIGHT);
		batch.end();

		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

    public void switchScreen(GameScreen newScreen) {
        if (currentScreen != newScreen) {
            if (currentScreen != null){
                currentScreen.destroyScreen();
            }
            currentScreen = newScreen;
            stage.addActor(currentScreen);
            currentScreen.showScreen();
        }
    }
}
