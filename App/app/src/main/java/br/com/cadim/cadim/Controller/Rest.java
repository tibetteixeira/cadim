package br.com.cadim.cadim.Controller;

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
    // server to send
    private static final String BASE_URL = "http://10.101.52.168:8080/";
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
        public String cpf;
        public String ecgFile;
        public String dataHora;
        public double imc;
        public String signalECG;
    }


}