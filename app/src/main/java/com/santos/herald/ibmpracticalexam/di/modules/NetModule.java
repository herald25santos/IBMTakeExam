package com.santos.herald.ibmpracticalexam.di.modules;

import android.app.Application;
import android.util.Log;

import com.santos.herald.ibmpracticalexam.App;
import com.santos.herald.ibmpracticalexam.BuildConfig;
import com.santos.herald.ibmpracticalexam.network.ApiServices;
import com.santos.herald.ibmpracticalexam.network.exception.RxErrorHandlingCallAdapterFactory;
import com.santos.herald.ibmpracticalexam.utils.NetworkUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    private String mBaseUrl;

    public NetModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
        Request request = chain.request();
        String applyResponseCache = request.header("ApplyResponseCache");
        if (request.method().equals("GET") && applyResponseCache != null) {
            if (Boolean.valueOf(applyResponseCache)) {
                Log.d("CACHE_CONTROL", "cache applied");
                return chain.proceed(request.newBuilder().removeHeader("ApplyResponseCache").cacheControl(CacheControl.FORCE_CACHE).build());
            } else {
                Log.d("CACHE_CONTROL", "cache not applied");
                return chain.proceed(request.newBuilder().removeHeader("ApplyResponseCache").cacheControl(CacheControl.FORCE_NETWORK).build());
            }
        } else {
            Log.d("CACHE_CONTROL", "cache not applied");
            return chain.proceed(request.newBuilder().removeHeader("ApplyResponseCache").cacheControl(CacheControl.FORCE_NETWORK).build());
        }
    };

    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    Interceptor provideOfflineInterceptor() {
        return chain -> {
            Request request = chain.request();
            if (!NetworkUtils.isNetworkConnected(App.getApplication()) && request.method().equals("GET")) {
                Log.d("Offline_Interceptor", "request from :: " + chain.request().url().toString());
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 2419200).build();
            }
            return chain.proceed(request);
        };
    }

    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    @Singleton
    @Provides
    @Named("ok-1")
    OkHttpClient provideOkHttpClient(Cache cache) {

        OkHttpClient.Builder client = new OkHttpClient().newBuilder();
        client.readTimeout(180, TimeUnit.SECONDS);
        client.connectTimeout(30, TimeUnit.SECONDS);
        client.writeTimeout(30, TimeUnit.SECONDS);
        client.cache(cache);
        client.retryOnConnectionFailure(true);
        client.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        if(BuildConfig.DEBUG){
            client.interceptors().add(provideOfflineInterceptor());
            client.interceptors().add(provideLoggingInterceptor());
        }
        client.interceptors().add(chain -> {

            okhttp3.Request original = chain.request();

            okhttp3.Request.Builder request = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method(), original.body());

            return chain.proceed(request.build());
        });

        return client.build();

    }

    @Singleton
    @Provides
    @Named("ok-2")
    OkHttpClient provideOkHttpClient2() {

        OkHttpClient.Builder client = new OkHttpClient().newBuilder();
        client.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.interceptors().add(interceptor);
        client.interceptors().add(chain -> {

            okhttp3.Request original = chain.request();

            okhttp3.Request.Builder request = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method(), original.body());

            return chain.proceed(request.build());
        });

        return client.build();
    }

    @Singleton
    @Provides
    CallAdapter.Factory provideRxJavaCallAdapterFactory() {
        return RxErrorHandlingCallAdapterFactory.create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(@Named("ok-1") OkHttpClient client, GsonConverterFactory converterFactory, CallAdapter.Factory adapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    ApiServices provideApiServices(Application application) {
        return provideRetrofit(provideOkHttpClient(provideOkHttpCache(application)), provideGsonConverterFactory(), provideRxJavaCallAdapterFactory()).create(ApiServices.class);
    }

}