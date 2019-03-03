package iit.ism.dhanbad.srijan;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetroClient {


    private static final String ROOT_URL = "https://graph.facebook.com/ism.srijan/";


    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}

