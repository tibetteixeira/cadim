package br.com.cadim.cadim.Controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.cadim.cadim.Model.Ecg;
import br.com.cadim.cadim.Model.Paciente;

public class LoginController {

    public static Paciente getPaciente(JSONObject objectLogin) {
        try {
            String cpf = ((JSONObject) objectLogin.get("login")).getString("cpf");
            String nome = ((JSONObject) objectLogin.get("login")).getString("nome");
            String dataNascimento = ((JSONObject) objectLogin.get("login")).getString("data_nasc");
            String email = ((JSONObject) objectLogin.get("login")).getString("email");
            String senha = ((JSONObject) objectLogin.get("login")).getString("senha");
            String sexo = ((JSONObject) objectLogin.get("login")).getString("sexo");
            int altura = ((JSONObject) objectLogin.get("login")).getInt("altura");
            double peso = ((JSONObject) objectLogin.get("login")).getDouble("peso");
            int telefone = ((JSONObject) objectLogin.get("login")).getInt("telefone");

            return new Paciente(cpf, nome, dataNascimento, email, senha, sexo, altura, peso, telefone);
        } catch (JSONException e) {
            e.printStackTrace();

        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Ecg> getEcg(JSONObject objectLogin, String cpf) {
        try {
            JSONArray ecgArray = ((JSONObject) objectLogin.get("login")).getJSONArray("ecg");

            ArrayList<Ecg> ecgTodayList = new ArrayList<>();
            JSONObject ecg;

            if (!(((JSONObject) ecgArray.get(0)).getString("data_hora").equals("null"))) {
                for (int i = 0; i < ecgArray.length(); i++) {
                    ecg = (JSONObject) ecgArray.get(i);

                    ecgTodayList.add(
                            new Ecg(Integer.parseInt(ecg.getString("ecg_id")),
                                    ecg.getString("ecg_file"),
                                    cpf,
                                    ecg.getString("data_hora"),
                                    Double.parseDouble(ecg.getString("imc"))));
                }
                return ecgTodayList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

