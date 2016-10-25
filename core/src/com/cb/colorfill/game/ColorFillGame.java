package com.cb.colorfill.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cb.colorfill.levels.Level;
import com.cb.colorfill.levels.classic.ClassicLevel;
import com.cb.colorfill.screens.ColorBoardScreen;
import com.cb.colorfill.screens.GameScreen;
import com.cb.colorfill.screens.GameLostScreen;
import com.cb.colorfill.screens.GameWonScreen;
import com.cb.colorfill.screens.MenuScreen;
import com.cb.colorfill.screens.TestScreen;

public class ColorFillGame extends Game{

	Stage stage;
	BitmapFont font;
	com.cb.colorfill.screens.GameScreen currentScreen;
	public ColorUtils colorUtils;
	public GameData  gameData ;
	private Texture bg;
    private Sound flipSound;

    @Override
	public void create() {
        FreeTypeFontGenerator.setMaxTextureSize(2048);
        initGame();
        setupStage();
		launchMenuScreen();
	}



    private void initGame() {
        Gdx.input.setCatchBackKey(true);

        colorUtils = new ColorUtils();
        gameData = new GameData();
        bg = gameData.getRadialTexture();
        font = new BitmapFont();

    }

    private void setupStage() {
        stage = new Stage(new FitViewport(gameData.WORLD_WIDTH(), gameData.WORLD_HEIGHT()));
        Gdx.input.setInputProcessor(stage);
    }

    private void launchTestScreen() {
        switchScreen(new TestScreen(this));
    }

    private void launchMenuScreen() {
		switchScreen(new MenuScreen(this));
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
        level.setRemainingMoves(2);
        GameWonScreen gameWonScreen = new GameWonScreen(this, level);
        switchScreen(gameWonScreen);
    }

    public void clearBG(){
		//int screenWidth = Gdx.graphics.getWidth();
		//int screenHeight = Gdx.graphics.getHeight();
        /*
        int screenWidth = (int)gameData.WORLD_WIDTH();
        int screenHeight = (int)gameData.WORLD_HEIGHT();
        if(Gdx.graphics.getWidth() > screenWidth){
            screenWidth = Gdx.graphics.getWidth();
        }
        if(Gdx.graphics.getHeight() > screenHeight){
            screenHeight = Gdx.graphics.getHeight();
        }*/
		//Gdx.gl.glViewport(0, 0, screenWidth, screenHeight);
        Viewport viewport = stage.getViewport();
        viewport.apply(true);
		Gdx.gl.glClearColor(250/ 255f, 250/ 255f, 250/ 255f, 1);
		Batch batch = stage.getBatch();
		batch.begin();
		batch.draw(bg, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		batch.end();
	}

	@Override
	public void render() {
        super.render();
		clearBG();
		stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true);
        //int screenWidth = (int)gameData.WORLD_WIDTH();
        //int screenHeight = (int)gameData.WORLD_HEIGHT();
        //stage.getViewport().update(screenWidth, screenHeight, true);
        stage.getViewport().apply(true);
		float delta = Gdx.graphics.getDeltaTime();
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
        stage.getViewport().apply(true);
		//stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
        if(flipSound != null) {
            flipSound.dispose();
        }
		stage.dispose();
	}

    public void switchScreen(GameScreen newScreen) {
        if (currentScreen != newScreen) {
			if (currentScreen != null){
                currentScreen.pause();
                currentScreen.destroyScreen();
            }
            newScreen.getColor().a = 0f;
            currentScreen = newScreen;
            stage.addActor(currentScreen);
            currentScreen.showScreen();
        }
    }

    public void playFlipSound(float pitch){
        if(flipSound == null) {
            flipSound = Gdx.audio.newSound(Gdx.files.internal("audio/tong.wav"));
        }
        flipSound.play(0.8f, pitch, 0);
    }

    public int currentIter = 0;

    public void playFlipForIter(int iter){
        float maxIters  = 20f;
        if(maxIters < ColorBoardScreen.MAX_ITERS){
            maxIters = ColorBoardScreen.MAX_ITERS;
        }
        float maxPitch = 0.5f;
        float perPitch = maxPitch/maxIters;
        float pitch = perPitch*iter;
        if(pitch > maxPitch ){
            pitch = maxPitch;
        }
        pitch += 1.0f;
        playFlipSound(pitch);
        currentIter = iter;
    }

}
