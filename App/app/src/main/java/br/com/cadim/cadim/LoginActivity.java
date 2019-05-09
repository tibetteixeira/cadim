package br.com.cadim.cadim;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import br.com.cadim.cadim.Test.Api;
import br.com.cadim.cadim.Test.RequestHandler;

public class LoginActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private EditText cpfEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login);

        cpfEditText = (EditText) findViewById(R.id.cpf);
        passwordEditText = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);

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
    }


    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //the url where we need to send the request
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        JSONObject object;

        //constructor to initialize values
        public PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
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
                    String nome = ((JSONObject) object.get("login")).getString("nome");
                    String mensagem = "Bem vindo " + nome + ". " + object.getString("message");
                    Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_SHORT).show();
                    //refreshHeroList(object.getJSONArray("heroes"));
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
