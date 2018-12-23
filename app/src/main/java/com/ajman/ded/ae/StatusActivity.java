package com.ajman.ded.ae;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.ajman.ded.ae.adapters.StatusAdapter;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.data.model.request.GetRequestStatus.RequestBody_GetRequestStatus;
import com.ajman.ded.ae.data.model.request.GetRequestStatus.RequestData_GetRequestStatus;
import com.ajman.ded.ae.data.model.request.GetRequestStatus.RequestEnvelope_GetRequestStatus;
import com.ajman.ded.ae.data.model.response.GetRequestStatus.Item_GetRequestStatus;
import com.ajman.ded.ae.data.model.response.GetRequestStatus.ResponseEnvelope_GetRequestStatus;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class StatusActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private StatusAdapter adapter;
    private Api api;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String appNo = getIntent().getStringExtra("appNo");
        recyclerView = findViewById(R.id.status_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestEnvelope_GetRequestStatus envelope = new RequestEnvelope_GetRequestStatus();

        RequestBody_GetRequestStatus body = new RequestBody_GetRequestStatus();

        RequestData_GetRequestStatus data = new RequestData_GetRequestStatus();

        data.setApplicationNo(appNo);

        body.setRequestData(data);

        envelope.setBody(body);

        api = ApiBuilder.providesApi();

        Call<ResponseEnvelope_GetRequestStatus> call = api.requestStatusCall("GetRequestStatus_json", envelope);

        call.enqueue(new retrofit2.Callback<ResponseEnvelope_GetRequestStatus>() {

            @Override
            public void onResponse(@NonNull Call<ResponseEnvelope_GetRequestStatus> call, @NonNull final Response<ResponseEnvelope_GetRequestStatus> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Item_GetRequestStatus[] list = gson.fromJson(response.body().getBody().getData().getElements(), Item_GetRequestStatus[].class);
                    List<Item_GetRequestStatus> models = new ArrayList<>();
                    models.addAll(Arrays.asList(list));
                    adapter = new StatusAdapter(StatusActivity.this, models);
                    recyclerView.setAdapter(adapter);

                } else {
                    //TODO Handel Exception !!
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseEnvelope_GetRequestStatus> call, @NonNull Throwable t) {
                Log.d("DATA ERROR:", String.valueOf(t));
            }
        });
    }
}
