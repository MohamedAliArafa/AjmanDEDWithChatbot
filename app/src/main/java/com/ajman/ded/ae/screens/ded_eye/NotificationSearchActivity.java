package com.ajman.ded.ae.screens.ded_eye;

import android.os.Bundle;
import android.util.Log;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.models.notification.NotificationStatusResponse;
import com.ajman.ded.ae.screens.ded_eye.adapter.NotificationAdapter;
import com.ajman.ded.ae.utility.SharedTool.UserData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSearchActivity extends AppCompatActivity {

    private Api api;

    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.search)
    androidx.appcompat.widget.SearchView searchView;
    private NotificationAdapter adapter;
    private Call<NotificationStatusResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notification);
        ButterKnife.bind(this);
        adapter = new NotificationAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter.getItemCount() > 0)
                    adapter.getFilter().filter(newText);
                return false;
            }
        });
        api = ApiBuilder.basicApi();
        call = api.status_notification(UserData.getUserObject(NotificationSearchActivity.this).getUserId(), String.valueOf(3));

        call.clone().enqueue(new Callback<NotificationStatusResponse>() {
            @Override
            public void onResponse(Call<NotificationStatusResponse> call, Response<NotificationStatusResponse> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body().getResponseContent());
                }
            }

            @Override
            public void onFailure(Call<NotificationStatusResponse> call, Throwable t) {
                Log.d("AAa", t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (call != null)
            call.cancel();
        super.onDestroy();
    }
}
