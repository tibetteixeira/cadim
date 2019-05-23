package br.com.cadim.cadim;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class AquisitionEcgActivity extends AppCompatActivity {

    static ArrayList<String> signalEcg;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static String signalAcquisitionDate;
    ConnectionThread connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.aquisition_layout);


        ImageButton btnPauseAquisition = (ImageButton) findViewById(R.id.btnPauseAquisition);

        if (!checkPermission()) requestPermission();

        btnPauseAquisition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect.cancel();
                message("Comunicação encerrada!");
                ArrayList<Integer> signal = mountSignal(signalEcg);
                saveSignal(signal);
                message("Success Saved!!");
            }
        });

        startAquisition();
    }

    private void startAquisition() {
        signalEcg = new ArrayList<>();
        signalAcquisitionDate = nowDate();
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            System.out.println("Que pena! Hardware Bluetooth não está funcionando :(");
        } else {
            System.out.println("Ótimo! Hardware Bluetooth está funcionando :)");
        }

        btAdapter.enable();

        connect = new ConnectionThread("00:21:13:01:00:71");
        connect.start();

        /* Um descanso rápido, para evitar bugs esquisitos.
         */
        try {
            Thread.sleep(500);
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    private ArrayList<Integer> mountSignal(ArrayList<String> signalEcg) {
        ArrayList<Integer> signal = new ArrayList<>();
        int hb, lb;
        for (int index_sync = 0; index_sync < signalEcg.size() - 7; index_sync++) {
            if (signalEcg.get(index_sync).contains("165") && signalEcg.get(index_sync + 1).contains("90")) {

                hb = (int) Double.parseDouble(signalEcg.get(index_sync + 6));
                lb = (int) Double.parseDouble(signalEcg.get(index_sync + 7));

                System.out.println("HB: " + hb + " LB: " + lb);
                signal.add((hb << 8) + lb);
            }
        }

        return signal;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(AquisitionEcgActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(AquisitionEcgActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(AquisitionEcgActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(AquisitionEcgActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private void saveSignal(ArrayList<Integer> signalEcg) {
        File file, dir;
        FileOutputStream fos;

        String fileName = "ECG-" + signalAcquisitionDate + ".txt";

        byte[] data;
        String signal;

        try {
            dir = new File(getDirectory());
            file = new File(dir, fileName);

            int fileLines;

            for (fileLines = 0; fileLines < signalEcg.size() - 1; fileLines++) {

                signal = signalEcg.get(fileLines) + " ";
                data = signal.getBytes();

                fos = new FileOutputStream(file, true);
                fos.write(data);
                fos.flush();
                fos.close();
            }

        } catch (FileNotFoundException e) {
            message("Erro FNFE: " + e.getMessage());

        } catch (IOException e) {
            message("Erro IOE: " + e.getMessage());
        }
    }

    public static String nowDate() {
        @SuppressLint("SimpleDateFormat")

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
        Date now = new Date();

        return dateFormat.format(now);
    }

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
                // System.out.println(dataString);
                signalEcg.addAll(new ArrayList<>(Arrays.asList(dataString.split("\n"))));
                System.out.println(dataString);
            }
        }
    };

    public static String getDirectory() {

        File root = new File(Environment
                .getExternalStorageDirectory()
                + "/Arida/MobileECG/");

        if (!root.exists()) root.mkdirs();

        return root.toString();
    }

    private void message(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
