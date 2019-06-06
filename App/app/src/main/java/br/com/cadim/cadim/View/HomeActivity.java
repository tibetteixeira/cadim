package br.com.cadim.cadim.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import br.com.cadim.cadim.Controller.FoundDevices;
import br.com.cadim.cadim.Model.Diagnostico;
import br.com.cadim.cadim.Model.Ecg;
import br.com.cadim.cadim.Model.Paciente;
import br.com.cadim.cadim.R;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int SELECT_DISCOVERED_DEVICE = 3;
    private Paciente paciente;
    ListView ecgTodayList;
    ArrayList<Ecg> ecgs;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();

        paciente = getIntent().getExtras().getParcelable("paciente");
        ecgs = getIntent().getParcelableArrayListExtra("listaEcg");

        if (ecgs != null) {
            setContentView(R.layout.today_exam);
            ecgTodayList = (ListView) findViewById(R.id.ecgsToday);
            CustomTodayExam cte = new CustomTodayExam();


            ecgTodayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Ecg ecg = (Ecg) ecgTodayList.getAdapter()
                            .getItem(position);
                    menuExam(ecg);
                }
            });

            ecgTodayList.setAdapter(cte);
        } else
            setContentView(R.layout.home_screen);

        FloatingActionButton btnAddExame = (FloatingActionButton) findViewById(R.id.addExame);

        ImageButton btnExame = (ImageButton) findViewById(R.id.buttonExame);
        ImageButton btnDiagnostico = (ImageButton) findViewById(R.id.buttonDiagnosticos);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.buttonSettings);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBluetooth();

        btnAddExame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchDevicesIntent = new Intent(HomeActivity.this,
                        FoundDevices.class);
                startActivityForResult(searchDevicesIntent, SELECT_DISCOVERED_DEVICE);
            }
        });

        btnExame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Exame");
            }
        });

        btnDiagnostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carregarDiagnosticos = new Intent(HomeActivity.this,
                        LoadDiagnosticActivity.class);
                carregarDiagnosticos.putExtra("paciente", paciente);
                startActivity(carregarDiagnosticos);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Settings");
            }
        });
    }

    private void menuExam(final Ecg ecg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione").
                setItems(R.array.ecg_menu_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                downloadExam(ecg);
                                break;
                            case 1:
                                showDiagnotic(ecg);
                                break;
                            case 2:
                                showInformation(ecg);
                                break;
                        }
                    }
                });
        builder.show();
    }

    private void downloadExam(Ecg ecg) {
        System.out.println("Mostra exame");
    }

    private void showDiagnotic(Ecg ecg) {
        Diagnostico diagnostico = diagnosticCheck(ecg.getEcgId());
        if (diagnostico != null) {
            Intent diagnosticIntent = new Intent(HomeActivity.this, DiagnosticActivity.class);
            diagnosticIntent.putExtra("diagnostic", diagnostico);
            startActivity(diagnosticIntent);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Ainda não há dianósticos para esse exame",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showInformation(Ecg ecg) {
        System.out.println("Mostra informação");
    }

    private void checkBluetooth() {

        if (bluetoothAdapter == null) {
            System.out.println("Hardware Bluetooth não suportado.");
        } else {
            if (bluetoothAdapter.isEnabled()) {
                if (bluetoothAdapter.isDiscovering()) {
                    System.out.println("O Bluetooth está atualmente no processo de descoberta do dispositivo.");
                } else {
                    System.out.println("Bluetooth ativado.");
                }
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                System.out.println("Solicitando permissão do Bluetooth");
            }
        }
    }

    private Diagnostico diagnosticCheck(int ecgId) {

        return null;
    }

    class CustomTodayExam extends BaseAdapter {


        @Override
        public int getCount() {
            return ecgs.size();
        }

        @Override
        public Ecg getItem(int i) {
            return ecgs.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_today_exam, null);

            TextView dataHoraEcg = (TextView) view.findViewById(R.id.data_hora);

            dataHoraEcg.setText(ecgs.get(i).getDataHora());
            return view;
        }
    }
}
