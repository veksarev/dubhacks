package com.spencerpeters.accelerometerapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.*;
import android.util.Log;

import java.util.Arrays;


// Hi this is a commit
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager manager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        Sensor gyroscope = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //Sensor rotation = manager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        //Sensor orientation = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        manager.registerListener(this, gyroscope, 2000000);
        Log.d("tag", "what up");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Sensor gyroscope = event.sensor
        float[] rotations = event.values;
        Log.d("rotation", Arrays.toString(rotations));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // don't do anything--I don't know what needs to be done
        Log.e("accuracy", "accuracy changed");
    }
}
