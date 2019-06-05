package br.com.cadim.cadim;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Rest {
    public static final String BASE_URL = "http://192.168.1.104:8080/";
    private static RetrofitInterface retrofitInterface;

    public static synchronized RetrofitInterface getRetrofit() {
        if (retrofitInterface == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofitInterface = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build().create(RetrofitInterface.class);
        }
        return retrofitInterface;
    }

    public interface RetrofitInterface {

        @POST("json")
        Call<MSGString> sendString(@Body MSGString body);

    }

    public static class MSGString {
        public int pac_id;
        public String ecg_file;
        public Double imc;
        public String marcapasso;
        public int pressao_sistolica;
        public String cancer;
        public int pressao_diastolica;
        public String tabagismo;
        public String alcoolismo;
        public String sincope;
        public String sedentarismo;
        public String fibrilacao_fluter;
        public String avc;
        public String file;

    }


}