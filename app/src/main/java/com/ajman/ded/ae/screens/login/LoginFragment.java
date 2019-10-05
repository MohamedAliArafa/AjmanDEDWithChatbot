package com.ajman.ded.ae.screens.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajman.ded.ae.MyApplication;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.WebViewActivity;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestBody_ConfirmCode;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestData_ConfirmCode;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestEnvelope_ConfirmCode;
import com.ajman.ded.ae.data.model.request.GetAccount.RequestBody_GetAccount;
import com.ajman.ded.ae.data.model.request.GetAccount.RequestData_GetAccount;
import com.ajman.ded.ae.data.model.request.GetAccount.RequestEnvelope_GetAccount;
import com.ajman.ded.ae.data.model.request.UserId.RequestBody_UserId;
import com.ajman.ded.ae.data.model.request.UserId.RequestData_UserId;
import com.ajman.ded.ae.data.model.request.UserId.RequestEnvelope_UserId;
import com.ajman.ded.ae.data.model.response.ConfirmCode.ResponseEnvelope_ConfirmCode;
import com.ajman.ded.ae.data.model.response.GetAccount.ResponseEnvelope_GetAccount;
import com.ajman.ded.ae.data.model.response.UserId.ResponseEnvelope_UserId;
import com.ajman.ded.ae.models.UserIdResponse;
import com.ajman.ded.ae.models.UserModel;
import com.ajman.ded.ae.models.uaepass.AuthTokenModel;
import com.ajman.ded.ae.models.uaepass.ProfileModel;
import com.ajman.ded.ae.screens.IntroActivity;
import com.ajman.ded.ae.screens.ded_eye.DedEyeActivity;
import com.ajman.ded.ae.screens.registeration.RegisterActivity;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.goodiebag.pinview.Pinview;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.ajman.ded.ae.utility.Constants.CODE_RESULT_KEY;
import static com.ajman.ded.ae.utility.Constants.REDIRECT_URL_INTENT_KEY;
import static com.ajman.ded.ae.utility.Constants.URL_INTENT_KEY;
import static com.ajman.ded.ae.utility.Helper.convertDpToPixel;
import static com.ajman.ded.ae.utility.Helper.isValidEmail;

public class LoginFragment extends Fragment {

    private Api api;
    private Api apiUaePass;
    private SweetAlertDialog pDialog;
    private EditText username;
    private EditText password;
    private Pinview pinview;
    private Integer webViewRequestCode = 1152;

