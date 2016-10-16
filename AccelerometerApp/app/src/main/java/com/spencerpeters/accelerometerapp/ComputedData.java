package com.spencerpeters.accelerometerapp;

/**
 * Created by Spencer on 10/16/16.
 */

public class ComputedData {
    public static final int MICROSECONDS_PER_SECOND = 1000000;
    public int numPeaks;
    public int restingTime;
    public int totalTime;

    public ComputedData(int numPeaks, int restingTime, int totalTime) {
        this.numPeaks = numPeaks;
        this.restingTime = restingTime;
        this.totalTime = totalTime;
    }

    // seconds
    public ComputedData convertToStandard() {
        int restingTime = this.restingTime / MICROSECONDS_PER_SECOND;
        int totalTime = this.totalTime / MICROSECONDS_PER_SECOND;
        return new ComputedData(this.numPeaks, restingTime, totalTime);
    }

    public ComputedData delta(ComputedData previous) {
        int deltaNumPeaks = this.numPeaks - previous.numPeaks;
        int deltaRestingTime = this.restingTime - previous.restingTime;
        int deltaTotalTime = this.totalTime - previous.totalTime;
        return new ComputedData(deltaNumPeaks, deltaRestingTime, deltaTotalTime);
    }

    // reps / minute after conversion to standard
    public double getFrequency() {
        return (double) this.numPeaks * 60 / this.totalTime;
    }
}
