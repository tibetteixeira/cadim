package br.com.cadim.cadim;

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

import br.com.cadim.cadim.Model.Paciente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.cadim.cadim.ManageFile.message;
import static br.com.cadim.cadim.ManageFile.nowDate;
import static br.com.cadim.cadim.ManageFile.saveSignal;

public class AquisitionEcgActivity extends AppCompatActivity {

    static ArrayList<String> signalECGBuffer;
    static ArrayList<String> signalECGTmp;
    static ArrayList<Integer> signalECGMount;

    private static String signalAcquisitionDate;
    ConnectionThread connect;

    private static Redrawer redrawer;
    private static XYPlot plot;
    private static ECGModel ecgSeries;
    private static int lowerBoundary;
    private static int upperBoundary;
    private static TextView txtPeriodoAquisicao;
    private static int mountIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.aquisition_layout);


        ImageButton btnPauseAquisition = (ImageButton) findViewById(R.id.btnPauseAquisition);
        plot = (XYPlot) findViewById(R.id.plotECG);
        txtPeriodoAquisicao = (TextView) findViewById(R.id.txtPeriodoAquisicao);
        signalECGBuffer = new ArrayList<>();
        signalECGMount = new ArrayList<>();
        lowerBoundary = 0;
        upperBoundary = 500;
        mountIndex = 0;

        btnPauseAquisition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect.cancel();

                message(getApplicationContext(), "Comunicação encerrada!");

                ArrayList<Integer> signalECG = mountSignal(signalECGBuffer);
                Paciente paciente = getIntent().getExtras().getParcelable("paciente");

                saveSignal(signalECG);
                sendSignal(paciente, signalECG);

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

        String btName = getIntent().getExtras().getString("btName");
        String btAddress = getIntent().getExtras().getString("btAddress");

        btAdapter.enable();

        connect = new ConnectionThread(btAddress);
        connect.start();
//        plotSignal2();

        /* Um descanso rápido, para evitar bugs esquisitos.
         */
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

//                System.out.println("HB: " + hb + " LB: " + lb);
                signal.add((hb << 8) + lb);
            }
        }

        return signal;
    }

    private static void plotSignal(ArrayList<Integer> signalECG, int lowerBoundary, int upperBoundary) {
//        ecgSeries = new ECGModel(signalECG, 10000);

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

    private static void plotSignal2() {
        ecgSeries = new ECGModel(30);

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

    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            /* Esse método é invocado na Activity principal
                sempre que a thread de conexão Bluetooth recebe
                uma mensagem.
             */
            Bundle bundle = msg.getData();
            byte[] data = bundle.getByteArray("data");
            String dataString = new String(data);

            /* Aqui ocorre a decisão de ação, baseada na string
                recebida. Caso a string corresponda à uma das
                mensagens de status de conexão (iniciadas com --),
                atualizamos o status da conexão conforme o código.
             */
            if (dataString.equals("---N"))
                System.out.println("Ocorreu um erro durante a conexão D:");
            else if (dataString.equals("---S"))
                System.out.println("Conectado :D");
            else {
                System.out.println(dataString);
                signalECGBuffer.addAll(new ArrayList<>(Arrays.asList(dataString.split("\n"))));


//                if (signalECGBuffer.size() >= 15000) {
////                    System.out.println();
//                    signalECGTmp = (ArrayList<String>) signalECGBuffer.clone();
//                    signalECGBuffer.clear();
//                    signalECGMount.addAll(mountSignal(signalECGTmp));
//                    ecgSeries.appendSignal(signalECGMount);
//                    signalECGMount.clear();
////                    plotSignal(signalECGMount, lowerBoundary + 500 * mountIndex, upperBoundary + 500 * mountIndex);
////                    mountIndex += 1;
//                }

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
        private ArrayList<Integer> ECGSignal;
        private boolean keepRunning;
        private int latestIndex;
        private int sizeSignal;

        private WeakReference<AdvancedLineAndPointRenderer> rendererRef;

        /**
         * //         * @param signalECG    Array that contains the ECG signal
         *
         * @param updateFreqHz Frequency at which new samples are added to the model
         */
        ECGModel(int updateFreqHz) {

            latestIndex = 0;
//            ECGSignal = new ArrayList<>(signalECG);
//            sizeSignal = signalECG.size();
            data = new Number[0];
            // translate hz into delay (ms):
            delayMs = 1000 / updateFreqHz;


            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (keepRunning) {

                            data = getData();

                            if (latestIndex == upperBoundary) {
                                lowerBoundary += 500;
                                upperBoundary += 500;
                                plot.setDomainBoundaries(lowerBoundary, upperBoundary, BoundaryMode.FIXED);
                            }

                            // insert a sample
//                            data[latestIndex] = ECGSignal.get(latestIndex);

                            if (latestIndex == sizeSignal - 1) {
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

        private void appendSignal(ArrayList<Integer> ECGSignalTemp) {
            Number[] dataTemp = new Number[this.data.length];

            System.arraycopy(data, 0, dataTemp, 0, this.data.length);
            this.data = new Number[dataTemp.length + ECGSignalTemp.size()];

            System.arraycopy(dataTemp, 0, this.data, 0, dataTemp.length);
            System.arraycopy(ECGSignalTemp.toArray(), 0, this.data, dataTemp.length, ECGSignalTemp.size());
        }

        public Number[] getData() {
            return this.data;
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
