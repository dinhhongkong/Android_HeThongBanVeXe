package com.android.service.api_interface;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.1.17:8080/";
    private static ApiClient instance; // Biến duy nhất của ApiClient
    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient client;
    private static Retrofit retrofitInstance;
    private String authToken; // Biến lưu trữ token

    private ApiClient(String token) {
        this.authToken = token;
        client = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .addInterceptor(chain -> {
                    var original = chain.request();
                    var requestBuilder = original.newBuilder()
                            .header("Authorization", "Bearer " + authToken) // Thêm token vào header
                            .method(original.method(), original.body());
                    var request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .build();

        retrofitInstance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



    public static synchronized Retrofit getRetrofitInstance(String token) {
        return new ApiClient(token).getRetrofit();
    }

    public static synchronized Retrofit getRetrofitInstance() {
        return new ApiClient("").getRetrofit();
    }

    private Retrofit getRetrofit() {
        return retrofitInstance;
    }
}
