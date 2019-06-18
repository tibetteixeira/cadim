package br.com.cadim.cadim.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import br.com.cadim.cadim.Controller.Api;
import br.com.cadim.cadim.Controller.LoginController;
import br.com.cadim.cadim.Controller.PerformNetworkRequest;
import br.com.cadim.cadim.Model.Ecg;
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

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_LOGIN, params, PerformNetworkRequest.CODE_POST_REQUEST);
        try {

            JSONObject objectLogin = new JSONObject(request.execute().get());

            if (!objectLogin.getBoolean("error")) {
                if (objectLogin.get("login").equals("false")) {
                    Toast.makeText(getApplicationContext(), "Usuário ou senha inválido",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Login realizado com sucesso",
                            Toast.LENGTH_SHORT).show();

                    Paciente paciente = LoginController.getPaciente(objectLogin);
                    ArrayList<Ecg> ecgTodayList = LoginController.getEcg(objectLogin, paciente.getCpf());

                    Intent loadIncialIntent = new Intent(LoginActivity.this,
                            HomeActivity.class);

                    loadIncialIntent.putParcelableArrayListExtra("listaEcg", ecgTodayList);
                    loadIncialIntent.putExtra("paciente", paciente);

                    startActivity(loadIncialIntent);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Usuário ou senha inválido",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
