package com.ajman.ded.ae;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.notification.NotificationStatusResponse;
import com.ajman.ded.ae.utility.SharedTool.UserData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsActivity extends AppCompatActivity {
    @BindView(R.id.list)
    RecyclerView recyclerView;
    private NotificationAdapter mAdapter;
    private Api api;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);

        int status = getIntent().getIntExtra("status", -1);
        switch (status) {
            case 0:
                mAdapter = new NotificationAdapter(this, getString(R.string.ongoing_notification));
                break;
            case 1:
                mAdapter = new NotificationAdapter(this, getString(R.string.finished_notification));
                break;
            case 3:
                mAdapter = new NotificationAdapter(this, getString(R.string.inquiry_notification));
                break;
            default:
                mAdapter = new NotificationAdapter(this, getString(R.string.inquiry_notification));
                break;

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        api = ApiBuilder.basicApi();
        Call<NotificationStatusResponse> call = api.status_notification(UserData.getUserObject(NotificationsActivity.this).getUserId(), String.valueOf(status));

        call.clone().enqueue(new Callback<NotificationStatusResponse>() {
            @Override
            public void onResponse(Call<NotificationStatusResponse> call, Response<NotificationStatusResponse> response) {
                if (response.isSuccessful()) {
                    mAdapter.setData(response.body().getResponseContent());
                }
            }

            @Override
            public void onFailure(Call<NotificationStatusResponse> call, Throwable t) {
                Log.d("AAa", t.getMessage());
            }
        });
    }
}
