package br.com.gbrvalerio.tiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by gbrva on 3/1/2016.
 */
public class TileFlow {

    private ArrayList<TileLine> lines;
    private final Random rand;

    private final float panelWidth;
    private final float panelHeight;

    private Bitmap clickToStart;

    private boolean flowing;

    public TileFlow(GamePanel panel){
        rand = new Random();
        lines = new ArrayList<>();

        clickToStart = BitmapFactory.decodeResource(panel.getResources(), R.drawable.clicktostart);

        panelWidth = panel.getWidth();
        panelHeight = panel.getHeight();

        for(int i=0; i < 5 ; i++){
            lines.add(new TileLine(TileLine.EMPTY, i, panelWidth, panelHeight, 0));
        }
        lines.add(new TileLine(rand.nextInt(3), 5.0f, panelWidth, panelHeight, 0));
    }

    public void update(){
        synchronized (lines){
            if(lines.size() < 6){
                lines.add(new TileLine(rand.nextInt(3), 5.0f, panelWidth, panelHeight, TileLine.getSpeed()));
            }
            final int size = lines.size();
            for(int i=0; i < size; i++){
                TileLine line = lines.get(i);
                line.update();
                if(line.isCompletelyOffScreen()){
                    if(line.isToRemove()){
                        lines.remove(0);
                        break;
                    } else
                        line.setToRemove(true);
                }
            }
        }
    }

    public void draw(Canvas canvas){

//        if(lines.size() == 6)
        synchronized (lines){
            for(TileLine line : lines) line.draw(canvas);
        }
        if(!flowing){
            //dimensões da img: 600x450 - o calculo é pra centralizar
            canvas.drawBitmap(clickToStart, (panelWidth - 600)/2, (panelHeight-450)/2, null);
        }
    }

    public void verifyClicked(float x, float y){
        if(!flowing){
            flowing = true;
            TileLine.setSpeed(-30);
            return;
        }

        synchronized (lines){
            for(TileLine line : lines){
                if(line.wasClicked(x, y)) break;
            }
        }

    }

}
