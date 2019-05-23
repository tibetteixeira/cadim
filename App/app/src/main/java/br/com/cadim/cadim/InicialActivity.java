package br.com.cadim.cadim;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.util.Objects;

public class InicialActivity extends AppCompatActivity {

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
                Intent realizarExameIntent = new Intent(InicialActivity.this, AquisitionEcgActivity.class);
                startActivity(realizarExameIntent);
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
                System.out.println("Diagnostico");
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
