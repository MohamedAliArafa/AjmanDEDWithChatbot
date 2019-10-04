package com.ajman.ded.ae.data;

import com.ajman.ded.ae.DateDeserializer;
import com.ajman.ded.ae.NTLMAuthenticator;
import com.google.gson.GsonBuilder;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.stream.Format;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiBuilder {

    public static String NEWS_IMAGE_BASE_URL = "http://ded.sdg.ae/Ufiles/News/";
    public static String BASIC_BASE_URL = "http://site1.ajmanded.ae/";
    private static String NEWS_BASE_URL = "http://ded.sdg.ae/";
    private static String SITE_BASE_URL = "http://site4.ajmanded.ae";
//    Authorization Endpoint :  https://qa-id.uaepass.ae/trustedx-authserver/oauth/main-as
//    Token Endpoint :          https://qa-id.uaepass.ae/trustedx-authserver/oauth/main-as/token
//    User Info Endpoint :      https://qa-id.uaepass.ae/trustedx-resources/openid/v1/users/me
    private static String UAE_PASS_STAGE_URL = "https://qa-id.uaepass.ae";
//    Authorization Endpoint :  https://id.uaepass.ae/trustedx-authserver/oauth/main-as
//    Token Endpoint :          https://id.uaepass.ae/trustedx-authserver/oauth/main-as/token
//    User Info Endpoint :      https://id.uaepass.ae/trustedx-resources/openid/v1/users/me
    private static String UAE_PASS_PROD_URL = "https://id.uaepass.ae";

    public static Api providesApi() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Strategy strategy = new AnnotationStrategy();

        Serializer serializer = new Persister(new Format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>"));


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                .baseUrl(SITE_BASE_URL)
                .build();

        return retrofit.create(Api.class);

    }

    public static Api newsApi() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Strategy strategy = new AnnotationStrategy();

        Serializer serializer = new Persister(
                new Format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>"));

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(NEWS_BASE_URL)
                .build();

        return retrofit.create(Api.class);

    }

    public static Api basicApi() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .authenticator(new NTLMAuthenticator("sdg_hosam", "7atsheb$out@8080"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .baseUrl(BASIC_BASE_URL)
                .client(client)
                .build();

        return retrofit.create(Api.class);

    }


    public static Api uaePassApi() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
//                .authenticator(new NTLMAuthenticator("sdg_hosam", "7atsheb$out@8080"))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .baseUrl(UAE_PASS_STAGE_URL)
                .client(client)
                .build();
        return retrofit.create(Api.class);

    }

}
