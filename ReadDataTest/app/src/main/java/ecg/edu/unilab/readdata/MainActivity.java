package ecg.edu.unilab.readdata;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";


    /* Definição dos objetos que serão usados na Activity Principal
        statusMessage mostrará mensagens de status sobre a conexão
        counterMessage mostrará o valor do contador como recebido do Arduino
        connect é a thread de gerenciamento da conexão Bluetooth
     */
    static TextView statusMessage;
    static Button btn_ecg_save;
    static TextView counterMessage;
    ConnectionThread connect;
    static GraphView graphView;
    static int numSamples;
    static LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
    static double graph2LastXValue = 5d;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private static ArrayList<String> signalEcg;
    static LineGraphSeries<DataPoint> mSeries;
    static DataPoint[] dataPoints = new DataPoint[512];
    static List<DataPoint> listPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Link entre os elementos da interface gráfica e suas
            representações em Java.
         */
        signalEcg = new ArrayList<>();
        statusMessage = (TextView) findViewById(R.id.statusMessage);
        counterMessage = (TextView) findViewById(R.id.counterMessage);
        graphView = (GraphView) findViewById(R.id.graph);
        btn_ecg_save = (Button) findViewById(R.id.btn_ecg);

        if (!checkPermission()) requestPermission();

        btn_ecg_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //connect.cancel();
                statusMessage.setText("Comunicação encerrada");
                saveSignal(signalEcg);
                message("Success Saved!!");
            }
        });


        // first mSeries is a line
        mSeries = new LineGraphSeries<>();
        graphView.addSeries(mSeries);
        listPoints = new ArrayList<>();


        numSamples = 0;
   /* Teste rápido. O hardware Bluetooth do dispositivo Android
            está funcionando ou está bugado de forma misteriosa?
            Será que existe, pelo menos? Provavelmente existe.
         */
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            statusMessage.setText("Que pena! Hardware Bluetooth não está funcionando :(");
        } else {
            statusMessage.setText("Ótimo! Hardware Bluetooth está funcionando :)");
        }

        /* A chamada do seguinte método liga o Bluetooth no dispositivo Android
            sem pedido de autorização do usuário. É altamente não recomendado no
            Android Developers, mas, para simplificar este app, que é um demo,
            faremos isso. Na prática, em um app que vai ser usado por outras
            pessoas, não faça isso.
         */
        btAdapter.enable();

        /* Definição da thread de conexão como cliente.
            Aqui, você deve incluir o endereço MAC do seu módulo Bluetooth.
            O app iniciará e vai automaticamente buscar por esse endereço.
            Caso não encontre, dirá que houve um erro de conexão.
         */
        connect = new ConnectionThread("00:21:13:02:42:0B");
        connect.start();

        /* Um descanso rápido, para evitar bugs esquisitos.
         */
        try {
            Thread.sleep(1000);
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }
    }

    private void saveSignal(ArrayList<String> signalEcg) {
        File file, dir;
        FileOutputStream fos;

        String fileName = "ECG-teste-3.txt";

        byte[] data;
        String signal;
        ArrayList<String> newData = new ArrayList<>();
        int hb, lb;
        try {
            dir = new File(getDirectory());
            file = new File(dir, fileName);

            int fileLines;

            for (fileLines = 0; fileLines < signalEcg.size() - 1; fileLines++) {

                if (signalEcg.get(fileLines).equals(("165")) ) {
                    hb = Integer.parseInt(signalEcg.get(fileLines + 4));
                    lb = Integer.parseInt(signalEcg.get(fileLines + 5));
                    newData.add(String.valueOf((hb << 8) + lb));
                }

            }

            for (fileLines = 0; fileLines < newData.size() - 1; fileLines++) {
                signal = newData.get(fileLines) + " ";
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
                statusMessage.setText("Ocorreu um erro durante a conexão D:");
            else if (dataString.equals("---S"))
                statusMessage.setText("Conectado :D");
            else {

                /* Se a mensagem não for um código de status,
                    então ela deve ser tratada pelo aplicativo
                    como uma mensagem vinda diretamente do outro
                    lado da conexão. Nesse caso, simplesmente
                    atualizamos o valor contido no TextView do
                    contador.
                 */

                //counterMessage.setText(dataString);
                signalEcg.add(dataString);
//                DataPoint p =  new DataPoint(numSamples, Double.valueOf(dataString));
//                mSeries.appendData(p,true,1024,true);
//                graphView.addSeries(mSeries);
//                if( numSamples==512)
//                {
//                    graphView.removeAllSeries();
//                    numSamples =0;
//                    mSeries.resetData(new DataPoint[] {});
//                }
//                numSamples++;
            }
        }
    };

    /* Esse método é invocado sempre que o usuário clicar na TextView
        que contem o contador. O app Android transmite a string "restart",
        seguido de uma quebra de linha, que é o indicador de fim de mensagem.
     */
    public void restartCounter(View view) {
        connect.write("restart\n".getBytes());
    }

}