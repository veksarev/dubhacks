package com.spencerpeters.accelerometerapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Vadim on 10/16/2016.
 */

public class RepsCalculator {
    private static final int AXIS = 1;
    private static final float margin = 0.7f;
    private static final float restMargin = 0.4f;
    private static final int bufferSize = 10;

    public static int numPeaks(ArrayList<float[]> list, int size){
        int peaks = 0;
        int i = 0;
        float prevMinMax = 0.0f;
        while(i < size){
            float current = list.get(i)[AXIS];
            if((prevMinMax > margin && current < (0.0 - margin)) || (current > margin && prevMinMax < (0.0 - margin))){
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
            i++;
        }
        return peaks/2;
    }


    public static int restTime(ArrayList<float[]> list, int size, int freq){
        int pointsResting = 0;
        int i = bufferSize;
        while(i < size - bufferSize){
            if(almostZero(list.get(i - bufferSize)[AXIS]) && almostZero(list.get(i)[AXIS]) && almostZero(list.get(i + bufferSize)[AXIS])){
                pointsResting++;
            }
            i++;
        }
        return pointsResting * freq;
    }

    private static boolean almostZero(float f){
        return f < restMargin && f > (0.0f-restMargin);
    }
}
