package br.com.cadim.cadim.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import br.com.cadim.cadim.Controller.FoundDevices;
import br.com.cadim.cadim.Model.Ecg;
import br.com.cadim.cadim.Model.Paciente;
import br.com.cadim.cadim.R;

public class HomeActivity extends AppCompatActivity {
    private static final int SELECT_DISCOVERED_DEVICE = 3;
    private Paciente paciente;
    ArrayList<Ecg> ecgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();


        paciente = getIntent().getExtras().getParcelable("paciente");
        ecgs = getIntent().getParcelableArrayListExtra("listaEcg");

        if (ecgs != null) {
            setContentView(R.layout.today_exam);
            ListView ecgTodayList = (ListView) findViewById(R.id.ecgsToday);
            CustomTodayExam cte = new CustomTodayExam();
            ecgTodayList.setAdapter(cte);
        }

        else
            setContentView(R.layout.home_screen);

        FloatingActionButton btnAddExame = (FloatingActionButton) findViewById(R.id.addExame);

        ImageButton btnExame = (ImageButton) findViewById(R.id.buttonExame);
        ImageButton btnDiagnostico = (ImageButton) findViewById(R.id.buttonDiagnosticos);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.buttonSettings);

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
