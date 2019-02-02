package com.ajman.ded.ae.screens.dashboard;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.WebViewActivity;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.data.model.request.OnlineUserAllLicense_CountMob.RequestBody_OnlineUserAllLicense_CountMob;
import com.ajman.ded.ae.data.model.request.OnlineUserAllLicense_CountMob.RequestData_OnlineUserAllLicense_CountMob;
import com.ajman.ded.ae.data.model.request.OnlineUserAllLicense_CountMob.RequestEnvelope_OnlineUserAllLicense_CountMob;
import com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_CountMob.Item_OnlineUserAllLicense_CountMob;
import com.ajman.ded.ae.data.model.response.OnlineUserAllLicense_CountMob.ResponseEnvelope_OnlineUserAllLicense_CountMob;
import com.ajman.ded.ae.libs.AnimatedFragment;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.UserModel;
import com.ajman.ded.ae.screens.barcode.BarcodeActivity;
import com.ajman.ded.ae.screens.home.HomeActivity;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;
import static com.ajman.ded.ae.utility.Constants.URL_INTENT_KEY;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_AR;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_EN;

/**
 * A simple {@link Fragment} subclass.
 */
public class LicensePagerFragment extends AnimatedFragment {

    View[] views;
    @BindView(R.id.frameLayout)
    FrameLayout counterFrame;

    @BindView(R.id.dash_panel)
    ConstraintLayout dashPanel;

    //    @BindView(R.id.menu_panel)
//    LinearLayout menuPanel;
//
//    @BindView(R.id.btn_home)
//    LinearLayout mHomeBtn;
//
    @BindView(R.id.btn_next)
    ImageView mNextBtn;

    @BindView(R.id.btn_prev)
    ImageView mPrevBtn;

    @BindView(R.id.active)
    TextView active;

    @BindView(R.id.expiry)
    TextView expiry;

    @BindView(R.id.cancel)
    TextView cancel;

    @BindView(R.id.banned)
    TextView banned;

    @BindView(R.id.total)
    TextView total;

    int active_value;
    int expiry_value;
    int cancel_value;
    int banned_value;
    int total_value;

    private Api api;

    public LicensePagerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_license_pager, container, false);
        ButterKnife.bind(this, view);
        views = new View[]{counterFrame, dashPanel};
        counterFrame.setOnClickListener(view1 -> getActivity().startActivity(new Intent(getActivity(), BarcodeActivity.class)));

        mNextBtn.setOnClickListener(view1 -> ((DashInterface) getParentFragment()).moveNext());
        mPrevBtn.setOnClickListener(view1 -> ((DashInterface) getParentFragment()).movePrev());
        UserModel userModel = UserData.getUserObject(getActivity());

        view.findViewById(R.id.button2).setOnClickListener(v -> startActivity(new Intent(getActivity(), HomeActivity.class)));


        RequestEnvelope_OnlineUserAllLicense_CountMob envelope = new RequestEnvelope_OnlineUserAllLicense_CountMob();

        RequestBody_OnlineUserAllLicense_CountMob body = new RequestBody_OnlineUserAllLicense_CountMob();

        RequestData_OnlineUserAllLicense_CountMob data = new RequestData_OnlineUserAllLicense_CountMob();

        if (null != userModel) {
            data.setEmail(userModel.getUserName());
        }
        body.setRequestData(data);

        envelope.setBody(body);

        api = ApiBuilder.providesApi();

        Call<ResponseEnvelope_OnlineUserAllLicense_CountMob> call = api.requestCountMobCall("OnlineUserAllLicense_CountMob_json", envelope);

        call.enqueue(new retrofit2.Callback<ResponseEnvelope_OnlineUserAllLicense_CountMob>() {

            @Override
            public void onResponse(@NonNull Call<ResponseEnvelope_OnlineUserAllLicense_CountMob> call, @NonNull final Response<ResponseEnvelope_OnlineUserAllLicense_CountMob> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Item_OnlineUserAllLicense_CountMob[] list = gson.fromJson(response.body().getBody().getData().getElements(), Item_OnlineUserAllLicense_CountMob[].class);
                    List<Item_OnlineUserAllLicense_CountMob> models = new ArrayList<>();
                    models.addAll(Arrays.asList(list));
                    active_value = models.get(0).getActive();
                    active.setText(String.valueOf(active_value));
                    expiry_value = models.get(0).getExpiry();
                    expiry.setText(String.valueOf(expiry_value));
                    cancel_value = models.get(0).getAbrogated_Cancelled_Revoked();
                    cancel.setText(String.valueOf(cancel_value));
                    banned_value = models.get(0).getAdministrative_Cancellation_();
                    banned.setText(String.valueOf(banned_value));
                    total.setText(String.format(Locale.US, "%d", models.get(0).getTotal()));

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseEnvelope_OnlineUserAllLicense_CountMob> call, @NonNull Throwable t) {
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

    private void webview(String endPoint) {
        Intent in = new Intent(getActivity(), WebViewActivity.class);
        if (Objects.equals(LocaleManager.getLanguage(getActivity()), LANGUAGE_ARABIC))
            in.putExtra(URL_INTENT_KEY, DOMAIN_AR + endPoint);
        else
            in.putExtra(URL_INTENT_KEY, DOMAIN_EN + endPoint);
        getActivity().startActivity(in);
    }

}
