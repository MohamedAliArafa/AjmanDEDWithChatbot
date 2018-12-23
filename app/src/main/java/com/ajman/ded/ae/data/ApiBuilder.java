package com.ajman.ded.ae.data;


import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.stream.Format;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiBuilder {

    public static String NEWS_BASE_URL = "http://ded.sdg.ae/";
    public static String SITE_BASE_URL = "http://site4.ajmanded.ae";
    public static String NEWS_IMAGE_BASE_URL = "http://ded.sdg.ae/Ufiles/News/";

    public static Api providesApi() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Strategy strategy = new AnnotationStrategy();

        Serializer serializer = new Persister(
                new Format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>"));

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create(serializer))
                .baseUrl(SITE_BASE_URL)
                .client(okHttpClient)
                .build();

        return retrofit.create(Api.class);

    }

    public static Api newsApi() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Strategy strategy = new AnnotationStrategy();

        Serializer serializer = new Persister(
                new Format("<?xml version=\"1.0\" encoding=\"utf-8\" ?>"));

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(NEWS_BASE_URL)
                .client(okHttpClient)
                .build();

        return retrofit.create(Api.class);

    }

}
