package br.com.cadim.cadim.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import br.com.cadim.cadim.DAO.Api;
import br.com.cadim.cadim.DAO.RequestHandler;
import br.com.cadim.cadim.Model.Paciente;
import br.com.cadim.cadim.R;

public class LoginActivity extends AppCompatActivity {

    private EditText cpfEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.login);

        cpfEditText = (EditText) findViewById(R.id.cpf);
        passwordEditText = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        String cpf = cpfEditText.getText().toString();
        String senha = passwordEditText.getText().toString();


        if (TextUtils.isEmpty(cpf)) {
            cpfEditText.setError("Por favor insira o CPF");
            cpfEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(senha)) {
            passwordEditText.setError("Por favor insira a senha");
            passwordEditText.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("cpf", cpf);
        params.put("senha", senha);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_LOGIN, params, MainActivity.CODE_POST_REQUEST);
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
                    Toast.makeText(getApplicationContext(), "Login realizado com sucesso",
                            Toast.LENGTH_SHORT).show();

                    String cpf = ((JSONObject) object.get("login")).getString("cpf");
                    String nome = ((JSONObject) object.get("login")).getString("nome");
                    String dataNascimento = ((JSONObject) object.get("login")).getString("data_nasc");
                    String email = ((JSONObject) object.get("login")).getString("email");
                    String senha = ((JSONObject) object.get("login")).getString("senha");
                    String sexo = ((JSONObject) object.get("login")).getString("sexo");
                    int altura = ((JSONObject) object.get("login")).getInt("altura");
                    double peso = ((JSONObject) object.get("login")).getDouble("peso");
                    int telefone = ((JSONObject) object.get("login")).getInt("telefone");

                    Paciente paciente = new Paciente(cpf, nome, dataNascimento, email, senha, sexo, altura, peso, telefone);

                    Intent loadIncialIntent = new Intent(LoginActivity.this,
                            HomeActivity.class);
                    loadIncialIntent.putExtra("paciente", paciente);
                    startActivity(loadIncialIntent);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == MainActivity.CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == MainActivity.CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

}
