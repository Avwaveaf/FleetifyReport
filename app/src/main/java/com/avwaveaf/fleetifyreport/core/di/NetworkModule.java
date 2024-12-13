package com.avwaveaf.fleetifyreport.core.di;

import android.content.Context;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    private static final String BASE_URL = "https://public-api-staging-dot-fair-catcher-256606.el.r.appspot.com/";

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(@ApplicationContext Context context) {
        // Logging interceptor to log request and response
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        // Chucker interceptor for logging network requests
        ChuckerInterceptor chuckerInterceptor = new ChuckerInterceptor.Builder(context)
                .maxContentLength(250_000L)
                .alwaysReadResponseBody(true)
                .build();

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chuckerInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .setLenient()
                                .create()
                ))
                .baseUrl(BASE_URL) // Replace with your base URL
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }
}
