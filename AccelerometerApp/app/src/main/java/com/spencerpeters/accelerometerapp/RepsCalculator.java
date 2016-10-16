package com.spencerpeters.accelerometerapp;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Vadim on 10/16/2016.
 */

public class RepsCalculator {
    private static final int AXIS = 1;
    private static final float margin = 1.0f;
    private static final float restMargin = 1.0f;
    private static final int bufferSize = 30;

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


    public static int restTime(Iterator<float[]> it, int freq){
        int pointsResting = 0;
        LinkedList<Float> buffer = new LinkedList<Float>();
        for(int i = 0; i < bufferSize; i++) {
            buffer.add(0.0f);
        }
        while(it.hasNext()){
            boolean allZero = true;
            buffer.pop();
            buffer.push(it.next()[AXIS]);
            for(float f: buffer){
                if(!almostZero(f)){
                    allZero = false;
                }
            }
            if(allZero){
                pointsResting++;
            }
        }
        return pointsResting * freq;
    }

    private static boolean almostZero(float f){
        return f < restMargin && f > (0.0f-restMargin);
    }
}
