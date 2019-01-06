package com.ajman.ded.ae;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain)
            throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}