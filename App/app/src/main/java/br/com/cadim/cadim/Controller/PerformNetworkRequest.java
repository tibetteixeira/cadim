package br.com.cadim.cadim.Controller;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

public class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

    private String url;
    private HashMap<String, String> params;
    private int requestCode;
    private JSONObject object;

    public PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
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
