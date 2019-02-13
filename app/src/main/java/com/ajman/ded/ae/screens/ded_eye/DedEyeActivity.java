package com.ajman.ded.ae.screens.ded_eye;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.models.notification.NotificationStatusResponse;
import com.ajman.ded.ae.models.notification.ResponseContent;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.ajman.ded.ae.screens.ded_eye.adapter.HomeEyeAdapter;
import com.ajman.ded.ae.screens.ded_eye.model.ItemHome;
import com.ajman.ded.ae.utility.SharedTool.UserData;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DedEyeActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.search)
    SearchView searchView;
    @BindView(R.id.call)
    ImageView callNo;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.new_notification)
    Button newNotification;
    private HomeEyeAdapter adapter;
    private Api api;
    private int ongoing, closed, all;
    private Call<NotificationStatusResponse> call;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_ded_eye;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        toolbarTitle.setText(getString(R.string.ajmaneye));
        name.setText(UserData.getUserObject(this).getNameAr());
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(new Intent(DedEyeActivity.this, NotificationSearchActivity.class));
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                startActivity(new Intent(DedEyeActivity.this, NotificationSearchActivity.class));
                searchView.clearFocus();
                return false;
            }
        });
        searchView.setOnSearchClickListener(v -> startActivity(new Intent(DedEyeActivity.this, NotificationSearchActivity.class)));
        newNotification.setOnClickListener(v -> startActivity(new Intent(DedEyeActivity.this, NewNotificationActivity.class)));
        callNo.setOnClickListener(v -> dialPhoneNumber("80070"));
        adapter = new HomeEyeAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        List<ItemHome> data = new ArrayList<>();

        api = ApiBuilder.basicApi();
        call = api.status_notification(UserData.getUserObject(DedEyeActivity.this).getUserId(), String.valueOf(3));
        call.clone().enqueue(new Callback<NotificationStatusResponse>() {
            @Override
            public void onResponse(Call<NotificationStatusResponse> call, Response<NotificationStatusResponse> response) {
                if (response.isSuccessful()) {
                    for (ResponseContent item : response.body().getResponseContent()) {
                        if (item.getIsClosed() != null && item.getIsClosed().equals("true"))
                            closed += 1;
                        else
                            ongoing += 1;

                        all += 1;
                    }
                    data.add(new ItemHome(closed, getString(R.string.closed)));
                    data.add(new ItemHome(ongoing, getString(R.string.ongoing)));
                    data.add(new ItemHome(all, getString(R.string.all_notification)));
                    adapter.setData(data);
                }
            }

            @Override
            public void onFailure(Call<NotificationStatusResponse> call, Throwable t) {
            }
        });

    }

    @Override
    public void triggerByInternet() {

    }

    @Override
    protected void onDestroy() {
        if (call != null)
            call.cancel();
        super.onDestroy();
    }
}
