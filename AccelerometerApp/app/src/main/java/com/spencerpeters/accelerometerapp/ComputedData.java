package com.spencerpeters.accelerometerapp;

/**
 * Created by Spencer on 10/16/16.
 */

public class ComputedData {
    public int numPeaks;
    public int restingTime;
    public int totalTime;

    public ComputedData(int numPeaks, int restingTime, int totalTime) {
        this.numPeaks = numPeaks;
        this.restingTime = restingTime;
        this.totalTime = totalTime;
    }

    public ComputedData delta(ComputedData previous) {
        int deltaNumPeaks = this.numPeaks - previous.numPeaks;
        int deltaRestingTime = this.restingTime - previous.restingTime;
        int deltaTotalTime = this.totalTime - previous.totalTime;
        return new ComputedData(deltaNumPeaks, deltaRestingTime, deltaTotalTime);
    }

    public int getFrequency() {
        return this.numPeaks / this.totalTime;
    }
}
