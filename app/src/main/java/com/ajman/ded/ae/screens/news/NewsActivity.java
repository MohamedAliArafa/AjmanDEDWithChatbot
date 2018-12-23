package com.ajman.ded.ae.screens.news;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.models.News;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class NewsActivity extends BaseActivity {

    private SweetAlertDialog pDialog;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.news_rv);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setCancelable(true);
        Api api = ApiBuilder.newsApi();
        Call<String> call = api.news_caller();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        api = ApiBuilder.providesApi();

//        Call<ResponseEnvelope_GetAccount> call = api.doGetAccount("GetAccount", News);
        call.enqueue(new retrofit2.Callback<String>() {

            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull final Response<String> response) {
                if (response.isSuccessful()) {
                    String data = String.valueOf(response.body());
                    data = data.replace("<string xmlns=\"http://AjmanDED-MobData.org/\">", "");

                    List<News> result = new Gson().fromJson(data, new TypeToken<List<News>>() {
                    }.getType());
                    adapter = new NewsAdapter(NewsActivity.this, result);
                    recyclerView.setAdapter(adapter);

                } else {
                    pDialog.setTitleText(getString(R.string.went_wrong))
                            .setConfirmText("OK")
                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d("DATA ERROR:", String.valueOf(t));
                pDialog.setTitleText("something went wrong!")
                        .setConfirmText("OK")
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            }
        });

    }
}
