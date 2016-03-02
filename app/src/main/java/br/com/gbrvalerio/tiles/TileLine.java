package br.com.gbrvalerio.tiles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by gbrva on 3/1/2016.
 */
public class TileLine {

    private static int points = 0;

    public static final int LEFT = 0;
    public static final int CENTERLEFT = 1;
    public static final int CENTERRIGHT = 2;
    public static final int RIGHT = 3;
    public static final int EMPTY = 4;

    public static final int TILESPERLINE = 4;
    public static final int MAXLINES = 5;

    private final float panelWidth;
    private final float panelHeight;
    private final float tileWidth;
    private final float tileHeight;

    private boolean toRemove;
    private boolean clicked;

    private final int alignment;

    private static float speed;
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
        TileLine.speed = YoffSet;
    }

    public void update(){
        Y += TileLine.speed;
        finalY = Y + tileHeight;
    }

    public static void setSpeed(float speed){
        TileLine.speed = speed;
    }

    public static float getSpeed(){
        return TileLine.speed;
    }

    public void draw(Canvas canvas){
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        Paint black = new Paint();
        black.setColor(Color.BLACK);
        Paint gray = new Paint();
        gray.setColor(Color.GRAY);

        switch(alignment){
            case LEFT:
                canvas.drawRect(3            , Y, tileWidth     - 3, finalY -3, (wasClicked() ? gray : black));
                canvas.drawRect(tileWidth    , Y, tileWidth * 2 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 2, Y, tileWidth * 3 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 3, Y, panelWidth    - 3, finalY -3, white);
                break;
            case CENTERLEFT:
                canvas.drawRect(3            , Y, tileWidth     - 3, finalY -3, white);
                canvas.drawRect(tileWidth    , Y, tileWidth * 2 - 3, finalY -3, (wasClicked() ? gray : black));
                canvas.drawRect(tileWidth * 2, Y, tileWidth * 3 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 3, Y, panelWidth    - 3, finalY -3, white);
                break;
            case CENTERRIGHT:
                canvas.drawRect(3            , Y, tileWidth     - 3, finalY -3, white);
                canvas.drawRect(tileWidth    , Y, tileWidth * 2 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 2, Y, tileWidth * 3 - 3, finalY -3, (wasClicked() ? gray : black));
                canvas.drawRect(tileWidth * 3, Y, panelWidth    - 3, finalY -3, white);
                break;
            case RIGHT:
                canvas.drawRect(3            , Y, tileWidth     - 3, finalY -3, white);
                canvas.drawRect(tileWidth    , Y, tileWidth * 2 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 2, Y, tileWidth * 3 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 3, Y, panelWidth    - 3, finalY -3, (wasClicked() ? gray : black));
                break;
            case EMPTY:
                canvas.drawRect(3            , Y, tileWidth     - 3, finalY -3, white);
                canvas.drawRect(tileWidth    , Y, tileWidth * 2 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 2, Y, tileWidth * 3 - 3, finalY -3, white);
                canvas.drawRect(tileWidth * 3, Y, panelWidth    - 3, finalY -3, white);
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

    public boolean wasClicked(float x, float y){
        if(y >= Y && y <= finalY)
            switch (alignment) {
                case LEFT:
                    if (x >= 0 && x <= tileWidth) {
                        setClicked(true);
                        return true;
                    }
                    break;
                case CENTERLEFT:
                    if (x >= tileWidth && x <= tileWidth * 2) {
                        setClicked(true);
                        return true;
                    }
                    break;
                case CENTERRIGHT:
                    if (x >= tileWidth * 2 && x <= tileWidth * 3) {
                        setClicked(true);
                        return true;
                    }
                    break;
                case RIGHT:
                    if (x >= tileWidth * 3 && x <= panelWidth) {
                        setClicked(true);
                        return true;
                    }
                    break;
            }
        return false;
    }

    public boolean wasClicked(){
        return clicked;
    }

    private void setClicked(boolean clicked){
        if(wasClicked()) return;
        points++;
        setSpeed(-30 - (points/2.0f));
        this.clicked = clicked;
        System.out.println("pts: "+ points + " | speed: "+ TileLine.speed);
    }

    public static int getPoints(){
        return points;
    }
}
