package com.cb.colorfill.screens;

import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.levels.Level;


public class GameWonScreen extends GameScreen {

    private final Level level;

    public GameWonScreen(ColorFillGame game, Level level){
        super(game);
        this.level = level;
    }
}
