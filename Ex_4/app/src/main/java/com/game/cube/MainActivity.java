package com.game.cube;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private static final int SHAKE_THRESHOLD = 3;
    List<ImageView> imageViews;
    private int numberOfCubes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        numberOfCubes = 1;
        initImageViews();
        initSensor();
        clearImageViews();
    }

    private void initImageViews() {
        imageViews = new ArrayList<>();
        imageViews.add(findViewById(R.id.first_cube));
        imageViews.add(findViewById(R.id.second_cube));
        imageViews.add(findViewById(R.id.third_cube));
    }

    private void initSensor() {
        mSensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        if (mSensorManager != null) {
            mAccelerometer =
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    private void generateRandomNumber() {
        Random randomGenerator = new Random();
        int randomNum;
        clearImageViews();
        if (imageViews.size() >= numberOfCubes) {
            for (int i = 0; i < numberOfCubes; i++) {
                randomNum = randomGenerator.nextInt(6) + 1;
                setImageView(randomNum, i);
            }
        }
    }

    private void clearImageViews() {
        for (int i = 0; i < imageViews.size(); i++) {
            if (i < numberOfCubes)
                imageViews.get(i).setBackgroundResource(R.drawable.blank);
            else
                imageViews.get(i).setBackgroundResource(android.R.color.transparent);
        }
    }

    private void setImageView(int randomNum, int i) {
        switch (randomNum) {
            case 1:
                imageViews.get(i).setBackgroundResource(R.drawable.one);
                break;
            case 2:
                imageViews.get(i).setBackgroundResource(R.drawable.two);
                break;
            case 3:
                imageViews.get(i).setBackgroundResource(R.drawable.three);
                break;
            case 4:
                imageViews.get(i).setBackgroundResource(R.drawable.four);
                break;
            case 5:
                imageViews.get(i).setBackgroundResource(R.drawable.five);
                break;
            case 6:
                imageViews.get(i).setBackgroundResource(R.drawable.six);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        float acceleration = (float) Math.sqrt(x * x + y * y + z * z) -
                SensorManager.GRAVITY_EARTH;
        if (acceleration > SHAKE_THRESHOLD) {
            generateRandomNumber();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void oneButtonListener(View v) {
        numberOfCubes = 1;
        clearImageViews();
    }

    public void twoButtonListener(View v) {
        numberOfCubes = 2;
        clearImageViews();
    }

    public void threeButtonListener(View v) {
        numberOfCubes = 3;
        clearImageViews();
    }

}