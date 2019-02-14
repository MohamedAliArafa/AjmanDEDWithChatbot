package com.ajman.ded.ae.screens.ded_eye;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.notification.NotificationStatusResponse;
import com.ajman.ded.ae.screens.ded_eye.adapter.NotificationSearchAdapter;
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    private Api api;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.search)
    androidx.appcompat.widget.SearchView searchView;
    private NotificationSearchAdapter adapter;
    private Call<NotificationStatusResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notification);
        ButterKnife.bind(this);
        adapter = new NotificationSearchAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        SearchView.SearchAutoComplete searchText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        ImageView searchViewIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        ViewGroup linearLayoutSearchView = (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);
        linearLayoutSearchView.addView(searchViewIcon);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0)
                    searchViewIcon.setVisibility(View.GONE);
                else
                    searchViewIcon.setVisibility(View.VISIBLE);
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
