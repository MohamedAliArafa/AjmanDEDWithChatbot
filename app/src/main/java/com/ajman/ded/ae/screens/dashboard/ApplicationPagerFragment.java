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
import com.ajman.ded.ae.data.model.request.ApplicationListByEmail.RequestBody_ApplicationListByEmail;
import com.ajman.ded.ae.data.model.request.ApplicationListByEmail.RequestData_ApplicationListByEmail;
import com.ajman.ded.ae.data.model.request.ApplicationListByEmail.RequestEnvelope_ApplicationListByEmail;
import com.ajman.ded.ae.data.model.response.ApplicationListByEmail.Item_ApplicationListByEmail;
import com.ajman.ded.ae.data.model.response.ApplicationListByEmail.ResponseEnvelope_ApplicationListByEmail;
import com.ajman.ded.ae.libs.AnimatedFragment;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.UserModel;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;
import static com.ajman.ded.ae.utility.Constants.URL_INTENT_KEY;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_AR;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_EN;

public class ApplicationPagerFragment extends AnimatedFragment {

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

    @BindView(R.id.btn_next)
    ImageView mNextBtn;

    @BindView(R.id.completed_value)
    TextView completed;

    @BindView(R.id.pending_value)
    TextView pending;

    @BindView(R.id.rejected_value)
    TextView rejected;

    @BindView(R.id.process_value)
    TextView process;

    @BindView(R.id.total)
    TextView total;

    int completed_value;
    int pending_value;
    int rejected_value;
    int process_value;
    int online_payment;
    int customer_value;
    int total_value;

    private Api api;

    public ApplicationPagerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_application_pager, container, false);
        ButterKnife.bind(this, view);
        views = new View[]{counterFrame, dashPanel};
//        view.findViewById(R.id.tasks).setOnClickListener(view12 -> webview(APP_APPLICATIONS));

//        mHomeBtn.setOnClickListener(view1 -> getActivity().startActivity(new Intent(getActivity(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)));
        UserModel userModel = UserData.getUserObject(getActivity());
        view.findViewById(R.id.button2).setOnClickListener(v -> startActivity(new Intent(getActivity(), HomeActivity.class)));

        RequestEnvelope_ApplicationListByEmail envelope = new RequestEnvelope_ApplicationListByEmail();

        RequestBody_ApplicationListByEmail body = new RequestBody_ApplicationListByEmail();

        RequestData_ApplicationListByEmail data = new RequestData_ApplicationListByEmail();

        if (null != userModel) {
            data.setEmail(userModel.getUserName());
        }

        body.setRequestData(data);

        envelope.setBody(body);

        api = ApiBuilder.providesApi();

        Call<ResponseEnvelope_ApplicationListByEmail> call = api.requestListByEmailCall("ApplicationListByEmail_json", envelope);

        call.enqueue(new retrofit2.Callback<ResponseEnvelope_ApplicationListByEmail>() {

            @Override
            public void onResponse(@NonNull Call<ResponseEnvelope_ApplicationListByEmail> call, @NonNull final Response<ResponseEnvelope_ApplicationListByEmail> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Item_ApplicationListByEmail[] list = gson.fromJson(response.body().getBody().getData().getElements(), Item_ApplicationListByEmail[].class);
                    List<Item_ApplicationListByEmail> models = new ArrayList<>();
                    models.addAll(Arrays.asList(list));
                    for (Item_ApplicationListByEmail item : models) {
                        if (Objects.equals(item.getOnlineStatusEn(), "Completed")) {
                            completed.setText(String.format(Locale.US, "%d", item.getCOUNTA()));
                            completed_value = item.getCOUNTA();
                        } else if (Objects.equals(item.getOnlineStatusEn(), "InProgress")) {
                            pending.setText(String.format(Locale.US, "%d", item.getCOUNTA()));
                            pending_value = item.getCOUNTA();
                        } else if (Objects.equals(item.getOnlineStatusEn(), "Rejected")) {
                            rejected.setText(String.format(Locale.US, "%d", item.getCOUNTA()));
                            rejected_value = item.getCOUNTA();
                        } else if (Objects.equals(item.getOnlineStatusEn(), "Under Payment Process")) {
                            process.setText(String.format(Locale.US, "%d", item.getCOUNTA()));
                            process_value = item.getCOUNTA();
                        } else if (Objects.equals(item.getOnlineStatusEn(), "Online Payment")) {
                            online_payment = item.getCOUNTA();
                        } else if (Objects.equals(item.getOnlineStatusEn(), "Customer Service")) {
                            customer_value = item.getCOUNTA();
                        }
                    }
                    total_value = completed_value + pending_value + rejected_value + process_value + online_payment + customer_value;
                    total.setText(String.format(Locale.US, "%d", total_value));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseEnvelope_ApplicationListByEmail> call, @NonNull Throwable t) {
                Log.d("DATA ERROR:", String.valueOf(t));
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DashboardFragment fragment = ((DashboardFragment) getParentFragment());
        mNextBtn.setOnClickListener(view1 -> {
            fragment.moveNext();
        });
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
