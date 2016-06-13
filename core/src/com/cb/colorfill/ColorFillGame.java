package com.cb.colorfill;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ColorFillGame extends Game{

	Stage stage;
	ColorBoard board;
	BitmapFont font;

	@Override
	public void create() {
		font = new BitmapFont();
		stage = new Stage(new FitViewport(GameData.WORLD_WIDTH, GameData.WORLD_HEIGHT));
		board = new ColorBoard(8, 25);
		stage.addActor(board);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render() {
		//Gdx.gl.glClearColor(180 / 255f, 197 / 255f, 195 / 255f, 1);
		//Gdx.gl.glClearColor(200 / 255f, 200 / 255f, 200 / 255f, 1);
		//Gdx.gl.glClearColor(243/ 255f, 242/ 255f, 241/ 255f, 1);
		Gdx.gl.glClearColor(250/ 255f, 250/ 255f, 250/ 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float delta = Gdx.graphics.getDeltaTime();
		stage.act(delta);
		stage.draw();

		Batch batch = stage.getBatch();
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, GameData.WORLD_HEIGHT);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
