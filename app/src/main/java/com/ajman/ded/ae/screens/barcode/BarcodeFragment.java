package com.ajman.ded.ae.screens.barcode;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.adapters.BarcodeAdapter;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.data.model.request.OnlineUserAllLicense_Mob.RequestBody_OnlineUserAllLicense_Mob;
import com.ajman.ded.ae.data.model.request.OnlineUserAllLicense_Mob.RequestData_OnlineUserAllLicense_Mob;
import com.ajman.ded.ae.data.model.request.OnlineUserAllLicense_Mob.RequestEnvelope_OnlineUserAllLicense_Mob;
import com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_Mob.ItemOnlineUserAllLicenseMob;
import com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_Mob.ResponseEnvelope_OnlineUserAllLicense_Mob;
import com.ajman.ded.ae.models.UserModel;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class BarcodeFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecycler;
    private BarcodeAdapter mAdapter;
    private Api api;

    public BarcodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barcode, container, false);
        ButterKnife.bind(this, view);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new BarcodeAdapter(getActivity(), R.layout.list_item_barcode);
        mRecycler.setAdapter(mAdapter);
        UserModel userModel = UserData.getUserObject(getActivity());

        RequestEnvelope_OnlineUserAllLicense_Mob envelope = new RequestEnvelope_OnlineUserAllLicense_Mob();

        RequestBody_OnlineUserAllLicense_Mob body = new RequestBody_OnlineUserAllLicense_Mob();

        RequestData_OnlineUserAllLicense_Mob data = new RequestData_OnlineUserAllLicense_Mob();

        if (null != userModel) {
            data.setEmail(userModel.getUserName());
            data.setFlagLicenseStatus("");
        }
        body.setRequestData(data);

        envelope.setBody(body);

        api = ApiBuilder.providesApi();

        Call<ResponseEnvelope_OnlineUserAllLicense_Mob> call = api.requestLicenseMobCall("OnlineUserAllLicense_Mob_json", envelope);

        call.enqueue(new retrofit2.Callback<ResponseEnvelope_OnlineUserAllLicense_Mob>() {

            @Override
            public void onResponse(@NonNull Call<ResponseEnvelope_OnlineUserAllLicense_Mob> call, @NonNull final Response<ResponseEnvelope_OnlineUserAllLicense_Mob> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    ItemOnlineUserAllLicenseMob[] list = gson.fromJson(response.body().getBody().getData().getElements(), ItemOnlineUserAllLicenseMob[].class);
                    List<ItemOnlineUserAllLicenseMob> models = new ArrayList<>();
                    models.addAll(Arrays.asList(list));
                    mAdapter.updateData(models);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseEnvelope_OnlineUserAllLicense_Mob> call, @NonNull Throwable t) {
                Log.d("DATA ERROR:", String.valueOf(t));
            }
        });

        return view;
    }

}
