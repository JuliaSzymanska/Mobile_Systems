package com.game.cube;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private static int SHAKE_THRESHOLD = 3;
    List<TextView> textViews;
    private int numberOfCubes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberOfCubes = 1;
        textViews = new ArrayList<>();
        textViews.add((TextView) findViewById(R.id.first_cube));
        textViews.add((TextView) findViewById(R.id.second_cube));
        textViews.add((TextView) findViewById(R.id.third_cube));
        mSensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer =
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void generateRandomNumber() {
        Random randomGenerator = new Random();
        int randomNum;
        for(int i = 0; i < textViews.size(); i++){
            textViews.get(i).setText("");
        }
        System.out.println(textViews.size());
        if (textViews.size() >= numberOfCubes) {
            for (int i = 0; i < numberOfCubes; i++) {
                randomNum = randomGenerator.nextInt(6) + 1;
                textViews.get(i).setText(Integer.toString(randomNum));
            }
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
    }

    public void twoButtonListener(View v) {
        numberOfCubes = 2;
    }

    public void threeButtonListener(View v) {
        numberOfCubes = 3;
    }

}