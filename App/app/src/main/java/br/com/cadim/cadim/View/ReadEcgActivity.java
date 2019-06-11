package br.com.cadim.cadim.View;

import android.graphics.Paint;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.androidplot.util.Redrawer;
import com.androidplot.xy.AdvancedLineAndPointRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import br.com.cadim.cadim.Model.Ecg;
import br.com.cadim.cadim.Model.Paciente;
import br.com.cadim.cadim.R;

public class ReadEcgActivity extends AppCompatActivity {

    private static Redrawer redrawer;
    private static XYPlot plot;
    private static ECGModel ecgSeries;
    private static int lowerBoundary;
    private static int upperBoundary;
    static Ecg ecgRead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.aquisition_layout);

        ecgRead = getIntent().getParcelableExtra("ecg");

        ImageButton btnPauseAquisition = (ImageButton) findViewById(R.id.btnPauseAquisition);

        plot = (XYPlot) findViewById(R.id.plotECG);
        lowerBoundary = 0;
        upperBoundary = 500;

        btnPauseAquisition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Paciente paciente = getIntent().getExtras().getParcelable("paciente");
            }
        });

        plotSignal(ecgRead.getEcgFile(), lowerBoundary, upperBoundary);
    }

    private static void plotSignal(String ecgSignalString, int lowerBoundary, int upperBoundary) {
        ArrayList<Integer> ecgSignalArray = parseString2Integer(ecgSignalString);

        ecgSeries = new ECGModel(ecgSignalArray, 30);

        MyFadeFormatter formatter = new MyFadeFormatter(1000);
        formatter.setLegendIconEnabled(false);

        plot.addSeries(ecgSeries, formatter);
        plot.setRangeBoundaries(0, 1000, BoundaryMode.FIXED);
        plot.setDomainBoundaries(lowerBoundary, upperBoundary, BoundaryMode.FIXED);

        // reduce the number of range labels
        plot.setLinesPerRangeLabel(3);

        // set a redraw rate of 30hz and start immediately:
        redrawer = new Redrawer(plot, 30, true);

        // start generating ecg data in the background:
        ecgSeries.start(new WeakReference<>(plot.getRenderer(AdvancedLineAndPointRenderer.class)));
    }

    private static ArrayList<Integer> parseString2Integer(String ecgSignalString) {
        String[] ecgString = ecgSignalString.split(" ");
        ArrayList<Integer> ecgSignalInteger = new ArrayList<>();

        for (String ecgSampleString : ecgString) {
            ecgSignalInteger.add(Integer.parseInt(ecgSampleString));
        }
        return ecgSignalInteger;
    }

    public static class MyFadeFormatter extends AdvancedLineAndPointRenderer.Formatter {

        private int trailSize;

        MyFadeFormatter(int trailSize) {
            this.trailSize = trailSize;
        }

        @Override
        public Paint getLinePaint(int thisIndex, int latestIndex, int seriesSize) {
            // offset from the latest index:
            int offset;
            if (thisIndex > latestIndex) {
                offset = latestIndex + (seriesSize - thisIndex);
            } else {
                offset = latestIndex - thisIndex;
            }

            float scale = 255f / trailSize;
            int alpha = (int) (255 - (offset * scale));
            getLinePaint().setAlpha(alpha > 0 ? alpha : 0);
            return getLinePaint();
        }
    }

    public static class ECGModel implements XYSeries {

        private Number[] data;
        private final long delayMs;
        private final Thread thread;
        private ArrayList<Integer> ECGSignal;
        private boolean keepRunning;
        private int latestIndex;

        private WeakReference<AdvancedLineAndPointRenderer> rendererRef;

        /**
         * @param ecgSignalArray Array that contains the ECG signal
         * @param updateFreqHz   Frequency at which new samples are added to the model
         */
        ECGModel(ArrayList<Integer> ecgSignalArray, int updateFreqHz) {
            latestIndex = 0;
            ECGSignal = ecgSignalArray;
            data = new Number[ecgSignalArray.size()];

            delayMs = 1000 / updateFreqHz;

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (keepRunning) {

                            if (latestIndex == upperBoundary) {
                                lowerBoundary += 500;
                                upperBoundary += 500;
                                plot.setDomainBoundaries(lowerBoundary, upperBoundary, BoundaryMode.FIXED);
                            }

                            // insert a sample
                            data[latestIndex] = ECGSignal.get(latestIndex);

                            if (latestIndex == ECGSignal.size() - 1) {
                                keepRunning = false;
                            }

                            if (rendererRef.get() != null) {
                                rendererRef.get().setLatestIndex(latestIndex);
                                Thread.sleep(delayMs);

                            } else {
                                keepRunning = false;
                            }

                            latestIndex++;
                        }
                    } catch (InterruptedException e) {
                        keepRunning = false;
                    }
                }
            });
        }

        void start(final WeakReference<AdvancedLineAndPointRenderer> rendererRef) {
            this.rendererRef = rendererRef;
            keepRunning = true;
            thread.start();
        }

        @Override
        public int size() {
            return data.length;
        }

        @Override
        public Number getX(int index) {
            return index;
        }

        @Override
        public Number getY(int index) {
            return data[index];
        }

        @Override
        public String getTitle() {
            return "Signal ECG";
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        redrawer.finish();
    }
}
