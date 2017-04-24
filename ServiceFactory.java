package com.eric.net.util.net;

import com.eric.net.util.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    public static final String XIAOYAN_SERVER = "http://xxxxxxxxx.cn/";
    public static final String XIAOYAN_SERVER_TEST = "http://xxxxxxxxx.cn/";

    private static final OkHttpClient sClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("authorization", Context.oAuthUser().token)
                            .build();
//                    L.e("rxjava", "token:" + Context.oAuthUser().token);
                    return chain.proceed(newRequest);
                }
            }).build();

    public static <T> T createTestService(Class<T> serviceClazz) {
        return createOauthService(XIAOYAN_SERVER_TEST, serviceClazz);
    }
    public static <T> T createOauthService(String baseUrl, Class<T> serviceClazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(sClient)
                .baseUrl(baseUrl)
                //原生不支持，直接String返回结果
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClazz);
    }

    public static <T> T createOauthService(Class<T> serviceClazz) {

        return createOauthService(XIAOYAN_SERVER, serviceClazz);
    }

    public static <T> T createNoOauthService(Class<T> serviceClazz) {
        Retrofit retrofit = new Retrofit.Builder()
//                .client(sClient)
                .baseUrl(XIAOYAN_SERVER)
                //原生不支持，直接String返回结果
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClazz);
    }
}
