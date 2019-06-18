package br.com.cadim.cadim.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import br.com.cadim.cadim.Controller.Api;
import br.com.cadim.cadim.Controller.RequestHandler;
import br.com.cadim.cadim.Model.Ecg;
import br.com.cadim.cadim.Model.Paciente;
import br.com.cadim.cadim.R;

import static br.com.cadim.cadim.Controller.PerformNetworkRequest.CODE_POST_REQUEST;
import static br.com.cadim.cadim.Controller.PerformNetworkRequest.CODE_GET_REQUEST;

public class LoadHistoricActivity extends AppCompatActivity {

    private static ArrayList<Integer> ecgs;
    private static ArrayList<String> files;
    private static ArrayList<Double> imcs;
    private static ArrayList<String> cpfs;
    private static ArrayList<String> datas_horas;
    ArrayList<Ecg> ecgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.load_screen);

        ecgs = new ArrayList<>();
        files = new ArrayList<>();
        imcs = new ArrayList<>();
        cpfs = new ArrayList<>();
        datas_horas = new ArrayList<>();

        TextView textView = (TextView) findViewById(R.id.textView);

        textView.setText("Carregando Histórico...");

        Paciente paciente = getIntent().getExtras().getParcelable("paciente");
        ecgList = getIntent().getParcelableArrayListExtra("listaEcg");
        carregarHistorico(paciente.getCpf());
    }

    public void carregarHistorico(String cpf) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cpf", cpf);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_HISTORIC_LIST,
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
                    JSONArray historics = (JSONArray) object.get("historic");

                    for (int i = 0; i < historics.length(); i++) {
                        JSONObject diagnostico = (JSONObject) historics.get(i);
                        ecgs.add(diagnostico.getInt("ecg_id"));
                        files.add(diagnostico.getString("file"));
                        imcs.add(diagnostico.getDouble("imc"));
                        cpfs.add(diagnostico.getString("cpf"));
                        datas_horas.add(diagnostico.getString("data_hora_historico"));
                    }

                    Paciente paciente = getIntent().getExtras().getParcelable("paciente");

                    Intent listHistoricIntent = new Intent(LoadHistoricActivity.this,
                            HistoricListActivity.class);

                    listHistoricIntent.putExtra("ecgs", ecgs);
                    listHistoricIntent.putExtra("files", files);
                    listHistoricIntent.putExtra("imcs", imcs);
                    listHistoricIntent.putExtra("cpfs", cpfs);
                    listHistoricIntent.putExtra("datas_horas", datas_horas);

                    listHistoricIntent.putExtra("paciente", paciente);
                    listHistoricIntent.putExtra("ecgLength", ecgs.size());
                    listHistoricIntent.putParcelableArrayListExtra("listaEcg", ecgList);

                    startActivity(listHistoricIntent);
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


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
}
