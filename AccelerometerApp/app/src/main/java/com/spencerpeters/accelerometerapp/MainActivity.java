package com.spencerpeters.accelerometerapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.*;
import android.util.Log;

import java.util.Arrays;


// Hi this is a commit
public class MainActivity extends AppCompatActivity {

    public RotationalData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, DynamicXYPlotActivity.class);
        startActivity(intent);
    }
}
