package br.com.cadim.cadim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListaDiagnosticoActivity extends AppCompatActivity {

    private static String[] nomes = {"nome 1", "nome 2", "nome 3", "nome 4", "nome 5"};
    private static String[] crms = {"crm 1", "crm 2", "crm 3", "crm 4", "crm 5"};
    private static String[] descricoes = {"descricao 1", "descricao 2", "descricao 3", "descricao 4", "descricao 5"};
    private static String[] datas_horas = {"data_hora 1", "data_hora 2", "data_hora 3", "data_hora 4", "data_hora 5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.lista_diagnostico);

        ListView listDiagnostico = (ListView) findViewById(R.id.lista_diagnostico);
        CustomListDiagnostico cld = new CustomListDiagnostico();

        listDiagnostico.setAdapter(cld);
    }

    class CustomListDiagnostico extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
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
