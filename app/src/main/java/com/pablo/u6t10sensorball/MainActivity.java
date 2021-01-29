package com.pablo.u6t10sensorball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.SoundPool;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;

    private SoundManager soundPool;

    private BallView ballView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ballView = new BallView(this);
        setContentView(ballView);
        //6.1 portait block
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        soundPool = ballView.getSoundManager();


    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(ballView);
        soundPool.liberar();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(ballView,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
        ballView.cargar(getApplicationContext());
    }
}