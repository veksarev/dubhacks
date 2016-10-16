package com.spencerpeters.accelerometerapp;

import java.util.Iterator;

/**
 * Created by Vadim on 10/16/2016.
 */

public class RepsCalculator {
    private static final int AXIS = 1;
    private static final float margin = 1.0f;

    public static int numPeaks(Iterator<float[]> it){
        int peaks = 0;
        float prevMinMax = 0.0f;
        while(it.hasNext()){
            float current = it.next()[AXIS];
            if((prevMinMax > margin && current < (0.0 - margin))||(prevMinMax < (0.0 - margin) && current > margin)){
                peaks++;
                prevMinMax = current;
            }
            if(prevMinMax > 0.0f){
                if(current > prevMinMax){
                    prevMinMax = current;
                }
            } else {
                if(current < prevMinMax){
                    prevMinMax = current;
                }
            }
        }
        return peaks;
    }

    private static boolean almostZero(float f){
        return f < margin && f > (0.0f-margin);
    }
}
