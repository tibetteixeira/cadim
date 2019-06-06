package br.com.cadim.cadim.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.util.Objects;

import br.com.cadim.cadim.Controller.FoundDevices;
import br.com.cadim.cadim.Model.Paciente;
import br.com.cadim.cadim.R;

public class HomeActivity extends AppCompatActivity {
    private static final int SELECT_DISCOVERED_DEVICE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.home_screen);

        FloatingActionButton btnAddExame = (FloatingActionButton) findViewById(R.id.addExame);

        ImageButton btnExame = (ImageButton) findViewById(R.id.buttonExame);
        ImageButton btnDiagnostico = (ImageButton) findViewById(R.id.buttonDiagnosticos);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.buttonSettings);

        btnAddExame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent searchDevicesIntent = new Intent(HomeActivity.this, FoundDevices.class);
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
                Intent carregarDiagnosticos = new Intent(HomeActivity.this, LoadDiagnosticActivity.class);
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
