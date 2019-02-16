package com.ajman.ded.ae.screens.ded_eye;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
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
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            searchView.clearFocus();
            startActivity(new Intent(DedEyeActivity.this, NotificationSearchActivity.class));
        });
        newNotification.setOnClickListener(v -> startActivity(new Intent(DedEyeActivity.this, NewNotificationActivity.class)));
        ImageView searchViewIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        SearchView.SearchAutoComplete searchText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchText.setCursorVisible(false);
        ViewGroup linearLayoutSearchView = (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);
        linearLayoutSearchView.addView(searchViewIcon);
        callNo.setOnClickListener(v -> dialPhoneNumber("80070"));
        adapter = new HomeEyeAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        api = ApiBuilder.basicApi();
        call = api.status_notification(UserData.getUserObject(DedEyeActivity.this).getUserId(), String.valueOf(3));
    }

    @Override
    public void triggerByInternet() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        call();
    }

    private void call() {
        List<ItemHome> data = new ArrayList<>();
        closed = 0;
        ongoing = 0;
        all = 0;
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
                    data.add(new ItemHome(closed, getString(R.string.HOME_closed)));
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
    protected void onDestroy() {
        if (call != null)
            call.cancel();
        super.onDestroy();
    }
}
