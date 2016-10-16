package com.spencerpeters.accelerometerapp;

import java.util.ArrayList;
import java.util.Iterator;
import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.*;

/**
 * Created by Vadim on 10/16/2016.
 */

public class RotationalData implements Runnable {

    private static int timeInterval;
    private static final int AXIS = 1;
    private ArrayList<float[]> data;
    private ArrayList<float[]> buffer;
    private int countBuffered;
    private RepsCalculator calc;

    class MyObservable extends Observable {
        @Override
        public void notifyObservers() {
            setChanged();
            super.notifyObservers();
        }
    }
    private MyObservable notifier;
    private boolean keepRunning = false;


    public void stopThread() {
        keepRunning = false;
    }

    public RotationalData(int interval){
        data = new ArrayList<float[]>();
        buffer = new ArrayList<float[]>();
        countBuffered = 0;
        this.timeInterval = interval;
        calc = new RepsCalculator();
        notifier = new MyObservable();
    }

    @Override
    public int size(){
        return data.size();
    }

    @Override
    public Number getY(int index){
        return data.get(index)[AXIS];
    }

    @Override
    public Number getX(int index){
        return index * timeInterval;
    }



    //@Override
    public void run() {
        try {
            keepRunning = true;
            boolean isRising = true;
            while (keepRunning) {

                Thread.sleep(10); // decrease or remove to speed up the refresh rate.
                notifier.notifyObservers();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addObserver(Observer observer) {
        notifier.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        notifier.deleteObserver(observer);
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


