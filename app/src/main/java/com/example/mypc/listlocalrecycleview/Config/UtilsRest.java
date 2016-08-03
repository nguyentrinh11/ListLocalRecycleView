package com.example.mypc.listlocalrecycleview.Config;


import com.example.mypc.listlocalrecycleview.APIInterface.APIInterface;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hairom on 6/9/16.
 */
public class UtilsRest {
    public static APIInterface getInterfaceService() {

        //  OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //  OkHttpClient client = httpClient.build();
//                Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                final Request request = chain.request().newBuilder()
//                        .addHeader("Content-type", "application/json")
//                        .build();
//
//                return chain.proceed(request);
//            }
//        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //builder.addInterceptor(interceptor);
//        builder.cookieJar(new MyCookieJar());
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(APIInterface.class);
    }

    public static APIInterface getInterfaceServiceWithAuth() {

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();

                return chain.proceed(request);
            }
        };
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(APIInterface.class);
    }

}
