package com.spencerpeters.accelerometerapp;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Vadim on 10/16/2016.
 */

public class RotationalData {

    private LinkedList<float[]> data;
    private LinkedList<float[]> buffer;
    private int countBuffered;

    public RotationalData(){
        data = new LinkedList<float[]>();
        buffer = new LinkedList<float[]>();
        countBuffered = 0;
    }

    public void addData(float[] sample){
        if(countBuffered == 4){
            countBuffered = 0;
            buffer.add(sample);
            data.add(averageBuffer());
        } else{
            buffer.add(averageBuffer());
            countBuffered++;
        }
    }

    public Iterator<float[]> numPeaks(){
        return data.iterator();
    }

    private float[] averageBuffer(){
       float[] result = new float[3];
       for(float[] ar: buffer){
           for(int i = 0; i < 3; i++){
               result[i]+=ar[i];
           }
       }
       for(int i = 0; i < 3; i++){
           result[i] = result[i]/5;
       }
       return result;
    }

}


