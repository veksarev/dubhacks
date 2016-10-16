package com.spencerpeters.accelerometerapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

/**
 * Created by Spencer on 10/16/16.
 */

public class RotationalSensorListener implements SensorEventListener {

    public RotationalData toUpdate;

    public RotationalSensorListener(RotationalData toUpdate) {
        this.toUpdate = toUpdate;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Sensor gyroscope = event.sensor
        float[] rotations = event.values;
        toUpdate.addData(rotations);
        //Log.d("rotation", Arrays.toString(rotations));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // don't do anything--I don't know what needs to be done
        Log.e("accuracy", "accuracy changed");
    }
}
