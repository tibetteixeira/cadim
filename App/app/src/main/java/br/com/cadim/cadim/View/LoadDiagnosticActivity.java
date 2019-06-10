package br.com.cadim.cadim.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import br.com.cadim.cadim.DAO.Api;
import br.com.cadim.cadim.DAO.RequestHandler;
import br.com.cadim.cadim.Model.Ecg;
import br.com.cadim.cadim.Model.Paciente;
import br.com.cadim.cadim.R;

import static br.com.cadim.cadim.View.MainActivity.CODE_POST_REQUEST;

public class LoadDiagnosticActivity extends AppCompatActivity {

    private static ArrayList<Integer> ecgs;
    private static ArrayList<Integer> diagnosticos;
    private static ArrayList<String> nomes;
    private static ArrayList<String> crms;
    private static ArrayList<String> descricoes;
    private static ArrayList<String> datas_horas;
    ArrayList<Ecg> ecgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.load_diagnostic);

        ecgs = new ArrayList<>();
        diagnosticos = new ArrayList<>();
        nomes = new ArrayList<>();
        crms = new ArrayList<>();
        descricoes = new ArrayList<>();
        datas_horas = new ArrayList<>();

        Paciente paciente = getIntent().getExtras().getParcelable("paciente");
        ecgList = getIntent().getParcelableArrayListExtra("listaEcg");
        carregarDiagnosticos(paciente.getCpf());
    }

    public void carregarDiagnosticos(String cpf) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cpf", cpf);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DIAGNOSTICLIST,
                params, CODE_POST_REQUEST);
        request.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        private String url;
        private HashMap<String, String> params;
        private int requestCode;
        private JSONObject object;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
            this.object = null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                System.out.println(s);

                this.object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    JSONArray diagnostics = (JSONArray) object.get("diagnostic");

                    for (int i = 0; i < diagnostics.length(); i++) {
                        JSONObject diagnostico = (JSONObject) diagnostics.get(i);
                        ecgs.add(diagnostico.getInt("ecg_id"));
                        diagnosticos.add(diagnostico.getInt("diagnostico_id"));
                        descricoes.add(diagnostico.getString("descricao"));
                        nomes.add(diagnostico.getString("nome"));
                        crms.add(diagnostico.getString("crm"));
                        datas_horas.add(diagnostico.getString("data_hora_diagnostico"));
                    }
                    System.out.println("ECG SIZE -----> " + ecgs.size());

                    Paciente paciente = getIntent().getExtras().getParcelable("paciente");

                    Intent listDiagnosticIntent = new Intent(LoadDiagnosticActivity.this,
                            DiagnosticListActivity.class);

                    listDiagnosticIntent.putExtra("ecgs", ecgs);
                    listDiagnosticIntent.putExtra("diagnosticos", diagnosticos);
                    listDiagnosticIntent.putExtra("descricoes", descricoes);
                    listDiagnosticIntent.putExtra("nomes", nomes);
                    listDiagnosticIntent.putExtra("crms", crms);
                    listDiagnosticIntent.putExtra("datas_horas", datas_horas);

                    listDiagnosticIntent.putExtra("paciente", paciente);
                    listDiagnosticIntent.putParcelableArrayListExtra("listaEcg", ecgList);

                    startActivity(listDiagnosticIntent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == MainActivity.CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
}
