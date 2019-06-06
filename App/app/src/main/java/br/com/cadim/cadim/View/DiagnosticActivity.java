package br.com.cadim.cadim.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import br.com.cadim.cadim.Model.Diagnostico;
import br.com.cadim.cadim.R;

public class DiagnosticActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.diagnostic);

        Diagnostico diagnostico = getIntent().getExtras().getParcelable("diagnostic");

        TextView nome = (TextView) findViewById(R.id.nome);
        TextView crm = (TextView) findViewById(R.id.crm);
        TextView descricao = (TextView) findViewById(R.id.descricao);
        TextView data_hora = (TextView) findViewById(R.id.data_hora);

        nome.setText(diagnostico.getNomeMedico());
        crm.setText(diagnostico.getCrm());
        descricao.setText(diagnostico.getDescricao());
        data_hora.setText(diagnostico.getDataHora());
    }
}
