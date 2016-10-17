package com.spencerpeters.accelerometerapp;
        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.DashPathEffect;
        import android.graphics.Paint;
        import android.hardware.Sensor;
        import android.hardware.SensorManager;
        import android.os.Bundle;
        import android.os.Environment;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.androidplot.Plot;
        import com.androidplot.util.PixelUtils;
        import com.androidplot.xy.XYSeries;
        import com.androidplot.xy.*;

        import java.text.DecimalFormat;
        import java.util.Observable;
        import java.util.Observer;

public class DynamicXYPlotActivity extends Activity {

    // redraws a plot whenever an update is received:
    private class MyTextBoxUpdater implements Observer {
        TextView view;
        private DecimalFormat format = new DecimalFormat("#.##");
        public MyTextBoxUpdater(TextView view) {
            this.view = view;
        }

        @Override
        public void update(Observable o, Object arg) {
            ComputedData preData = (ComputedData) arg;
            final ComputedData data = preData.convertToStandard();
            Log.d("box", "box updated");
            runOnUiThread(
                    new Runnable() {
                @Override
                public void run() {
                    Log.d("peaks", "" + data.numPeaks);
                    String textToDisplay = "";
                    textToDisplay += "Reps done: " + data.numPeaks + "\n";
                    textToDisplay += "Resting time: " + data.restingTime + "\n";
                    textToDisplay += "Reps per minute: " + format.format(data.getFrequency()) + "\n";
                    view.setText(textToDisplay);

                }
            }
            );
        }
    }
    private class MyPlotUpdater implements Observer {
        Plot plot;

        public MyPlotUpdater(Plot plot) {
            this.plot = plot;
        }

        @Override
        public void update(Observable o, Object arg) {
            Log.d("refresh", "plot is redrawn");
            plot.redraw();
        }
    }

    private XYPlot dynamicPlot;
    private MyPlotUpdater plotUpdater;
    private MyTextBoxUpdater textBoxUpdater;
    RotationalData data;
    private Thread myThread;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // android boilerplate stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_data_plot);

        final Intent intent = new Intent(this, MainActivity.class);

        final Button button = (Button) findViewById(R.id.stopbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        // get handles to our View defined in layout.xml:
        dynamicPlot = (XYPlot) findViewById(R.id.plot);
        textView = (TextView) findViewById(R.id.textBox);

        plotUpdater = new MyPlotUpdater(dynamicPlot);
        textBoxUpdater = new MyTextBoxUpdater(textView);

        // only display whole numbers in domain labels
        dynamicPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).
                setFormat(new DecimalFormat("0"));

        // getInstance and position datasets:
        SensorManager manager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        Sensor gyroscope = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        int interval = 20000;
        data = new RotationalData(interval);
        manager.registerListener(new RotationalSensorListener(data), gyroscope, interval);


        SampleDynamicSeries sine1Series = new SampleDynamicSeries(data, "workout");

        LineAndPointFormatter formatter1 = new LineAndPointFormatter(
                Color.rgb(0, 200, 0), null, null, null);
        formatter1.getLinePaint().setStrokeJoin(Paint.Join.ROUND);
        formatter1.getLinePaint().setStrokeWidth(10);
        dynamicPlot.addSeries(sine1Series,
                formatter1);


        // hook up the plotUpdater to the data model:
        data.addObserver(plotUpdater);
        data.addComputeObserver(textBoxUpdater);

        // thin out domain tick labels so they dont overlap each other:
        dynamicPlot.setDomainStepMode(StepMode.INCREMENT_BY_VAL);
        dynamicPlot.setDomainStepValue(0.25);

        dynamicPlot.setRangeStepMode(StepMode.INCREMENT_BY_VAL);
        dynamicPlot.setRangeStepValue(10);

        dynamicPlot.getGraph().getLineLabelStyle(
                XYGraphWidget.Edge.LEFT).setFormat(new DecimalFormat("###.#"));

        dynamicPlot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new DecimalFormat("###.#"));

        // uncomment this line to freeze the range boundaries:
        dynamicPlot.setRangeBoundaries(-3, 3, BoundaryMode.FIXED);

        // create a dash effect for domain and range grid lines:
        DashPathEffect dashFx = new DashPathEffect(
                new float[] {PixelUtils.dpToPix(3), PixelUtils.dpToPix(3)}, 0);
        dynamicPlot.getGraph().getDomainGridLinePaint().setPathEffect(dashFx);
        dynamicPlot.getGraph().getRangeGridLinePaint().setPathEffect(dashFx);
    }

    @Override
    public void onResume() {
        // kick off the data generating thread:
        myThread = new Thread(data);
        myThread.start();
        super.onResume();
    }

    @Override
    public void onPause() {
        data.stopThread();
        super.onPause();
    }

    class SampleDynamicSeries implements XYSeries {
        private RotationalData datasource;
        private int seriesIndex;
        private String title;

        public SampleDynamicSeries(RotationalData datasource,  String title) {
            this.datasource = datasource;
            this.title = title;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public int size() {
            return datasource.size();
        }

        @Override
        public Number getX(int index) {
            return datasource.getX(index);
        }

        @Override
        public Number getY(int index) {
            return datasource.getY(index);
        }
    }
}