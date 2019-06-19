package br.com.cadim.cadim.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import br.com.cadim.cadim.Controller.Api;
import br.com.cadim.cadim.Controller.DiagnosticController;
import br.com.cadim.cadim.Controller.PerformNetworkRequest;
import br.com.cadim.cadim.Model.Diagnostico;
import br.com.cadim.cadim.Model.Ecg;
import br.com.cadim.cadim.Model.Paciente;
import br.com.cadim.cadim.R;

import static br.com.cadim.cadim.Controller.PerformNetworkRequest.CODE_POST_REQUEST;

public class LoadDiagnosticActivity extends AppCompatActivity {

    ArrayList<Ecg> ecgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.load_screen);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Carregando Diagnósticos...");

        Paciente paciente = getIntent().getExtras().getParcelable("paciente");
        ecgList = getIntent().getParcelableArrayListExtra("listaEcg");
        carregarDiagnosticos(paciente.getCpf());
    }

    public void carregarDiagnosticos(String cpf) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cpf", cpf);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DIAGNOSTIC_LIST,
                params, CODE_POST_REQUEST);
        try {
            JSONObject objectLoadDiagnostic = new JSONObject(request.execute().get());

            if (!objectLoadDiagnostic.getBoolean("error")) {
                ArrayList<Diagnostico> listDiagnostic = DiagnosticController.getListDiagnostic(objectLoadDiagnostic);

                Paciente paciente = getIntent().getExtras().getParcelable("paciente");

                Intent listDiagnosticIntent = new Intent(LoadDiagnosticActivity.this,
                        DiagnosticListActivity.class);

                listDiagnosticIntent.putExtra("listDiagnostic", listDiagnostic);
                listDiagnosticIntent.putExtra("paciente", paciente);
                listDiagnosticIntent.putExtra("diagnosticLength", listDiagnostic.size());
                listDiagnosticIntent.putParcelableArrayListExtra("listaEcg", ecgList);

                startActivity(listDiagnosticIntent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
