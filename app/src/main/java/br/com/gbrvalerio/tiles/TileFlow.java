package br.com.gbrvalerio.tiles;

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

    public TileFlow(GamePanel panel){
        rand = new Random();
        lines = new ArrayList<>();


        panelWidth = panel.getWidth();
        panelHeight = panel.getHeight();

        for(int i=0; i < 5 ; i++){
            lines.add(new TileLine(TileLine.EMPTY, i, panelWidth, panelHeight, -20));
        }
        lines.add(new TileLine(rand.nextInt(3), 5.0f, panelWidth, panelHeight, -20));
    }

    public void update(){
        synchronized (lines){
            if(lines.size() < 6){
                lines.add(new TileLine(rand.nextInt(3), 5.0f, panelWidth, panelHeight, -20));
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
    }

    public void verifyClicked(float x, float y){
        synchronized (lines){
            for(TileLine line : lines){
                if(line.wasClicked(x, y)) break;
            }
        }

    }

}
