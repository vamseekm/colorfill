package com.cb.colorfill.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.cb.colorfill.elements.ColorBox;
import com.cb.colorfill.elements.ScoreLabel;
import com.cb.colorfill.game.ColorFillGame;
import com.cb.colorfill.game.GameUtil;
import com.cb.colorfill.levels.Level;

import java.util.Vector;


//forumua for max moves
//    maxclick= Math.floor(25*((nrows+ncols)*ncolors)/((14+14)*6));



public class ColorBoardScreen extends com.cb.colorfill.screens.GameScreen {
    private final int boardSize;
    private final Level level;

    ColorBox[][] boxes;
    ColorBox[] controls;
    private int currentColorCode = -1;
    //private int currentMove;
    ScoreLabel scoreLabel;
    boolean gameOver = false;
    boolean animating = false;
    private int originRow;
    private int originCol;

    public ColorBoardScreen(ColorFillGame game, Level level) {
        super(game);
        this.level = level;
        this.boardSize = level.getSize();
        this.originRow = level.getOriginX();
        this.originCol = level.getOriginY();
        //this.currentMove = 0;
        setupBoard();
        setupControls();
        setupScore();
        setupInput();
    }

    private void setupScore() {
        scoreLabel = new ScoreLabel(getGame(), level.getMoves());
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
    private boolean areWeInAnimation(){
        return animating;
    }
    private void fill(int colorCode) {
        //Don't process input while we are currently doing fill animation.
        if(areWeInAnimation()){
            return;
        }
        currentColorCode = boxes[originRow][originCol].getColorCode();
        //currently filled and user selected colors are same.
        if (currentColorCode == colorCode) {
            return;
        }

        //start filling.
        boardProcessed(false);
        currentColorCode = colorCode;
        checkBFS(colorCode);
        scoreLabel.doMove();
    }

    private void checkBFS(int replaceColorCode) {
        int colorCode = boxes[originRow][originCol].getColorCode();
        Vector<ColorBox> colorBoxes = new Vector<ColorBox>();
        colorBoxes.add(boxes[originRow][originCol]);
        int atIndex = 0;
        while(atIndex < colorBoxes.size()){
            ColorBox box = colorBoxes.elementAt(atIndex);
            if(box.isProcessed() == false){
                int row = box.getRow();
                int col = box.getCol();
                box.setProcessed(true);
                if(box.getColorCode() == colorCode) {
                    if(box.getRow() == originRow && box.getCol() == originCol) {
                        box.setColorCode(replaceColorCode);
                    }else{
                        box.bumpAnim(replaceColorCode);
                    }
                    if (row < boardSize - 1 && boxes[row + 1][col].isProcessed() == false) {
                        boxes[row+1][col].setIter(box.getIter()+1);
                        colorBoxes.add(boxes[row + 1][col]);
                    }
                    if (col < boardSize - 1 && boxes[row][col + 1].isProcessed() == false) {
                        boxes[row][col+1].setIter(box.getIter()+1);
                        colorBoxes.add(boxes[row][col + 1]);
                    }
                    if (row > 0 && boxes[row - 1][col].isProcessed() == false) {
                        boxes[row-1][col].setIter(box.getIter()+1);
                        colorBoxes.add(boxes[row - 1][col]);
                    }
                    if (col > 0 && boxes[row][col - 1].isProcessed() == false) {
                        boxes[row][col-1].setIter(box.getIter()+1);
                        colorBoxes.add(boxes[row][col - 1]);
                    }
                }
                if(box.getColorCode() == replaceColorCode){
                    box.bumpAnim(replaceColorCode);

                    if (row < boardSize - 1 && boxes[row + 1][col].isProcessed() == false && boxes[row+1][col].getColorCode() == replaceColorCode) {
                        boxes[row+1][col].setIter(box.getIter()+1);
                        colorBoxes.add(boxes[row + 1][col]);
                    }
                    if (col < boardSize - 1 && boxes[row][col + 1].isProcessed() == false && boxes[row][col + 1].getColorCode() == replaceColorCode) {
                        boxes[row][col+1].setIter(box.getIter()+1);
                        colorBoxes.add(boxes[row][col + 1]);
                    }
                    if (row > 0 && boxes[row - 1][col].isProcessed() == false && boxes[row - 1][col].getColorCode() == replaceColorCode) {
                        boxes[row-1][col].setIter(box.getIter()+1);
                        colorBoxes.add(boxes[row - 1][col]);
                    }
                    if (col > 0 && boxes[row][col - 1].isProcessed() == false && boxes[row][col - 1].getColorCode() == replaceColorCode) {
                        boxes[row][col-1].setIter(box.getIter()+1);
                        colorBoxes.add(boxes[row][col - 1]);
                    }
                }
            }
            atIndex += 1;
        }
    }

    private void boardProcessed(boolean val){
        for(int row=0;row<boardSize;row++){
            for(int col=0;col<boardSize;col++){
                boxes[row][col].setProcessed(val);
                boxes[row][col].setIter(0);
            }
        }
    }

    private void setupControls() {
        ColorFillGame game = getGame();
        int numColors = game.colorUtils.getNumColors();
        float controlSize = (game.gameData.WORLD_WIDTH - BOARD_BORDER_SIZE * 2) / numColors;
        float controlY = bottomMargin() / 2 - controlSize / 2;
        float controlBorderSize = controlSize * 0.1f;
        controls = new ColorBox[numColors];
        for (int i = 0; i < numColors; i++) {
            float controlX = i * controlSize + BOARD_BORDER_SIZE;
            controls[i] = new ColorBox(getGame(), ColorBox.ShapeType.DIAMOND, i);
            controls[i].setBounds(controlX + controlBorderSize, controlY + controlBorderSize, controlSize - controlBorderSize * 2, controlSize - controlBorderSize * 2);
            controls[i].setClickable(true);
            addActor(controls[i]);
        }

    }

    private static final int BOARD_BORDER_SIZE = 10;

    private float boxSize() {
        return (getGame().gameData.WORLD_WIDTH - BOARD_BORDER_SIZE * 2) / boardSize;
    }

    private float bottomMargin() {
        return (getGame().gameData.WORLD_HEIGHT - boardSize * boxSize()) / 2;
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
                float boxX = col * boxSize() + BOARD_BORDER_SIZE;
                float boxY = (boardSize - row - 1) * boxSize() + BOARD_BORDER_SIZE;
                boxes[row][col] = new ColorBox(getGame(), ColorBox.ShapeType.SQUARE, getGame().colorUtils.randomColorCode(), row, col);
                boxes[row][col].setBounds(boxX + boxBorderSize, boxY + boxBorderSize + yOffset, boxSize() - boxBorderSize * 2, boxSize() - boxBorderSize * 2);
                boxes[row][col].setTouchable(Touchable.disabled);
                addActor(boxes[row][col]);
            }
        }
        boxes[originRow][originCol].setShape(ColorBox.ShapeType.STAR);
        boxes[originRow][originCol].foreverBump();
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        int startingColor = boxes[originRow][originCol].getColorCode();
        gameOver = true;
        animating = false;
        for(int row=0;row<boardSize;row++){
            for(int col=0;col<boardSize;col++){
                if(row != originRow && col != originCol){
                    gameOver  = gameOver && boxes[row][col].getColorCode() == startingColor;
                    animating = animating || boxes[row][col].getActions().size > 0;
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(!animating && false){
            GameUtil.saveScreenshot(scoreLabel.getRemainingMoves());
        }
    }
}
