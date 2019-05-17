package br.com.cadim.cadim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.cadim.cadim.Model.Paciente;

public class ListaDiagnosticoActivity extends AppCompatActivity {

    private static String[] nomes;
    private static String[] crms;
    private static String[] descricoes;
    private static String[] datas_horas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.lista_diagnostico);

        Paciente paciente = getIntent().getExtras().getParcelable("paciente");

        System.out.println(paciente.getCpf());
        System.out.println(paciente.getNome());
        System.out.println(paciente.getEmail());

        nomes = new String[]{"nome 1", "nome 2", "nome 3", "nome 4", "nome 5"};
        crms = new String[]{"crm 1", "crm 2", "crm 3", "crm 4", "crm 5"};
        descricoes = new String[]{"descricao 1", "descricao 2", "descricao 3", "descricao 4", "descricao 5"};
        datas_horas = new String[]{"data_hora 1", "data_hora 2", "data_hora 3", "data_hora 4", "data_hora 5"};

        ListView listDiagnostico = (ListView) findViewById(R.id.lista_diagnostico);
        CustomListDiagnostico cld = new CustomListDiagnostico();

        listDiagnostico.setAdapter(cld);
    }

    class CustomListDiagnostico extends BaseAdapter {

        @Override
        public int getCount() {
            return nomes.length;
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

            nome.setText(nomes[i]);
            crm.setText(crms[i]);
            descricao.setText(descricoes[i]);
            data_hora.setText(datas_horas[i]);

            return view;
        }
    }
}
