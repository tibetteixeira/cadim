package br.com.cadim.cadim;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.util.Objects;

import br.com.cadim.cadim.Model.Paciente;

public class InicialActivity extends AppCompatActivity {
    private static final int SELECT_DISCOVERED_DEVICE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.tela_inicial);

        FloatingActionButton btnAddExame = (FloatingActionButton) findViewById(R.id.addExame);

        ImageButton btnExame = (ImageButton) findViewById(R.id.buttonExame);
        ImageButton btnDiagnostico = (ImageButton) findViewById(R.id.buttonDiagnosticos);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.buttonSettings);

        btnAddExame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent searchDevicesIntent = new Intent(InicialActivity.this, FoundDevices.class);
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
                Intent carregarDiagnosticos = new Intent(InicialActivity.this, LoadDiagnostic.class);
                Paciente paciente = getIntent().getExtras().getParcelable("paciente");
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
}
