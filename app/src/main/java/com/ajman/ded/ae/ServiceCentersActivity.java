package com.ajman.ded.ae;

import android.os.Bundle;

import com.ajman.ded.ae.libs.ChatSplash.ResultModel;
import com.ajman.ded.ae.models.service.ServiceList;
import com.ajman.ded.ae.screens.base.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceCentersActivity extends BaseActivity {

    @BindView(R.id.list)
    RecyclerView recyclerView;
    private List<ServiceList> data;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_service_centers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//fetch data and on ExpandableRecyclerAdapter
        InputStream raw = getResources().openRawResource(R.raw.services_center);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        data = gson.fromJson(rd,  new TypeToken<ArrayList<ServiceList>>(){}.getType());
        recyclerView.setAdapter(new ExpandableRecyclerAdapter(data));
    }
}
