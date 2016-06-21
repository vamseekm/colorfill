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

import java.util.Queue;
import java.util.Vector;


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
        scoreLabel = new ScoreLabel(getGame());
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
        //checkBox(0, 0, boxes[0][0].getColorCode(), colorCode, 1);
        checkBFS(0, 0, colorCode);
    }

    private void checkBFS(int orow, int ocol, int replaceColorCode) {
        int colorCode = boxes[orow][ocol].getColorCode();
        Vector<ColorBox> colorBoxes = new Vector<ColorBox>();
        colorBoxes.add(boxes[orow][ocol]);
        int atIndex = 0;
        while(atIndex < colorBoxes.size()){
            ColorBox box = colorBoxes.elementAt(atIndex);
            if(box.isProcessed() == false){
                int row = box.getRow();
                int col = box.getCol();
                System.out.println("Procesisng:" + row+"/"+col);
                System.out.println("looking for cc:" + colorCode+" found " + box.getColorCode());
                box.setProcessed(true);
                if(box.getColorCode() == colorCode) {
                    //box.setColorCode(replaceColorCode);
                    box.bumpAnim(replaceColorCode);
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
            /*
            for(int i=0;i<colorBoxes.size();i++){
                ColorBox colorBox = colorBoxes.elementAt(i);
                System.out.println("In queue:" + colorBox.getRow()+"/" + colorBox.getCol());
            }*/
            atIndex += 1;
        }
    }

    private int distance(int x1, int y1, int x2, int y2){
        return (int)Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }

    private void boardProcessed(boolean val){
        for(int row=0;row<boardSize;row++){
            for(int col=0;col<boardSize;col++){
                boxes[row][col].setProcessed(val);
                boxes[row][col].setIter(0);
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
            boxes[row][col].bumpAnim(replaceColorCode);
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
        ColorFillGame game = getGame();
        int numColors = game.colorUtils.getNumColors();
        float controlSize = (game.gameData.WORLD_WIDTH - BOARD_BORDER_SIZE * 2) / numColors;
        float controlY = bottomMargin() / 2 - controlSize / 2;
        float controlBorderSize = controlSize * 0.1f;
        controls = new ColorBox[numColors];
        for (int i = 0; i < numColors; i++) {
            float controlX = i * controlSize + BOARD_BORDER_SIZE;
            controls[i] = new ColorBox(getGame(), i);
            controls[i].setBounds(controlX + controlBorderSize, controlY + controlBorderSize, controlSize - controlBorderSize * 2, controlSize - controlBorderSize * 2);
            controls[i].setDiamond(true);
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
                float boxX = row * boxSize() + BOARD_BORDER_SIZE;
                float boxY = (boardSize - col - 1) * boxSize() + BOARD_BORDER_SIZE;
                boxes[row][col] = new ColorBox(getGame(), getGame().colorUtils.randomColorCode(), row, col);
                boxes[row][col].setBounds(boxX + boxBorderSize, boxY + boxBorderSize + yOffset, boxSize() - boxBorderSize * 2, boxSize() - boxBorderSize * 2);
                boxes[row][col].setTouchable(Touchable.disabled);
                addActor(boxes[row][col]);
            }
        }

        boxes[0][0].setDiamond(true);
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
