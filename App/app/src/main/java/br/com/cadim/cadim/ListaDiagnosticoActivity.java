package br.com.cadim.cadim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaDiagnosticoActivity extends AppCompatActivity {

    private static ArrayList<Integer> ecgs;
    private static ArrayList<Integer> diagnosticos;
    private static ArrayList<String> nomes;
    private static ArrayList<String> crms;
    private static ArrayList<String> descricoes;
    private static ArrayList<String> datas_horas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.lista_diagnostico);

        ecgs = getIntent().getExtras().getIntegerArrayList("ecgs");
        diagnosticos = getIntent().getExtras().getIntegerArrayList("diagnosticos");
        descricoes = getIntent().getExtras().getStringArrayList("descricoes");
        nomes = getIntent().getExtras().getStringArrayList("nomes");
        crms = getIntent().getExtras().getStringArrayList("crms");
        datas_horas = getIntent().getExtras().getStringArrayList("datas_horas");

        ListView listDiagnostico = (ListView) findViewById(R.id.lista_diagnostico);
        CustomListDiagnostico cld = new CustomListDiagnostico();

        listDiagnostico.setAdapter(cld);
    }

    class CustomListDiagnostico extends BaseAdapter {

        @Override
        public int getCount() {
            return diagnosticos.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_list_diagnostico, null);

            TextView nome = (TextView) view.findViewById(R.id.nome);
            TextView crm = (TextView) view.findViewById(R.id.crm);
            TextView descricao = (TextView) view.findViewById(R.id.descricao);
            TextView data_hora = (TextView) view.findViewById(R.id.data_hora);

            nome.setText(nomes.get(i));
            crm.setText("CRM: " + crms.get(i));
            descricao.setText(descricoes.get(i));
            data_hora.setText(datas_horas.get(i));

            return view;
        }
    }
}
