package br.com.cadim.cadim.Controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.cadim.cadim.Model.Diagnostico;

public class DiagnosticController {

    public static Diagnostico returnDiagnostic(JSONObject object) {
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

    public static ArrayList<Diagnostico> getListDiagnostic(JSONObject objectLoadDiagnostic) {
        try {
            ArrayList<Diagnostico> listDianostic = new ArrayList<>();
            JSONArray diagnostics = (JSONArray) objectLoadDiagnostic.get("diagnostic");

            for (int i = 0; i < diagnostics.length(); i++) {

                JSONObject diagnostico = (JSONObject) diagnostics.get(i);
                listDianostic.add(new Diagnostico(
                        diagnostico.getInt("diagnostico_id"),
                        diagnostico.getInt("ecg_id"),
                        diagnostico.getString("descricao"),
                        diagnostico.getString("crm"),
                        diagnostico.getString("nome"),
                        diagnostico.getString("data_hora_diagnostico")

                ));
            }

            return listDianostic;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
