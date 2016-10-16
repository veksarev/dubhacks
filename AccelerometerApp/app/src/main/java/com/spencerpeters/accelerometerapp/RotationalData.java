package com.spencerpeters.accelerometerapp;

import java.util.ArrayList;
import java.util.Iterator;
import android.util.Log;

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

    private static final int POINTS_TO_SHOW = 100; // don't make this any bigger
    private static final int TEXT_UPDATE_TIME = 100;
    private static int timeInterval;
    private static final int AXIS = 0;
    public ArrayList<float[]> data;
    private ArrayList<float[]> buffer;
    private int countBuffered;
    private RepsCalculator calc;

    class MyObservable extends Observable {
        @Override
        public void notifyObservers() {
            setChanged();
            super.notifyObservers();
        }

        @Override
        public void notifyObservers(Object arg) {
            Log.d("notify", "notify with arg called");
            setChanged();
            super.notifyObservers(arg);
        }
    }

    private MyObservable notifier;
    private MyObservable computeNotifier;
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
        computeNotifier = new MyObservable();
    }

    public int size(){
        return Math.min(data.size(), POINTS_TO_SHOW);
    }

    public int indexToDataIndex(int index) {
        return data.size() - 1  - Math.min(Math.abs(data.size() - 1), POINTS_TO_SHOW) + index;
    }

    public Number getY(int index){
  //      Log.d("getY", "getYCalled");
        return data.get(indexToDataIndex(index))[AXIS];
    }

    public Number getX(int index){
        return indexToDataIndex(index);
    }



    @Override
    public void run() {
        int totalTimeSlept = 0;
        try {
            keepRunning = true;
            boolean isRising = true;
            while (keepRunning) {
                totalTimeSlept += 10;
                Thread.sleep(10); // decrease or remove to speed up the refresh rate.

                notifier.notifyObservers();
                if (totalTimeSlept % TEXT_UPDATE_TIME == 0) {
                    Log.d("sanity", "this better be called");
                    ComputedData toDisplay = computeData();
                    computeNotifier.notifyObservers(toDisplay);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ComputedData computeData() {
        return new ComputedData(peaks(), timeResting(), totalTime());
    }

    public void addComputeObserver(Observer observer) {
        computeNotifier.addObserver(observer);
    }

    public void addObserver(Observer observer) {
        notifier.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        notifier.deleteObserver(observer);
    }

    public int totalTime() {
        return data.size() * timeInterval;
    }

    public void addData(float[] sample){
//        if(countBuffered == 4){
//            countBuffered = 0;
//            buffer.add(sample);
//            float[] added = averageBuffer();
//            data.add(added);
//            Log.d("data", Arrays.toString(added) + " at index " + data.size());
//        } else{
//            buffer.add(averageBuffer());
//            countBuffered++;
//        }
 //       Log.d("data", Arrays.toString(sample) + " at index " + data.size());
//        for (int i = 0; i < data.size(); i++) {
//            Log.d("datadump", Arrays.toString(data.get(i)) + "at index" + i);
//        }
        data.add(sample.clone());
    }

    public Iterator<float[]> iterator(){
        return data.iterator();
    }

    public int peaks(){
        return calc.numPeaks(this.data, data.size());
    }

    public int timeResting(){
        return calc.restTime(this.data, data.size(), this.timeInterval);
    }
//    private float[] averageBuffer(){
//       float[] result = new float[3];
//       for(float[] ar: buffer){
//           for(int i = 0; i < 3; i++){
//               result[i]+=ar[i];
//           }
//       }
//       for(int i = 0; i < 3; i++){
//           result[i] = result[i]/5;
//       }
//       return result;
//    }

}


