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
    private static final float margin = 1.0f;
    private static final float restMargin = 1.0f;
    private static final int bufferSize = 30;

    public static int numPeaks(ArrayList<float[]> list, int size){
        int peaks = 0;
        int i = 0;
        float prevMinMax = 0.0f;
        while(i < size){
            float current = list.get(i)[AXIS];
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
            i++;
        }
        return peaks;
    }


    public static int restTime(ArrayList<float[]> list, int size, int freq){
        int pointsResting = 0;
        LinkedList<Float> buffer = new LinkedList<Float>();
        for(int i = 0; i < bufferSize; i++) {
            buffer.add(0.0f);
        }
        int i = 0;
        while(i < size){
            boolean allZero = true;
            buffer.pop();
            buffer.push(list.get(i)[AXIS]);
            for(float f: buffer){
                if(!almostZero(f)){
                    allZero = false;
                }
            }
            if(allZero){
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
