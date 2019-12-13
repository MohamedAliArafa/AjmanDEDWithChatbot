package com.ajman.ded.ae.screens.login;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ajman.ded.ae.MyApplication;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.UAEPassRequestModels;
import com.ajman.ded.ae.WebViewActivity;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestBody_ConfirmCode;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestData_ConfirmCode;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestEnvelope_ConfirmCode;
import com.ajman.ded.ae.data.model.request.GetAccount.RequestBody_GetAccount;
import com.ajman.ded.ae.data.model.request.GetAccount.RequestData_GetAccount;
import com.ajman.ded.ae.data.model.request.GetAccount.RequestEnvelope_GetAccount;
import com.ajman.ded.ae.data.model.request.OnlineUseUAEPass.RequestBody_OnlineUaePass;
import com.ajman.ded.ae.data.model.request.OnlineUseUAEPass.RequestData_OnlineUaePass;
import com.ajman.ded.ae.data.model.request.OnlineUseUAEPass.RequestEnvelope_OnlineUAEPass;
import com.ajman.ded.ae.data.model.request.UserId.RequestBody_UserId;
import com.ajman.ded.ae.data.model.request.UserId.RequestData_UserId;
import com.ajman.ded.ae.data.model.request.UserId.RequestEnvelope_UserId;
import com.ajman.ded.ae.data.model.response.ConfirmCode.ResponseEnvelope_ConfirmCode;
import com.ajman.ded.ae.data.model.response.GetAccount.ResponseEnvelope_GetAccount;
import com.ajman.ded.ae.data.model.response.OnlineUseUAEPass.ResponseEnvelope_UAEPass;
import com.ajman.ded.ae.data.model.response.UserId.ResponseEnvelope_UserId;
import com.ajman.ded.ae.models.UserIdResponse;
import com.ajman.ded.ae.models.UserModel;
import com.ajman.ded.ae.models.uaepass.AuthTokenModel;
import com.ajman.ded.ae.screens.IntroActivity;
import com.ajman.ded.ae.screens.ded_eye.DedEyeActivity;
import com.ajman.ded.ae.screens.home.HomeActivity;
import com.ajman.ded.ae.screens.registeration.RegisterActivity;
import com.ajman.ded.ae.utility.SharedTool.SharedPreferencesTool;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.goodiebag.pinview.Pinview;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import ae.sdg.libraryuaepass.UAEPassAccessTokenCallback;
import ae.sdg.libraryuaepass.UAEPassController;
import ae.sdg.libraryuaepass.UAEPassProfileCallback;
import ae.sdg.libraryuaepass.business.authentication.model.UAEPassAccessTokenRequestModel;
import ae.sdg.libraryuaepass.business.profile.model.ProfileModel;
import ae.sdg.libraryuaepass.business.profile.model.UAEPassProfileRequestModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.ACTIVITY_SERVICE;
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
    public static final Integer webViewRequestCode = 1152;

    public static final String UAE_PASS_CLIENT_ID = "ajm_ded_mob_stage";
    public static final String UAE_PASS_CLIENT_SECRET = "QYknfXVshPZmPlsq";
    public static final String REDIRECT_URL = "ajmanded://uaepass.sdg.ae/success";
    public static final String DOCUMENT_SIGNING_SCOPE = "urn:safelayer:eidas:sign:process:document";
    public static final String RESPONSE_TYPE = "code";
    public static final String SCOPE = "urn:uae:digitalid:profile";
    public static final String ACR_VALUES_MOBILE = "urn:digitalid:authentication:flow:mobileondevice";
    public static final String ACR_VALUES_WEB = "urn:safelayer:tws:policies:authentication:level:low";
    public static final String UAE_PASS_PACKAGE_ID = "ae.uaepass.mainapp";

    public LoginFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("deprecation")
    public void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }


    public void clearApplicationData() {
        SharedPreferencesTool.clearObject(getContext());
        File cacheDirectory = getContext().getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }

    /**
     * Get User Profile from UAE Pass.
     */
    private void getProfile() {
        UAEPassAccessTokenRequestModel requestModel = UAEPassRequestModels.getAuthenticationRequestModel(getContext());
        UAEPassController.getInstance().getAccessToken(getContext(), requestModel, (accessToken, error) -> {
            if (error != null) {
                if (!error.equals("No Intent available to handle action")){
                    Toast.makeText(getContext(), R.string.user_cancel_login_error_msg , Toast.LENGTH_SHORT).show();
                }
            } else {
//                Toast.makeText(getContext(), "Access Token Received", Toast.LENGTH_SHORT).show();
                UAEPassProfileRequestModel profileRequestModel = UAEPassRequestModels.getProfileRequestModel(getContext());
                UAEPassController.getInstance().getUserProfile(getContext(), profileRequestModel, (profileModel, profileError) -> {
                    if (profileError != null) {
                        Log.e("UAE_PASS_ERROR", profileError);
                        Toast.makeText(getContext(), "Failed to login", Toast.LENGTH_SHORT).show();
                    } else {
                        clearApplicationData();
                        clearCookies(getContext());
                        String jsonString = new Gson().toJson(profileModel);
                        Log.d("UAE_PASS", jsonString);
                        if (profileModel.getUserType().equals("SOP1")) {
                            Toast.makeText(getContext(), R.string.sop1_Unverified_user_error_msg, Toast.LENGTH_SHORT).show();
                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText(getString(R.string.sop1_dialog_title))
                                    .setContentText(getString(R.string.sop1_dialog_content))
                                    .setConfirmText(getString(R.string.sop1_dialog_button))
                                    .setConfirmClickListener(sDialog -> {
                                        sDialog.dismissWithAnimation();
                                        startActivity(new Intent(getActivity(), HomeActivity.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                                    })
                                    .show();
                        } else {
                            Toast.makeText(getContext(), R.string.login_success , Toast.LENGTH_SHORT).show();
                            api = ApiBuilder.providesApi();
                            RequestEnvelope_OnlineUAEPass envelope = new RequestEnvelope_OnlineUAEPass();
                            RequestBody_OnlineUaePass body = new RequestBody_OnlineUaePass();
                            RequestData_OnlineUaePass data = new RequestData_OnlineUaePass();
                            data.setMobile(profileModel.getMobile());
                            data.setEmail(profileModel.getEmail());
                            data.setIdn(profileModel.getIdn());
                            data.setFullnameAR(profileModel.getFullnameAR());
                            data.setFullnameEN(profileModel.getFullnameEN());
                            data.setPassportNumber(profileModel.getPassportNumber());
                            data.setUuid(profileModel.getSub());
                            body.setRequestData(data);
                            envelope.setBody(body);
                            Call<ResponseEnvelope_UAEPass> call = api.registerUAEPassProfile(envelope);
                            call.enqueue(new retrofit2.Callback<ResponseEnvelope_UAEPass>() {

                                @Override
                                public void onResponse(@NonNull Call<ResponseEnvelope_UAEPass> call, @NonNull Response<ResponseEnvelope_UAEPass> response) {
                                    if (response.isSuccessful()) {
                                        String codeResult = response.body().getBody().getData().getAccountResult();
                                        Log.d("AccountResult:", String.valueOf(codeResult));
                                        Gson gson = new Gson();
                                        UserIdResponse[] list = gson.fromJson(codeResult, UserIdResponse[].class);
                                        List<UserIdResponse> models = new ArrayList<>();
                                        models.addAll(Arrays.asList(list));
                                        ActivityCompat.finishAffinity(getActivity());
                                        MyApplication.get(getActivity()).addUser(username.getText().toString(), password.getText().toString());
                                        UserModel model = new UserModel(profileModel.getEmail(), "", models.get(0).getId(), profileModel.getFullnameAR(), profileModel.getFullnameEN());
                                        UserData.saveUserObject(getActivity(), model, true);
                                        startActivity(new Intent(getActivity(), IntroActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                                    } else {
                                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<ResponseEnvelope_UAEPass> call, @NonNull Throwable t) {
                                    Log.d("PIN ERROR:", String.valueOf(t));
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        Button UAEPassBtn = rootView.findViewById(R.id.uae_pass_btn);
        Button login = rootView.findViewById(R.id.login_btn);
        username = rootView.findViewById(R.id.username_input);
        password = rootView.findViewById(R.id.password_input);
        TextView regisrer = rootView.findViewById(R.id.signup);
        regisrer.setOnClickListener(view -> startActivity(new Intent(getActivity(), RegisterActivity.class)));

        UAEPassBtn.setOnClickListener(v -> getProfile());

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