    private static final String UAE_PASS_CLIENT_ID = "ajm_ded_mob_stage";
    private static final String UAE_PASS_CLIENT_SECRET = "QYknfXVshPZmPlsq";
    private static final String REDIRECT_URL = "ajmanded://uaepass.sdg.ae/success";
    private static final String DOCUMENT_SIGNING_SCOPE = "urn:safelayer:eidas:sign:process:document";
    private static final String RESPONSE_TYPE = "code";
    private static final String SCOPE = "urn:uae:digitalid:profile";
    private static final String ACR_VALUES_MOBILE = "urn:digitalid:authentication:flow:mobileondevice";
    private static final String ACR_VALUES_WEB = "urn:safelayer:tws:policies:authentication:level:low";
    private static final String UAE_PASS_PACKAGE_ID = "ae.uaepass.mainapp";

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Intent uaePass = new Intent(getContext(), WebViewActivity.class);
        uaePass.putExtra(REDIRECT_URL_INTENT_KEY, REDIRECT_URL);
        uaePass.putExtra(URL_INTENT_KEY, "https://qa-id.uaepass.ae/trustedx-authserver/oauth/main-as?" +
                "redirect_uri=" + REDIRECT_URL + "&" +
                "client_id=" + UAE_PASS_CLIENT_ID + "&" +
                "response_type=code&" +
                "state=ShNP22hyl1jUU2RGjTRkpg==&" +
                "scope=urn:uae:digitalid:profile&" +
                "acr_values =urn:safelayer:tws:policies:authentication:level:low&" +
                "ui_locales=en");
        Objects.requireNonNull(getActivity()).startActivityForResult(uaePass, webViewRequestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == webViewRequestCode && resultCode == RESULT_OK) {
            String code = data.getStringExtra(CODE_RESULT_KEY);
            apiUaePass = ApiBuilder.uaeAuthServerApi();
            Call<AuthTokenModel> callAuth = apiUaePass.getToken("", REDIRECT_URL, code);
            callAuth.enqueue(new retrofit2.Callback<AuthTokenModel>() {
                @Override
                public void onResponse(@NonNull Call<AuthTokenModel> call, @NonNull Response<AuthTokenModel> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        Log.d("Token:", response.body().getAccessToken());
                        Api apiUaeResources = ApiBuilder.uaeResourcesApi(response.body().getAccessToken());
                        apiUaeResources.getProfile().enqueue(new Callback<ProfileModel>() {
                            @Override
                            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                                assert response.body() != null;
                                Toast.makeText(getContext(), "Welcome, " + response.body().getFullnameEN(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ProfileModel> call, Throwable t) {
                                pDialog.setTitleText(getString(R.string.went_wrong))
                                        .setConfirmText(getString(R.string.ok))
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            }
                        });
                    } else {
                        pDialog.setTitleText(getString(R.string.went_wrong))
                                .setConfirmText(getString(R.string.ok))
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AuthTokenModel> call, @NonNull Throwable t) {
                    Log.d("PIN ERROR:", String.valueOf(t));
                    pDialog.setTitleText(getString(R.string.went_wrong))
                            .setConfirmText(getString(R.string.ok))
                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        Button login = rootView.findViewById(R.id.login_btn);
        username = rootView.findViewById(R.id.username_input);
        password = rootView.findViewById(R.id.password_input);
        TextView regisrer = rootView.findViewById(R.id.signup);
        regisrer.setOnClickListener(view -> startActivity(new Intent(getActivity(), RegisterActivity.class)));

        login.setOnClickListener(view -> {
            if (!isValidEmail(username.getText().toString())) {
                username.setError(getString(R.string.email_validation));
                username.requestFocus();
            } else if (password.getText().toString().isEmpty()) {
                password.setError(getString(R.string.password_validation));
                password.requestFocus();
            } else {
                pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE)
                        .setTitleText("Loading");
                pDialog.show();
                pDialog.setCancelable(true);
                RequestEnvelope_GetAccount envelope = new RequestEnvelope_GetAccount();

                RequestBody_GetAccount body = new RequestBody_GetAccount();

                RequestData_GetAccount data = new RequestData_GetAccount();

                data.setEmail(username.getText().toString());
                data.setPassword(password.getText().toString());

                body.setRequestData(data);

                envelope.setBody(body);

                api = ApiBuilder.providesApi();

                Call<ResponseEnvelope_GetAccount> call = api.doGetAccount("GetAccount", envelope);
                call.enqueue(new retrofit2.Callback<ResponseEnvelope_GetAccount>() {

                    @Override
                    public void onResponse(@NonNull Call<ResponseEnvelope_GetAccount> call, @NonNull final Response<ResponseEnvelope_GetAccount> response) {
                        if (response.isSuccessful()) {
                            String result = response.body().getBody().getData().getAccountResult().replace("\"", "");
                            switch (result) {
                                case "1":
                                    getUserId();
                                    break;
                                case "2":
                                    pinview = new Pinview(getActivity());
                                    pinview.setPinHeight((int) convertDpToPixel(25));
                                    pinview.setPinWidth((int) convertDpToPixel(25));
                                    pinview.setPinLength(5);
                                    pinview.requestFocus();
                                    pinview.setInputType(Pinview.InputType.NUMBER);
                                    FrameLayout frameLayout = new FrameLayout(getActivity());
                                    frameLayout.addView(pinview);
                                    pDialog.setTitleText(getString(R.string.confirm_account))
                                            .setConfirmText(getString(R.string.ok))
                                            .setCustomView(frameLayout)
                                            .setConfirmClickListener(sweetAlertDialog -> confirmCode(pinview.getValue()))
                                            .changeAlertType(SweetAlertDialog.NORMAL_TYPE);
                                    break;
                                case "3":
                                    pDialog.setTitleText(getString(R.string.wrong_auth))
                                            .setConfirmText(getString(R.string.try_again))
                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    break;
                                default:
                                    pDialog.dismissWithAnimation();
                                    Log.d("ERROR", result);

                            }
                        } else {
                            pDialog.setTitleText(getString(R.string.went_wrong))
                                    .setConfirmText(getString(R.string.ok))
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseEnvelope_GetAccount> call, @NonNull Throwable t) {
                        Log.d("DATA ERROR:", String.valueOf(t));
                        pDialog.setTitleText(getString(R.string.went_wrong))
                                .setConfirmText(getString(R.string.ok))
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                });
            }
        });
        return rootView;
    }

    private void confirmCode(String pinٍ) {

        RequestEnvelope_ConfirmCode envelope = new RequestEnvelope_ConfirmCode();

        RequestBody_ConfirmCode body = new RequestBody_ConfirmCode();

        RequestData_ConfirmCode data = new RequestData_ConfirmCode();


        data.setEmail(username.getText().toString());
        data.setMobileConfirmationCode(pinٍ);
        body.setRequestData(data);

        envelope.setBody(body);

        api = ApiBuilder.providesApi();
        Call<ResponseEnvelope_ConfirmCode> call = api.requestConfirmCodeCall("ConfirmCode", envelope);

        call.enqueue(new retrofit2.Callback<ResponseEnvelope_ConfirmCode>() {

            @Override
            public void onResponse(@NonNull Call<ResponseEnvelope_ConfirmCode> call, @NonNull Response<ResponseEnvelope_ConfirmCode> response) {
                if (response.isSuccessful()) {
                    int codeResult = response.body().getBody().getData().getConfirmCodeResult();
                    switch (codeResult) {
                        case 1:
                            getUserId();
                            break;
                        case 2:
                            pDialog.setTitleText(getString(R.string.wrong_code))
                                    .setConfirmText(getString(R.string.try_again))
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            break;


                    }
                    Log.d("PIN PASS:", String.valueOf(codeResult));
                } else {
                    pDialog.setTitleText(getString(R.string.went_wrong))
                            .setConfirmText(getString(R.string.ok))
                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseEnvelope_ConfirmCode> call, @NonNull Throwable t) {
                Log.d("PIN ERROR:", String.valueOf(t));
            }
        });
    }

    private void getUserId() {
        UserData.clearUser(getActivity());
        RequestEnvelope_UserId envelope = new RequestEnvelope_UserId();
        RequestBody_UserId body = new RequestBody_UserId();
        RequestData_UserId data = new RequestData_UserId();

        data.setEmail(username.getText().toString());
        body.setRequestData(data);
        envelope.setBody(body);

        Call<ResponseEnvelope_UserId> userIdCall = api.requestUserIdCall("GetAccountInfoByEmail_JOSN", envelope);
        userIdCall.enqueue(new Callback<ResponseEnvelope_UserId>() {
            @Override
            public void onResponse(Call<ResponseEnvelope_UserId> call, Response<ResponseEnvelope_UserId> response) {
                if (response.isSuccessful()) {
                    pDialog.dismiss();
                    Gson gson = new Gson();
                    UserIdResponse[] list = gson.fromJson(response.body().getBody().getData().getElements(), UserIdResponse[].class);
                    List<UserIdResponse> models = new ArrayList<>();
                    models.addAll(Arrays.asList(list));
                    ActivityCompat.finishAffinity(getActivity());
                    MyApplication.get(getActivity()).addUser(username.getText().toString(), password.getText().toString());
                    UserModel model = new UserModel(username.getText().toString(), password.getText().toString(), models.get(0).getId(), models.get(0).getNameAR(), models.get(0).getNameEN());
                    UserData.saveUserObject(getActivity(), model, true);
                    startActivity(new Intent(getActivity(), IntroActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                }
            }

            @Override
            public void onFailure(Call<ResponseEnvelope_UserId> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
