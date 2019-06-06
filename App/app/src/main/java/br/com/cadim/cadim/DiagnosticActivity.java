package br.com.cadim.cadim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import br.com.cadim.cadim.Model.Diagnostico;
import br.com.cadim.cadim.Model.Paciente;

public class DiagnosticActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.diagnostic);

        Diagnostico diagnostico = getIntent().getExtras().getParcelable("diagnostic");

        System.out.println(diagnostico.getDescricao());

    }
}
