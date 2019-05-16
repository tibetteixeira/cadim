package br.com.cadim.cadim;

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

public class LoginActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
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


        //validating the inputs
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

        //if validation passes

        HashMap<String, String> params = new HashMap<>();
        params.put("cpf", cpf);
        params.put("senha", senha);

        //Calling the create hero API
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_LOGIN, params, CODE_POST_REQUEST);
        request.execute();
        JSONObject jsonObject = request.object;
        if (request.object != null) {
            try {
                String nome = ((JSONObject) jsonObject.get("login")).getString("nome");
                System.out.println("Nome aqui ----->: " + nome);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent listaDiagnosticoIntent = new Intent(LoginActivity.this, ListaDiagnosticoActivity.class);
            startActivity(listaDiagnosticoIntent);
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

            try {
                System.out.println(s);
                this.object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
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
