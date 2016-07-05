package com.cb.colorfill.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cb.colorfill.levels.Level;
import com.cb.colorfill.levels.classic.ClassicLevel;
import com.cb.colorfill.screens.GameScreen;
import com.cb.colorfill.screens.GameLostScreen;
import com.cb.colorfill.screens.GameWonScreen;

public class ColorFillGame extends Game{

	Stage stage;
	BitmapFont font;
	com.cb.colorfill.screens.GameScreen currentScreen;
	public ColorUtils colorUtils;
	public GameData  gameData ;
	private Texture bg;

	@Override
	public void create() {
		colorUtils = new ColorUtils();
		gameData = new GameData();
		bg = gameData.getRadialTexture();
		font = new BitmapFont();
		stage = new Stage(new FitViewport(gameData.WORLD_WIDTH, gameData.WORLD_HEIGHT));
		//currentScreen = new ColorBoardScreen(this, 9);
		//switchScreen(new ColorBoardScreen(this, 9));
        //testGameLostScreen();
        testGameWonScreen();
        //switchScreen(new MenuScreen(this));
		//switchScreen(new TestScreen(this));
		//switchScreen(currentScreen);
		Gdx.input.setInputProcessor(stage);
	}

    private void testGameLostScreen() {
        Level level = ClassicLevel.createLevel(ClassicLevel.Difficulty.EASY);
        level.setWon(false);
        level.setRemainingMoves(1);
        GameLostScreen gameoverScreen = new GameLostScreen(this, level);
        switchScreen(gameoverScreen);
    }

    private void testGameWonScreen() {
        Level level = ClassicLevel.createLevel(ClassicLevel.Difficulty.EASY);
        level.setWon(true);
        level.setRemainingMoves(10);
        GameWonScreen gameWonScreen = new GameWonScreen(this, level);
        switchScreen(gameWonScreen);
    }

    public void clearBG(){
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();
		Gdx.gl.glViewport(0, 0, screenWidth, screenHeight);
		Gdx.gl.glClearColor(250/ 255f, 250/ 255f, 250/ 255f, 1);
		Batch batch = stage.getBatch();
		batch.begin();
		batch.draw(bg, 0, 0, screenWidth, screenHeight);
		batch.end();
	}

	@Override
	public void render() {
        super.render();
		clearBG();
		stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true);
		float delta = Gdx.graphics.getDeltaTime();
		stage.act(delta);
		stage.draw();
        /*
		Batch batch = stage.getBatch();
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, gameData.WORLD_HEIGHT);
		batch.end();
		*/
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
            newScreen.getColor().a = 0f;
            currentScreen = newScreen;
            stage.addActor(currentScreen);
            currentScreen.showScreen();

        }
    }
}
