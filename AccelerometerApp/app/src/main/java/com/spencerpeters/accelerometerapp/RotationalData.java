package com.spencerpeters.accelerometerapp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Vadim on 10/16/2016.
 */

public class RotationalData implements XYSeries{

    private static int timeInterval;
    private static final int AXIS = 1;
    private ArrayList<float[]> data;
    private ArrayList<float[]> buffer;
    private int countBuffered;
    private String title;
    private RepsCalculator calc;

    public RotationalData(String title, int interval){
        data = new ArrayList<float[]>();
        buffer = new ArrayList<float[]>();
        countBuffered = 0;
        this.title = title;
        this.timeInterval = interval;
        calc = new RepsCalculator();
    }

    @Override
    public String getTitle(){
        return title;
    }

    @Override
    public int size(){
        return data.size();
    }

    @Override
    public Number getX(int index){
        return data.get(index)[AXIS];
    }

    @Override
    public Number getY(int index){
        return index * timeInterval;
    }

    public void addData(float[] sample){
        if(countBuffered == 4){
            countBuffered = 0;
            buffer.add(sample);
            float[] added = averageBuffer();
            data.add(added);
        } else{
            buffer.add(averageBuffer());
            countBuffered++;
        }
    }

    public Iterator<float[]> iterator(){
        return data.iterator();
    }

    public int peaks(){
        return calc.numPeaks(this.iterator());
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


