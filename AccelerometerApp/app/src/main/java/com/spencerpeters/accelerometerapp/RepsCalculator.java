package com.spencerpeters.accelerometerapp;

import java.util.Iterator;

/**
 * Created by Vadim on 10/16/2016.
 */

public class RepsCalculator {
    private static final int AXIS = 1;
    private static final float margin = 0.5f;


    public static int numPeaks(Iterator<float[]> it){
        int peaks = 0;
        float prev = 0.0f;
        while(it.hasNext()){
            if(!almostZero(prev) && almostZero(it.next()[AXIS])){
                peaks++;
            }
        }
        return peaks;
    }
    
    private static boolean almostZero(float f){
        return f < margin && f > (0.0f-margin);
    }
}
