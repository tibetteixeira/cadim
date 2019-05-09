package br.com.cadim.cadim;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                verifyConnection();
            }
        }, 2000);
    }

    private void verifyConnection() {
        if (!isConected(MainActivity.this)) {
            DialogNoConnection();
        } else {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    public void DialogNoConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Não há conexão com a Internet");
        builder.setMessage("Verifique os dados móveis\nou o Wi-Fi.");
        builder.setCancelable(true);
        builder.setNeutralButton("Tentar novamente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                verifyConnection();
            }
        });
        builder.show();
    }

    public static boolean isConected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();

            return ni != null && ni.isConnected();
        }
        return false;
    }

}
