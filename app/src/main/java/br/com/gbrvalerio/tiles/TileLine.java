package br.com.gbrvalerio.tiles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by gbrva on 3/1/2016.
 */
public class TileLine {

    public static final int LEFT = 0;
    public static final int CENTERLEFT = 1;
    public static final int CENTERRIGHT = 2;
    public static final int RIGHT = 3;

    public static final int TILESPERLINE = 4;
    public static final int MAXLINES = 5;

    private final float panelWidth;
    private final float panelHeight;
    private final float tileWidth;
    private final float tileHeight;

    private boolean toRemove;

    private final int alignment;

    private float YoffSet;
    private float Y;
    private float finalY;

    /*
    0
    1
    2
    3
    4
        0     1        2      3
     left  cntleft cntright right
    6 coming
     */

    public TileLine(int alignment, float pos, float panelWidth, float panelHeight, float YoffSet){
        this.alignment = alignment;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;

        tileHeight = (this.panelHeight / MAXLINES);
        tileWidth = (this.panelWidth / TILESPERLINE);

        Y = tileHeight * pos;
        this.YoffSet = YoffSet;
    }

    public void update(){
        Y += YoffSet;
        finalY = Y + tileHeight;
    }

    public void draw(Canvas canvas){
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        Paint black = new Paint();
        black.setColor(Color.BLACK);

        switch(alignment){
            case LEFT:
                canvas.drawRect(3            , Y, tileWidth     - 3, finalY -3, black);
                canvas.drawRect(tileWidth    , Y, tileWidth * 2 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 2, Y, tileWidth * 3 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 3, Y, panelWidth    - 3, finalY -3, white);
                break;
            case CENTERLEFT:
                canvas.drawRect(3            , Y, tileWidth     - 3, finalY -3, white);
                canvas.drawRect(tileWidth    , Y, tileWidth * 2 - 3, finalY -3, black);
                canvas.drawRect(tileWidth * 2, Y, tileWidth * 3 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 3, Y, panelWidth    - 3, finalY -3, white);
                break;
            case CENTERRIGHT:
                canvas.drawRect(3            , Y, tileWidth     - 3, finalY -3, white);
                canvas.drawRect(tileWidth    , Y, tileWidth * 2 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 2, Y, tileWidth * 3 - 3, finalY -3, black);
                canvas.drawRect(tileWidth * 3, Y, panelWidth    - 3, finalY -3, white);
                break;
            case RIGHT:
                canvas.drawRect(3            , Y, tileWidth     - 3, finalY -3, white);
                canvas.drawRect(tileWidth    , Y, tileWidth * 2 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 2, Y, tileWidth * 3 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 3, Y, panelWidth    - 3, finalY -3, black);
                break;
        }
    }

    public boolean isCompletelyOffScreen(){
        return finalY < 0;
    }

    public void setToRemove(boolean toRemove){
        this.toRemove = toRemove;
    }

    public boolean isToRemove() {
        return toRemove;
    }
}
