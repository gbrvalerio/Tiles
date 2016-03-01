package br.com.gbrvalerio.tiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by gbrva on 3/1/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private final GameThread thread;
    private TileFlow flow;
    
    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);

        thread = new GameThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
        flow = new TileFlow(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.stopGame();
    }

    public void update() {
        flow.update();
    }

    @Override
    public void draw(Canvas canvas) {

        final float scaleFactorX = getWidth() / (canvas.getWidth()* 1.0f);
        final float scaleFactorY = getHeight() / (canvas.getHeight() * 1.0f);

        if(canvas != null){
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            flow.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }
}
