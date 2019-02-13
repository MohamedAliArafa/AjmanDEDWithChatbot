package com.ajman.ded.ae.screens.registeration;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.ajman.ded.ae.MyApplication;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.adapters.SpinnerAdapter;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestBody_ConfirmCode;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestData_ConfirmCode;
import com.ajman.ded.ae.data.model.request.ConfirmCode.RequestEnvelope_ConfirmCode;
import com.ajman.ded.ae.data.model.request.InsertNewOnlineUser.RequestBody_InsertNewOnlineUser;
import com.ajman.ded.ae.data.model.request.InsertNewOnlineUser.RequestData_InsertNewOnlineUser;
import com.ajman.ded.ae.data.model.request.InsertNewOnlineUser.RequestEnvelope_InsertNewOnlineUser;
import com.ajman.ded.ae.data.model.request.UserId.RequestBody_UserId;
import com.ajman.ded.ae.data.model.request.UserId.RequestData_UserId;
import com.ajman.ded.ae.data.model.request.UserId.RequestEnvelope_UserId;
import com.ajman.ded.ae.data.model.response.ConfirmCode.ResponseEnvelope_ConfirmCode;
import com.ajman.ded.ae.data.model.response.InsertNewOnlineUser.ResponseEnvelope_InsertNewOnlineUser;
import com.ajman.ded.ae.data.model.response.UserId.ResponseEnvelope_UserId;
import com.ajman.ded.ae.models.Country;
import com.ajman.ded.ae.models.RegisterLookups;
import com.ajman.ded.ae.models.StockholderType;
import com.ajman.ded.ae.models.UserIdResponse;
import com.ajman.ded.ae.models.UserModel;
import com.ajman.ded.ae.screens.IntroActivity;
import com.ajman.ded.ae.utility.MaskEditText;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.ajman.ded.ae.utility.otpSms.SmsListener;
import com.ajman.ded.ae.utility.otpSms.SmsReceiver;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.goodiebag.pinview.Pinview;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ajman.ded.ae.utility.Helper.convertDpToPixel;
import static com.ajman.ded.ae.utility.Helper.isValidEmail;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements SmsListener {
    public static final int TYPE_STOCK = 0;
    public static final int TYPE_NATIONALITY = 1;
    @BindView(R.id.username_input)
    EditText username;
    @BindView(R.id.mobile_input)
    MaskEditText mobileNo;
    @BindView(R.id.password_input)
    EditText password;
    @BindView(R.id.confirm_input)
    EditText confirmPassword;
    @BindView(R.id.fullname_en)
    EditText fullNameEn;
    @BindView(R.id.full_name_ar)
    EditText fullNameAr;
    @BindView(R.id.nid_input)
    EditText nidNo;
    @BindView(R.id.nid_exp_input)
    EditText nidExpiry;
    @BindView(R.id.passport_input)
    EditText passportNo;
    @BindView(R.id.address_input)
    EditText address;
    @BindView(R.id.language_switch)
    Switch languageSwitch;
    @BindView(R.id.gender_switch)
    Switch genderSwitch;
    //    @BindView(R.id.cur_inverstor)
//    SwitchCompat investorSwitch;
    @BindView(R.id.spinner_stock)
    Spinner spinnerStock;
    @BindView(R.id.spinner_nat)
    Spinner spinnerNationality;
    @BindView(R.id.register)
    Button register;
    private RegisterLookups models;
    private Unbinder unbinder;
    private List<StockholderType> stockholderTypes;
    private List<Country> countries;

    private String stackholderType;
    private String nationality;
    private String language;
    private String gender;
    private boolean currentInvestor;
    private Api api;
    private SpinnerAdapter stockAdapter;
    private SpinnerAdapter nationalityAdapter;
    private String expirationDateServer;
    private int i = -1;
    private SweetAlertDialog pDialog;
    private String insertResult;
    private CountDownTimer countDown;
    private SwitchCompat aSwitchs;
    private SwitchCompat investorSwitch;
    private Pinview pinview;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        models = Realm.getDefaultInstance().where(RegisterLookups.class).findFirst();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        SmsReceiver.bind(this, "AjmanDED");
        investorSwitch = view.findViewById(R.id.cur_inverstor);

        unbinder = ButterKnife.bind(this, view);
        stockholderTypes = new ArrayList<>();
        stockholderTypes.addAll(models.getStockholderType());
        stockAdapter = new SpinnerAdapter(getActivity(), R.layout.spinner_row, stockholderTypes, TYPE_STOCK);
        spinnerStock.setAdapter(stockAdapter);
        spinnerStock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    stackholderType = stockholderTypes.get(pos - 1).getId();
                    spinnerStock.clearFocus();
                    username.requestFocusFromTouch();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        countries = new ArrayList<>();
        countries.addAll(models.getCountry());
        nationalityAdapter = new SpinnerAdapter(getActivity(), R.layout.spinner_row, countries, TYPE_NATIONALITY);
        spinnerNationality.setAdapter(nationalityAdapter);
        spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    nationality = countries.get(pos - 1).getId();
                    spinnerNationality.clearFocus();
                    nidNo.requestFocusFromTouch();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        configuration();

        nidExpiry.setOnClickListener(v -> {
            Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.MyDatePickerDialogTheme,
                    (view1, year, monthOfYear, dayOfMonth) -> {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                        SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz", Locale.US);
                        String expirationDate = userFormat.format(myCalendar.getTime());
                        expirationDateServer = serverFormat.format(myCalendar.getTime()).replace("GMT", "");
                        nidExpiry.setText(expirationDate);
                        nidExpiry.setError(null);
                        nidExpiry.clearFocus();
                        passportNo.requestFocusFromTouch();

                    }, myCalendar
                    .get(Calendar.YEAR), myCalendar
                    .get(Calendar.MONTH), myCalendar
                    .get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
            datePickerDialog.show();
        });

        register.setOnClickListener(view1 -> validation());
        return view;
    }


    private void validation() {
        if (!isValidEmail(username.getText().toString())) {
            username.setError(getString(R.string.email_validation));
            username.requestFocus();
        } else if (spinnerStock.getSelectedItemPosition() == 0) {
            TextView errorText = (TextView) spinnerStock.getSelectedView();
            errorText.setError("");
            errorText.requestFocus();
            errorText.setTextColor(Color.RED);
            errorText.setText(R.string.select_stock_holder_type);
            spinnerStock.requestFocusFromTouch();
        } else if (spinnerNationality.getSelectedItemPosition() == 0) {
            TextView error = (TextView) spinnerNationality.getSelectedView();
            error.setError("");
            error.setTextColor(Color.RED);//just to highlight that this is an error
            error.setText(R.string.select_nationality);
            spinnerNationality.requestFocusFromTouch();
        } else if (TextUtils.isEmpty(mobileNo.getText().toString())) {
            mobileNo.setError(getString(R.string.required_field));
            mobileNo.requestFocus();
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError(getString(R.string.required_field));
            password.requestFocus();
        } else if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
            confirmPassword.setError(getString(R.string.required_field));
            confirmPassword.requestFocus();
        } else if (!Objects.equals(password.getText().toString(), confirmPassword.getText().toString())) {
            confirmPassword.setError(getString(R.string.password_no_match));
            confirmPassword.requestFocus();
        } else if (TextUtils.isEmpty(fullNameEn.getText().toString())) {
            fullNameEn.setError(getString(R.string.required_field));
            fullNameEn.requestFocus();
        } else if (TextUtils.isEmpty(fullNameAr.getText().toString())) {
            fullNameAr.setError(getString(R.string.required_field));
            fullNameAr.requestFocus();
        } else if (TextUtils.isEmpty(nidNo.getText().toString())) {
            nidNo.setError(getString(R.string.required_field));
            nidNo.requestFocus();
        } else if (TextUtils.isEmpty(nidExpiry.getText().toString())) {
            nidExpiry.setError(getString(R.string.required_field));
            nidExpiry.requestFocus();
        } else if (TextUtils.isEmpty(passportNo.getText().toString())) {
            passportNo.setError(getString(R.string.required_field));
            passportNo.requestFocus();
        } else if (TextUtils.isEmpty(address.getText().toString())) {
            address.setError(getString(R.string.required_field));
            address.requestFocus();
        } else
            getValues();
    }

    private void getValues() {
        try {
            if (languageSwitch.isChecked())
                language = models.getLanguage().get(1).getId();
            else
                language = models.getLanguage().get(0).getId();
            if (genderSwitch.isChecked())
                gender = models.getGender().get(0).getId();
            else gender = models.getGender().get(1).getId();
            if (investorSwitch.isChecked())
                currentInvestor = true;
        } finally {
            doInsertNewUser();
        }
    }

    private void doInsertNewUser() {
        Progress();
        RequestEnvelope_InsertNewOnlineUser envelope = new RequestEnvelope_InsertNewOnlineUser();

        RequestBody_InsertNewOnlineUser body = new RequestBody_InsertNewOnlineUser();

        RequestData_InsertNewOnlineUser data = new RequestData_InsertNewOnlineUser();

        data.setStackholderType(stackholderType);
        data.setEmail(username.getText().toString());
        data.setMobileNo("5" + mobileNo.getRawText());
        data.setPassword(password.getText().toString());
        data.setConfirmPassword(confirmPassword.getText().toString());
        data.setLanguage(language);
        data.setNameEN(fullNameEn.getText().toString());
        data.setNameAR(fullNameAr.getText().toString());
        data.setNationality(nationality);
        data.setGender(gender);
        data.setIdentityNumber(nidNo.getText().toString());
        data.setIdentityExpireDate(expirationDateServer);
        data.setPassportNo(passportNo.getText().toString());
        data.setAddress(address.getText().toString());
        data.setIsCurrentInvestor(currentInvestor);
        body.setRequestData(data);

        envelope.setBody(body);

        api = ApiBuilder.providesApi();


        Call<ResponseEnvelope_InsertNewOnlineUser> call = api.requestNewOnlineUserCall("InsertNewOnlineUser_Josn", envelope);

        call.enqueue(new retrofit2.Callback<ResponseEnvelope_InsertNewOnlineUser>() {

            @Override
            public void onResponse(@NonNull Call<ResponseEnvelope_InsertNewOnlineUser> call, @NonNull final Response<ResponseEnvelope_InsertNewOnlineUser> response) {
                if (response.isSuccessful()) {
                    insertResult = response.body().getBody().getData().getInsertAccountResult().replace("\"", "");
                    Log.d("INSERT PASS:", String.valueOf(insertResult));
                    switch (insertResult) {
                        case "101":
                            pDialog.setTitleText(getString(R.string.already_registered))
                                    .setConfirmText(getString(R.string.ok))
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            break;
                        case "102":
                            pDialog.setTitleText(getString(R.string.phone_registered))
                                    .setConfirmText(getString(R.string.ok))
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            break;
                        case "103":
                            pDialog.setTitleText(getString(R.string.nid_registered))
                                    .setConfirmText(getString(R.string.ok))
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            break;
                        case "104":
                            pDialog.setTitleText(getString(R.string.passport_registered))
                                    .setConfirmText(getString(R.string.ok))
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                            break;
                        default:
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

                    }
                } else {
                    pDialog.setTitleText(getString(R.string.went_wrong))
                            .setConfirmText(getString(R.string.ok))
                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseEnvelope_InsertNewOnlineUser> call, @NonNull Throwable t) {
                Log.d("INSERT ERROR:", String.valueOf(t));
                pDialog.setTitleText(getString(R.string.went_wrong))
                        .setConfirmText(getString(R.string.ok))
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        pDialog = null;
        SmsReceiver.unbind();

    }

    private void Progress() {
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("Loading");
        pDialog.show();
        pDialog.setCancelable(true);
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
                        default:
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
                pDialog.setTitleText(getString(R.string.went_wrong))
                        .setConfirmText(getString(R.string.ok))
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            }
        });
    }

    private void configuration() {
        spinnerStock.setFocusable(true);
        spinnerStock.setFocusableInTouchMode(true);
        spinnerStock.requestFocusFromTouch();
        fullNameAr.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                hideKeyboard();
                textView.clearFocus();
                spinnerNationality.requestFocusFromTouch();
            }
            return true;
        });

        nidNo.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                hideKeyboard();
                textView.clearFocus();
                nidExpiry.requestFocusFromTouch();
            }
            return true;
        });

        spinnerStock.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
            }
            return true;
        });

        spinnerNationality.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
            }
            return true;
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
    public void messageReceived(String messageText) {
        pinview.setValue(messageText);
    }
}
