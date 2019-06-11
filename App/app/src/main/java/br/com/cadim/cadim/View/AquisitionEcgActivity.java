package br.com.cadim.cadim.View;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.androidplot.util.Redrawer;
import com.androidplot.xy.AdvancedLineAndPointRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import br.com.cadim.cadim.Controller.ConnectionThread;
import br.com.cadim.cadim.Model.Ecg;
import br.com.cadim.cadim.Model.Paciente;
import br.com.cadim.cadim.R;
import br.com.cadim.cadim.Controller.Rest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.cadim.cadim.Controller.ManageFile.message;
import static br.com.cadim.cadim.Controller.ManageFile.nowDate;
import static br.com.cadim.cadim.Controller.ManageFile.saveSignal;

public class AquisitionEcgActivity extends AppCompatActivity {

    static ArrayList<String> signalECGBuffer;
    static ArrayList<String> signalECGTmp;
    static ArrayList<Integer> ECGSignal;

    ConnectionThread connect;

    private static Redrawer redrawer;
    private static XYPlot plot;
    private static ECGModel ecgSeries;
    private static int lowerBoundary;
    private static int upperBoundary;
    private static TextView txtPeriodoAquisicao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.aquisition_layout);

        Paciente paciente = getIntent().getExtras().getParcelable("paciente");
        ArrayList<Ecg> ecgs = getIntent().getParcelableArrayListExtra("listaEcg");

        ImageButton btnPauseAquisition = (ImageButton) findViewById(R.id.btnPauseAquisition);
        plot = (XYPlot) findViewById(R.id.plotECG);
        txtPeriodoAquisicao = (TextView) findViewById(R.id.txtPeriodoAquisicao);

        ECGSignal = new ArrayList<>();
        signalECGBuffer = new ArrayList<>();

        lowerBoundary = 0;
        upperBoundary = 500;

        btnPauseAquisition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect.cancel();
                message(getApplicationContext(), "Comunicação encerrada!");

//                ArrayList<Integer> signalECG = mountSignal(signalECGBuffer);

//                saveSignal(signalECG);
//                sendSignal(paciente, signalECG);
                message(getApplicationContext(), "Success Saved!!");
            }
        });

        startAquisition();
    }

    private void startAquisition() {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            System.out.println("Que pena! Hardware Bluetooth não está funcionando :(");
        } else {
            System.out.println("Ótimo! Hardware Bluetooth está funcionando :)");
        }

        String btAddress = getIntent().getExtras().getString("btAddress");

        btAdapter.enable();

        connect = new ConnectionThread(btAddress);
        connect.start();
        plotSignal(lowerBoundary, upperBoundary);

        try {
            Thread.sleep(500);
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    private static ArrayList<Integer> mountSignal(ArrayList<String> signalEcg) {
        ArrayList<Integer> signal = new ArrayList<>();
        int hb, lb;
        for (int index_sync = 0; index_sync < signalEcg.size() - 7; index_sync++) {
            if (signalEcg.get(index_sync).contains("165") && signalEcg.get(index_sync + 1).contains("90")) {

                hb = (int) Double.parseDouble(signalEcg.get(index_sync + 6));
                lb = (int) Double.parseDouble(signalEcg.get(index_sync + 7));

                signal.add((hb << 8) + lb);
            }
        }
        return signal;
    }

    private static void plotSignal(int lowerBoundary, int upperBoundary) {
        ecgSeries = new ECGModel(50);

        MyFadeFormatter formatter = new MyFadeFormatter(500);
        formatter.setLegendIconEnabled(false);

        plot.addSeries(ecgSeries, formatter);
        plot.setRangeBoundaries(0, 1000, BoundaryMode.FIXED);
        plot.setDomainBoundaries(lowerBoundary, upperBoundary, BoundaryMode.FIXED);

        // reduce the number of range labels
        plot.setLinesPerRangeLabel(5);

        // set a redraw rate of 30hz and start immediately:
        redrawer = new Redrawer(plot, 30, true);

        // start generating ecg data in the background:
        ecgSeries.start(new WeakReference<>(plot.getRenderer(AdvancedLineAndPointRenderer.class)));
    }

    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString = new String(data);

            if (dataString.equals("---N"))
                System.out.println("Ocorreu um erro durante a conexão D:");
            else if (dataString.equals("---S"))
                System.out.println("Conectado :D");
            else {

                signalECGBuffer.addAll(new ArrayList<>(Arrays.asList(dataString.split("\n"))));

                if (signalECGBuffer.size() >= 5000) {
                    signalECGTmp = (ArrayList<String>) signalECGBuffer.clone();
                    signalECGBuffer.clear();
                    ECGSignal.addAll(mountSignal(signalECGTmp));
                    ;
                }
            }
        }
    };

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
        private boolean keepRunning;
        private int latestIndex;
        private static int numberSamples;

        private WeakReference<AdvancedLineAndPointRenderer> rendererRef;

        /**
         * //         * @param signalECG    Array that contains the ECG signal
         *
         * @param updateFreqHz Frequency at which new samples are added to the model
         */
        ECGModel(int updateFreqHz) {

            latestIndex = 0;
            numberSamples = 10000;

            data = new Number[numberSamples];
            // translate hz into delay (ms):
            delayMs = 1000 / updateFreqHz;

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (keepRunning) {

                            if (latestIndex == numberSamples) {
                                keepRunning = false;
                            }

                            if (latestIndex == upperBoundary) {
                                lowerBoundary += 500;
                                upperBoundary += 500;
                                plot.setDomainBoundaries(lowerBoundary, upperBoundary, BoundaryMode.FIXED);
                            }

                            // insert a sample
                            data[latestIndex] = ECGSignal.get(latestIndex);

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

        void start(final WeakReference<AdvancedLineAndPointRenderer> rendererRefPlot) {
            this.rendererRef = rendererRefPlot;
            keepRunning = true;

            Handler handle = new Handler();
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    thread.start();
                }
            }, 7000);

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

    private static void sendSignal(Paciente paciente, ArrayList<Integer> signal) {
        Rest.MSGString m = new Rest.MSGString();

        String signalECG = "";

        for (Integer sample : signal) {
            signalECG += sample + " ";
        }

        String cpf = paciente.getCpf();
        m.cpf = cpf;
        m.ecgFile = "ECG-" + cpf + "-" + nowDate() + ".txt";
        m.imc = paciente.getPeso() / Math.pow(paciente.getAltura(), 2);
        m.dataHora = nowDate();
        m.signalECG = signalECG;

        Rest.getRetrofit().sendString(m).enqueue(new Callback<Rest.MSGString>() {

            // Caso a mensagem chegue no servidor
            @Override
            public void onResponse(Call<Rest.MSGString> call, Response<Rest.MSGString> response) {
                Log.i("TAG", "OK");
            }

            // Caso ocorra algum erro
            @Override
            public void onFailure(Call<Rest.MSGString> call, Throwable t) {
                Log.i("TAG", "ERROR: " + t.getMessage());
            }
        });
    }

}
