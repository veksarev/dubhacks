package com.spencerpeters.accelerometerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;


// Hi this is a commit
public class MainActivity extends AppCompatActivity {

    public Intent intent;
    public RotationalData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, DynamicXYPlotActivity.class);
        final Button button = (Button) findViewById(R.id.startbutton);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
    }

//    public class StartButton extends Activity {
//        protected void onCreate(Bundle icicle) {
//            super.onCreate(icicle);
//
//            setContentView(R.layout.content_layout_id);
//
//            final Button button = (Button) findViewById(R.id.button_id);
//            button.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    startActivity(intent);
//                }
//            });
//        }
//    }

 /*   public class EndButton extends Activity {
        protected void onCreate(Bundle icicle) {
            super.onCreate(icicle);

            setContentView(R.layout.content_layout_id);

            final Button button = (Button) findViewById(R.id.button_id);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                }
            });
        }
    }*/
}
