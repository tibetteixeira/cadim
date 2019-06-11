package br.com.cadim.cadim.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.cadim.cadim.Model.Diagnostico;
import br.com.cadim.cadim.Model.Ecg;
import br.com.cadim.cadim.Model.Paciente;
import br.com.cadim.cadim.R;

public class DiagnosticListActivity extends AppCompatActivity {

    private static ArrayList<Integer> ecgs;
    private static ArrayList<Integer> diagnosticos;
    private static ArrayList<String> nomes;
    private static ArrayList<String> crms;
    private static ArrayList<String> descricoes;
    private static ArrayList<String> datas_horas;
    private static ListView diagnosticList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.diagnostic_list);

        int ecgLength = getIntent().getIntExtra("ecgLength", 0);

        if (ecgLength == 0) {
            DialogNoDiagnostic();
        }

        diagnosticList = (ListView) findViewById(R.id.diagnostics);

        ImageButton btnExame = (ImageButton) findViewById(R.id.buttonExame);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.buttonSettings);
        CustomListDiagnostico cld = new CustomListDiagnostico();

        ecgs = getIntent().getExtras().getIntegerArrayList("ecgs");
        diagnosticos = getIntent().getExtras().getIntegerArrayList("diagnosticos");
        descricoes = getIntent().getExtras().getStringArrayList("descricoes");
        nomes = getIntent().getExtras().getStringArrayList("nomes");
        crms = getIntent().getExtras().getStringArrayList("crms");
        datas_horas = getIntent().getExtras().getStringArrayList("datas_horas");


        btnExame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paciente paciente = getIntent().getExtras().getParcelable("paciente");
                ArrayList<Ecg> ecgList = getIntent().getParcelableArrayListExtra("listaEcg");

                Intent inicialIntent = new Intent(DiagnosticListActivity.this,
                        HomeActivity.class);
                inicialIntent.putExtra("paciente", paciente);
                inicialIntent.putParcelableArrayListExtra("listaEcg", ecgList);
                startActivity(inicialIntent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Settings");
            }
        });

        diagnosticList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Diagnostico diagnostico = (Diagnostico) diagnosticList.getAdapter()
                        .getItem(position);

                Intent diagnosticIntent = new Intent(DiagnosticListActivity.this,
                        DiagnosticActivity.class);
                diagnosticIntent.putExtra("diagnostic", diagnostico);
                startActivity(diagnosticIntent);
            }
        });

        diagnosticList.setAdapter(cld);
    }

    public void DialogNoDiagnostic() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Você não possui diagnósticos cadastrados");
        builder.setCancelable(true);
        builder.setNeutralButton("Voltar à tela inicial", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Paciente paciente = getIntent().getExtras().getParcelable("paciente");
                ArrayList<Ecg> ecgList = getIntent().getParcelableArrayListExtra("listaEcg");

                Intent returnHomeIntent = new Intent(DiagnosticListActivity.this, HomeActivity.class);
                returnHomeIntent.putExtra("paciente", paciente);
                returnHomeIntent.putParcelableArrayListExtra("listaEcg", ecgList);
                startActivity(returnHomeIntent);
            }
        });
        builder.show();
    }

    class CustomListDiagnostico extends BaseAdapter {

        @Override
        public int getCount() {
            return diagnosticos.size();
        }

        @Override
        public Diagnostico getItem(int i) {

            Diagnostico diagnostico = new Diagnostico(diagnosticos.get(i),
                    ecgs.get(i),
                    descricoes.get(i),
                    crms.get(i),
                    nomes.get(i),
                    datas_horas.get(i));

            return diagnostico;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint({"ViewHolder", "InflateParams", "SetTextI18n"})
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_diagnostic_list, null);

            TextView nome = (TextView) view.findViewById(R.id.nome);
            TextView crm = (TextView) view.findViewById(R.id.crm);
            TextView descricao = (TextView) view.findViewById(R.id.descricao);
            TextView data_hora = (TextView) view.findViewById(R.id.data_hora);

            nome.setText("Dr. " + nomes.get(i));
            crm.setText("CRM: " + crms.get(i));
            int tamanhoDescricao = descricoes.get(i).length();
            if (tamanhoDescricao >= 150)
                descricao.setText(descricoes.get(i).substring(0, 150) + "...");
            else
                descricao.setText(descricoes.get(i).substring(0, tamanhoDescricao));
            data_hora.setText(datas_horas.get(i));

            return view;
        }
    }
}
