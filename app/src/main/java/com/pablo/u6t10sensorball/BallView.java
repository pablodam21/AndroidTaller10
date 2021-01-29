package com.pablo.u6t10sensorball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;

public class BallView extends View implements SensorEventListener {

    private Ball ball;
    private Paint pen;

    private int with, height;

    SoundManager soundManager;

    Bitmap bitmapBall;

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public BallView(Context context) {
        super(context);

        ball = new Ball();
        //6.2 load background image
        setBackgroundResource(R.drawable.fondo);

        pen = new Paint();

        soundManager = new SoundManager();
        soundManager.initSoundManager(context);
        soundManager.addSound(1,R.raw.ballbounce);
        //6.4 sound plus
        soundManager.addSound(2,R.raw.cartonsound);
        soundManager.addSound(3,R.raw.jabsound);
        soundManager.addSound(4,R.raw.jumpsound);
        //6.4 take the resouces for bitmap
        bitmapBall = BitmapFactory.decodeResource(getResources(),R.drawable.chopper);
    }

    private double map(double value, double o1, double o2, double d1, double d2){
        double result;
        double scale = (d2-d1) / (o2-o1);

        result = (value - o1) * scale + d1;

        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        with = w;
        height = h;

        ball.x = w / 2;
        ball.y = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        pen.setColor(Color.BLUE);
//          pen.setStrokeWidth(1);
//          pen.setStyle(Paint.Style.FILL);



        //canvas.drawCircle((float) ball.x,(float) ball.y, ball.DIAMETER,pen);
        //6.3 draw bitmapball
        canvas.drawBitmap(bitmapBall,(float) ball.x,(float) ball.y, pen);

    }

    protected void updatePhysics(){
        boolean collision = false;
        //6.3 charge the bitball whith
        if (ball.x + bitmapBall.getWidth() > with - 1)  {
            ball.vx = -ball.vx / 2.0;
            ball.x = with - bitmapBall.getWidth();
            collision = true;
        }
        if (ball.x - ball.DIAMETER / 2 < 0) {
            ball.vx = -ball.vx / 2.0;
            ball.x = ball.DIAMETER / 2;
            collision = true;
        }
        if (ball.y + bitmapBall.getHeight() > height - 1)  {
            ball.vy *= -1;
            ball.y = height - bitmapBall.getHeight();
            collision = true;
        }
        if (ball.x - ball.DIAMETER / 2 < 0)  {
            ball.vy *= -1;
            ball.y = ball.DIAMETER / 2;
            collision = true;
        }
        if (collision) soundManager.playSound(1);

        ball.vy += ball.GRAVITY;
        ball.vx += ball.ax;

        ball.x += ball.vx;
        ball.y += ball.vy;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            double value = event.values[0];

            if (value > 5) value = 5;
            if (value < -5) value = -5;

            if (value < 0.1 && value > -0.1)
                ball.ax  = 0.0;
            else {
                ball.ax = map(Math.abs(value),0.1,5,0.05,0.5);
                if (value < 0.0) ball.ax *= -1;
            }
            ball.ax *= -1;

            updatePhysics();

            invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    //6.4.2 char resources
    public void cargar(Context context){
        soundManager = new SoundManager();
        soundManager.initSoundManager(context);

        soundManager.addSound(1,R.raw.ballbounce);
        soundManager.addSound(2,R.raw.cartonsound);
        soundManager.addSound(3,R.raw.jabsound);
        soundManager.addSound(4,R.raw.jumpsound);

    }
}
