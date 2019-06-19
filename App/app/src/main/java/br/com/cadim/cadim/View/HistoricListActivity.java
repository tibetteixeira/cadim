package br.com.cadim.cadim.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import br.com.cadim.cadim.Controller.Api;
import br.com.cadim.cadim.Controller.RequestHandler;
import br.com.cadim.cadim.Model.Diagnostico;
import br.com.cadim.cadim.Model.Ecg;
import br.com.cadim.cadim.Model.Paciente;
import br.com.cadim.cadim.R;

import static br.com.cadim.cadim.Controller.PerformNetworkRequest.CODE_POST_REQUEST;
import static br.com.cadim.cadim.Controller.PerformNetworkRequest.CODE_GET_REQUEST;

public class HistoricListActivity extends AppCompatActivity {

    private static ArrayList<Integer> ecgs;
    private static ArrayList<String> files;
    private static double[] imcs;
    private static ArrayList<String> cpfs;
    private static ArrayList<String> datas_horas;
    private static ListView historicList;
    ArrayList<Ecg> ecgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.historic_list);

        int ecgLength = getIntent().getIntExtra("ecgLength", 0);

        if (ecgLength == 0) {
            DialogNoHistoric();
        }

        historicList = (ListView) findViewById(R.id.historic);

        ImageButton btnDiagnostico = (ImageButton) findViewById(R.id.buttonDiagnosticos);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.buttonSettings);
        Historic historic = new Historic();

        ecgs = getIntent().getExtras().getIntegerArrayList("ecgs");
        files = getIntent().getExtras().getStringArrayList("files");
        imcs = getIntent().getExtras().getDoubleArray("imcs");
        cpfs = getIntent().getExtras().getStringArrayList("cpfs");
        datas_horas = getIntent().getExtras().getStringArrayList("datas_horas");


        btnDiagnostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paciente paciente = getIntent().getParcelableExtra("paciente");
                ArrayList<Ecg> ecgList = getIntent().getParcelableArrayListExtra("listaEcg");

                Intent carregarDiagnosticos = new Intent(HistoricListActivity.this,
                        LoadDiagnosticActivity.class);
                carregarDiagnosticos.putExtra("paciente", paciente);
                carregarDiagnosticos.putParcelableArrayListExtra("listaEcg", ecgList);
                startActivity(carregarDiagnosticos);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Settings");
            }
        });

        historicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ecg ecg = (Ecg) historicList.getAdapter()
                        .getItem(position);
                menuExam(ecg);
            }
        });

        historicList.setAdapter(historic);
    }

    public void menuExam(final Ecg ecgSelected) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione").
                setItems(R.array.ecg_menu_options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                showExam(ecgSelected);
                                break;
                            case 1:
                                try {
                                    showDiagnotic(ecgSelected);
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                showInformation(ecgSelected);
                                break;
                        }
                    }
                });
        builder.show();
    }

    public void DialogNoHistoric() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Você não possui exames cadastrados");
        builder.setCancelable(true);
        builder.setNeutralButton("Voltar à tela inicial", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Paciente paciente = getIntent().getExtras().getParcelable("paciente");
                ArrayList<Ecg> ecgList = getIntent().getParcelableArrayListExtra("listaEcg");

                Intent returnHomeIntent = new Intent(HistoricListActivity.this, HomeActivity.class);
                returnHomeIntent.putExtra("paciente", paciente);
                returnHomeIntent.putParcelableArrayListExtra("listaEcg", ecgList);
                startActivity(returnHomeIntent);
            }
        });
        builder.show();
    }

    private void showExam(Ecg ecg) {
        Intent showExamIntent = new Intent(HistoricListActivity.this, ReadEcgActivity.class);
        showExamIntent.putExtra("ecg", ecg);
        startActivity(showExamIntent);
    }

    private void showDiagnotic(Ecg ecg) throws ExecutionException, InterruptedException, JSONException {
        Diagnostico diagnostico = diagnosticCheck(ecg.getEcgId());

        if (diagnostico != null) {
            Intent diagnosticIntent = new Intent(HistoricListActivity.this, DiagnosticActivity.class);
            diagnosticIntent.putExtra("diagnostic", diagnostico);
            startActivity(diagnosticIntent);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Ainda não há dianósticos para esse exame",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showInformation(Ecg ecg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informações");
        builder.setCancelable(true);
        builder.setMessage(ecg.getDataHora() + "\n" + "67 bpm" + "\n" + "15 s");
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Ao clicar, o AlertDialog fechará
            }
        });
        builder.show();
    }


    private Diagnostico diagnosticCheck(int ecgId) throws ExecutionException, InterruptedException, JSONException {
        HashMap<String, String> params = new HashMap<>();
        params.put("ecgId", String.valueOf(ecgId));

        PerformNetworkRequest request = new PerformNetworkRequest(
                Api.URL_DIAGNOSTIC_ECG_LIST, params, CODE_POST_REQUEST);

        JSONObject object = new JSONObject(request.execute().get());
        return returnDiagnostic(object);
    }

    public Diagnostico returnDiagnostic(JSONObject object) {
        Diagnostico diagnosticoRequest = null;
        try {
            JSONObject diagnosticoJSON = (JSONObject) object.get("diagnosticEcg");

            if (!object.getBoolean("error") && diagnosticoJSON.length() > 0) {
                int diagnosticoId = Integer.parseInt(diagnosticoJSON.getString("diagnostico_id"));
                int ecgId = Integer.parseInt(diagnosticoJSON.getString("ecg_id"));
                String crm = diagnosticoJSON.getString("crm");
                String nome = diagnosticoJSON.getString("nome");
                String dataHora = diagnosticoJSON.getString("data_hora_diagnostico");
                String descricao = diagnosticoJSON.getString("descricao");

                diagnosticoRequest = new Diagnostico(diagnosticoId, ecgId, descricao, crm, nome, dataHora);
            }

        } catch (ClassCastException e) {
            diagnosticoRequest = null;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return diagnosticoRequest;
    }

    class Historic extends BaseAdapter {


        @Override
        public int getCount() {
            return ecgs.size();
        }

        @Override
        public Ecg getItem(int i) {

            Ecg ecg = new Ecg(ecgs.get(i),
                    files.get(i),
                    cpfs.get(i),
                    datas_horas.get(i),
                    //        imcs[i]
                    0
            );

            return ecg;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_exam_list, null);

            TextView dataHoraEcg = (TextView) view.findViewById(R.id.data_hora);
            dataHoraEcg.setText(datas_horas.get(i));
            return view;
        }
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
        }

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
