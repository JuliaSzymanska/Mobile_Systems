package com.game.cube;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private static int SHAKE_THRESHOLD = 3;
    private TextView firstCube;
    private TextView secondCube;
    private TextView thirdCube;
    private int numberOfCubes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstCube = (TextView) findViewById(R.id.first_cube);
        secondCube = (TextView) findViewById(R.id.second_cube);
        thirdCube = (TextView) findViewById(R.id.third_cube);
        mSensorManager =
                (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer =
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void generateRandomNumber() {
        Random randomGenerator = new Random();
        int randomNum = randomGenerator.nextInt(6) + 1;
        firstCube.setText(Integer.toString(randomNum));
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

    public void oneButtonListener(){
        numberOfCubes = 1;
    }

    public void twoButtonListener(){
        numberOfCubes = 2;
    }

    public void threeButtonListener(){
        numberOfCubes = 3;
    }

}