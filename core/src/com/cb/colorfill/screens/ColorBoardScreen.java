package com.cb.colorfill.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.cb.colorfill.elements.ColorBox;
import com.cb.colorfill.elements.ScoreLabel;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.ColorUtils;
import com.cb.colorfill.game.GameData;
import com.cb.colorfill.game.GameUtil;


public class ColorBoardScreen extends com.cb.colorfill.screens.GameScreen {
    private final int boardSize;

    ColorBox[][] boxes;
    ColorBox[] controls;
    private int currentColorCode = -1;
    //private int currentMove;
    ScoreLabel scoreLabel;

    public ColorBoardScreen(ColorFillGame game, int boardSize) {
        super(game);
        this.boardSize = boardSize;
        //this.currentMove = 0;
        setupBoard();
        setupControls();
        setupScore();
        setupInput();
    }

    private void setupScore() {
        scoreLabel = new ScoreLabel();
        float xPos = 0;
        float yPos = bottomMargin()*1.5f + boxSize()*boardSize + scoreLabel.getHeight();
        scoreLabel.setPosition(xPos, yPos);
        addActor(scoreLabel);
    }

    private void setupInput() {
        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ColorBox box = (ColorBox) hit(x, y, true);
                int colorCode = box.getColorCode();
                fill(colorCode);
                return true;
            }
        });
    }

    private void fill(int colorCode) {
        currentColorCode = boxes[0][0].getColorCode();
        if (currentColorCode == colorCode) {
            return;
        }
        boardProcessed(false);
        scoreLabel.increaseMove();
        currentColorCode = colorCode;
        checkBox(0, 0, boxes[0][0].getColorCode(), colorCode, 1);
    }

    private void boardProcessed(boolean val){
        for(int row=0;row<boardSize;row++){
            for(int col=0;col<boardSize;col++){
                boxes[row][col].setProcessed(val);
            }
        }
    }

    private void checkBox(int row, int col, int colorCode, int replaceColorCode, int iter) {
        if(boxes[row][col].isProcessed()){
            return;
        }
        if (boxes[row][col].getColorCode() == colorCode){
            boxes[row][col].setColorCode(replaceColorCode);
            boxes[row][col].setProcessed(true);;
        }else{
            return;
        }
        if(row < boardSize - 1){
            checkBox(row+1, col, colorCode, replaceColorCode, iter+1);
        }
        if(col < boardSize - 1){
            checkBox(row, col+1, colorCode, replaceColorCode, iter+1);
        }
        if(row > 0){
            checkBox(row-1, col, colorCode, replaceColorCode, iter+1);
        }
        if(col > 0){
            checkBox(row, col-1, colorCode, replaceColorCode, iter+1);
        }
    }



    private void setupControls() {
        int numColors = ColorUtils.getNumColors();
        float controlSize = (GameData.WORLD_WIDTH - BOARD_BORDER_SIZE * 2) / numColors;
        float controlY = bottomMargin() / 2 - controlSize / 2;
        float controlBorderSize = controlSize * 0.1f;
        controls = new ColorBox[numColors];
        for (int i = 0; i < numColors; i++) {
            float controlX = i * controlSize + BOARD_BORDER_SIZE;
            controls[i] = new ColorBox(i);
            controls[i].setBounds(controlX + controlBorderSize, controlY + controlBorderSize, controlSize - controlBorderSize * 2, controlSize - controlBorderSize * 2);
            addActor(controls[i]);
        }

    }

    private static final int BOARD_BORDER_SIZE = 10;

    private float boxSize() {
        return (GameData.WORLD_WIDTH - BOARD_BORDER_SIZE * 2) / boardSize;
    }

    private float bottomMargin() {
        return (GameData.WORLD_HEIGHT - boardSize * boxSize()) / 2;
    }

    private float boxBorderSize() {
        return boxSize() * 0.05f;
    }

    private void setupBoard() {
        boxes = new ColorBox[boardSize][];
        float yOffset = bottomMargin();
        float boxBorderSize = boxBorderSize();

        for (int row = 0; row < boardSize; row++) {
            boxes[row] = new ColorBox[boardSize];
            for (int col = 0; col < boardSize; col++) {
                float boxX = row * boxSize() + BOARD_BORDER_SIZE;
                float boxY = (boardSize - col - 1) * boxSize() + BOARD_BORDER_SIZE;
                boxes[row][col] = new ColorBox(ColorUtils.randomColorCode());
                boxes[row][col].setBounds(boxX + boxBorderSize, boxY + boxBorderSize + yOffset, boxSize() - boxBorderSize * 2, boxSize() - boxBorderSize * 2);
                boxes[row][col].setTouchable(Touchable.disabled);
                addActor(boxes[row][col]);
            }
        }
    }

    private static GlyphLayout glyphLayout = new GlyphLayout();

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        /*
        System.out.println(parentAlpha);
        GameUtil.enableBlending();
        //System.out.println(parentAlpha);
        String text = currentMove + "";
        BitmapFont scoreFont = GameData.GetBigFont();
        Color fontColor = GameData.FONT_COLOR;
        scoreFont.setColor(fontColor.r, fontColor.g, fontColor.b, parentAlpha);
        glyphLayout.setText(scoreFont, text);
        float xPos = GameData.WORLD_WIDTH/2 - glyphLayout.width/2;
        float yPos = bottomMargin()*1.5f + boxSize()*boardSize + glyphLayout.height/2;
        //System.out.println(glyphLayout.width+","+glyphLayout.height);
        scoreFont.draw(batch, text, xPos , yPos);
        GameUtil.disableBlending();
        */
    }
}
