package com.ajman.ded.ae.screens.dashboard;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.data.model.request.OnlineUserAllPermits.RequestBody_OnlineUserAllPermits;
import com.ajman.ded.ae.data.model.request.OnlineUserAllPermits.RequestData_OnlineUserAllPermits;
import com.ajman.ded.ae.data.model.request.OnlineUserAllPermits.RequestEnvelope_OnlineUserAllPermits;
import com.ajman.ded.ae.data.model.response.OnlineUserAllPermits.Item_OnlineUserAllPermits;
import com.ajman.ded.ae.data.model.response.OnlineUserAllPermits.ResponseEnvelope_OnlineUserAllPermits;
import com.ajman.ded.ae.libs.AnimatedFragment;
import com.ajman.ded.ae.models.UserModel;
import com.ajman.ded.ae.screens.home.HomeActivity;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PermitPagerFragment extends AnimatedFragment {

    View[] views;
    @BindView(R.id.frameLayout)
    FrameLayout counterFrame;

    @BindView(R.id.dash_panel)
    ConstraintLayout dashPanel;

//    @BindView(R.id.menu_panel)
//    LinearLayout menuPanel;

//    @BindView(R.id.btn_home)
//    LinearLayout mHomeBtn;

    @BindView(R.id.btn_prev)
    ImageView mPrevBtn;

    @BindView(R.id.valid)
    TextView valid;

    @BindView(R.id.expiry)
    TextView expiry;

//    @BindView(R.id.cancel)
//    TextView cancel;
//
//    @BindView(R.id.banned)
//    TextView banned;

    @BindView(R.id.total)
    TextView total;

    int valid_value;
    int expiry_value;
    int total_value;

    private Api api;

    public PermitPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (views != null)
            startAnimation();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_permit_pager, container, false);
        ButterKnife.bind(this, view);

        views = new View[]{counterFrame, dashPanel};
//        mHomeBtn.setOnClickListener(view1 -> {
//            getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));
//            getActivity().finish();
//        });
        view.findViewById(R.id.button2).setOnClickListener(v -> startActivity(new Intent(getActivity(), HomeActivity.class)));

        mPrevBtn.setOnClickListener(view1 -> ((DashInterface) getParentFragment()).movePrev());
        UserModel userModel = UserData.getUserObject(getActivity());

        RequestEnvelope_OnlineUserAllPermits envelope = new RequestEnvelope_OnlineUserAllPermits();

        RequestBody_OnlineUserAllPermits body = new RequestBody_OnlineUserAllPermits();

        RequestData_OnlineUserAllPermits data = new RequestData_OnlineUserAllPermits();

        if (null != userModel) {
            data.setEmail(userModel.getUserName());
        }
        body.setRequestData(data);

        envelope.setBody(body);

        api = ApiBuilder.providesApi();

        Call<ResponseEnvelope_OnlineUserAllPermits> call = api.requestAllPermitsCall("OnlineUserAllPermits_json", envelope);

        call.enqueue(new retrofit2.Callback<ResponseEnvelope_OnlineUserAllPermits>() {

            @Override
            public void onResponse(@NonNull Call<ResponseEnvelope_OnlineUserAllPermits> call, @NonNull final Response<ResponseEnvelope_OnlineUserAllPermits> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Item_OnlineUserAllPermits[] list = gson.fromJson(response.body().getBody().getData().getElements(), Item_OnlineUserAllPermits[].class);
                    List<Item_OnlineUserAllPermits> models = new ArrayList<>();
                    models.addAll(Arrays.asList(list));
                    valid_value = models.get(0).getValidPermits();
                    expiry_value = models.get(0).getExpiryPermits();
                    valid.setText(String.format(Locale.US, "%d", valid_value));
                    expiry.setText(String.format(Locale.US, "%d", expiry_value));
                    total_value = valid_value + expiry_value;
                    total.setText(String.format(Locale.US, "%d", total_value));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseEnvelope_OnlineUserAllPermits> call, @NonNull Throwable t) {
                Log.d("DATA ERROR:", String.valueOf(t));
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startAnimation();
    }

    @Override
    public void startAnimation() {
        new AnimatedFragment.animate(views, R.anim.expand_in).execute();
    }

}
