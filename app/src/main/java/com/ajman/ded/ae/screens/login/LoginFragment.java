package com.ajman.ded.ae.screens.login;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ajman.ded.ae.MyApplication;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestBody_ConfirmCode;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestData_ConfirmCode;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestEnvelope_ConfirmCode;
import com.ajman.ded.ae.data.model.request.GetAccount.RequestBody_GetAccount;
import com.ajman.ded.ae.data.model.request.GetAccount.RequestData_GetAccount;
import com.ajman.ded.ae.data.model.request.GetAccount.RequestEnvelope_GetAccount;
import com.ajman.ded.ae.data.model.response.ConfirmCode.ResponseEnvelope_ConfirmCode;
import com.ajman.ded.ae.data.model.response.GetAccount.ResponseEnvelope_GetAccount;
import com.ajman.ded.ae.models.UserModel;
import com.ajman.ded.ae.screens.IntroActivity;
import com.ajman.ded.ae.screens.registeration.RegisterActivity;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.ajman.ded.ae.utility.otpSms.SmsListener;
import com.ajman.ded.ae.utility.otpSms.SmsReceiver;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.goodiebag.pinview.Pinview;

import retrofit2.Call;
import retrofit2.Response;

import static com.ajman.ded.ae.utility.Helper.convertDpToPixel;
import static com.ajman.ded.ae.utility.Helper.isValidEmail;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements SmsListener {


    private Api api;
    private SweetAlertDialog pDialog;
    private EditText username;
    private EditText password;
    private Pinview pinview;

    public LoginFragment() {
        // Required empty public constructor
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
        SmsReceiver.bind(this, "AjmanDED");
        login.setOnClickListener(view -> {
            if (!isValidEmail(username.getText().toString())) {
                username.setError(getString(R.string.email_validation));
                username.requestFocus();
            } else if (password.getText().toString().isEmpty()) {
                password.setError(getString(R.string.password_validation));
                password.requestFocus();
            } else {
                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
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
                                    UserData.clearUser(getActivity());
                                    pDialog.dismiss();
                                    ActivityCompat.finishAffinity(getActivity());
                                    MyApplication.get(getActivity()).addUser(username.getText().toString(), password.getText().toString());
                                    UserModel model = new UserModel(username.getText().toString(), password.getText().toString());
                                    UserData.saveUserObject(getActivity(), model, true);
                                    startActivity(new Intent(getActivity(), IntroActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
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
                            MyApplication.get(getActivity()).addUser(username.getText().toString(), password.getText().toString());
                            UserModel model = new UserModel(username.getText().toString(), password.getText().toString());
                            UserData.saveUserObject(getActivity(), model, true);
                            startActivity(new Intent(getActivity(), IntroActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
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
                            .setConfirmText("OK")
                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseEnvelope_ConfirmCode> call, @NonNull Throwable t) {
                Log.d("PIN ERROR:", String.valueOf(t));
            }
        });
    }

    @Override
    public void messageReceived(String messageText) {
//        String number = messageText.replaceAll("\\D+","");
//        Log.d("Otp",number + " From " + messageText);
        pinview.setValue(messageText);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SmsReceiver.unbind();
    }

//    private void sendSMS() {
//        RequestEnvelope_SendSMS envelope = new RequestEnvelope_SendSMS();
//
//        RequestBody_SendSMS body = new RequestBody_SendSMS();
//
//        RequestData_SendSMS data = new RequestData_SendSMS();
//
//        SecureRandom random = new SecureRandom();
//        int num = random.nextInt(100000);
//        String formatted = String.format(Locale.US, "%05d", num);
//        data.setMessageEN(String.format(Locale.US, getString(R.string.code_verification_formula), formatted));
//        data.setMessageAR("رقم تفعيل تطبيق عجمان هو : " + formatted);
//        data.setMobileNo(mobileNo.getText().toString());
//        body.setRequestData(data);
//
//        envelope.setBody(body);
//
//        api = ApiBuilder.providesApi();
//
//        Call<ResponseEnvelope_SendSMS> call = api.requestSmsCall("SendSMS", envelope);
//
//        call.enqueue(new retrofit2.Callback<ResponseEnvelope_SendSMS>() {
//
//            @Override
//            public void onResponse(@NonNull Call<ResponseEnvelope_SendSMS> call, @NonNull final Response<ResponseEnvelope_SendSMS> response) {
//                if (response.isSuccessful()) {
//                    String result = response.body().getBody().getData().getSendSMSResult().replace("\"", "");
//                    Log.d("SMS PASS:", String.valueOf(result));
//                } else {
//                    pDialog.setTitleText(getString(R.string.went_wrong))
//                            .setConfirmText("OK")
//                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseEnvelope_SendSMS> call, @NonNull Throwable t) {
//                Log.d("SMS ERROR:", String.valueOf(t));
//                pDialog.setTitleText(getString(R.string.went_wrong))
//                        .setConfirmText("OK")
//                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
//            }
//        });
//    }
}
