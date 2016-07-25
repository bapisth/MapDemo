package com.esspl.hemendra.mapdemo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hemendra on 25-07-2016.
 */
public class ServiceFactory {
    public static <T> T createRetrofitService(final Class<T> clazz, final String endpoint){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(loggingInterceptor);

        /*OkHttpClient okClient = new OkHttpClient();
        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response;
            }
        });*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                //.client(okClient)
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        T service = retrofit.create(clazz);
        return service;
    }
}
