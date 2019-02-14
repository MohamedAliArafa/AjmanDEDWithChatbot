package com.ajman.ded.ae.screens.home;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ajman.ded.ae.R;
import com.ajman.ded.ae.StatusActivity;
import com.ajman.ded.ae.ViewDialog;
import com.ajman.ded.ae.WebViewActivity;
import com.ajman.ded.ae.libs.AnimatedFragment;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.ServiceCenter;
import com.ajman.ded.ae.screens.login.LoginActivity;
import com.ajman.ded.ae.utility.AppPreferenceManager;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;
import static com.ajman.ded.ae.utility.Constants.URL_INTENT_KEY;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_AR;
import static com.ajman.ded.ae.utility.Constants.links.DOMAIN_EN;
import static com.ajman.ded.ae.utility.Constants.links.ISSUE_NEW_PERMIT;
import static com.ajman.ded.ae.utility.Constants.links.ISSUE_TRADE_LICENSE;
import static com.ajman.ded.ae.utility.Constants.links.RENEW_TRADE_LICENSE;
import static com.ajman.ded.ae.utility.Constants.links.TRADE_NAME_RESERVATION;

public class HomeTabFragment extends AnimatedFragment {

    MapView mMapView;
    CardView cardView1;
    CardView cardView2;
    CardView cardView3;
    CardView cardView5;
    EditText applicationNo;
    Button checkStatus;
    private LatLngBounds.Builder builder;
    private GoogleMap googleMap;
    private RelativeLayout newLicence, renewLicence, tradeName, newPermit;

    public HomeTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);
        newLicence = view.findViewById(R.id.con_1);
        renewLicence = view.findViewById(R.id.con_2);
        tradeName = view.findViewById(R.id.con_3);
        newPermit = view.findViewById(R.id.con_4);
        applicationNo = view.findViewById(R.id.app_edit_text);
        checkStatus = view.findViewById(R.id.check_status);
        Intent intent = new Intent(getActivity(), WebViewActivity.class);

        newLicence.setOnClickListener(view1 -> {
            if (UserData.getUserObject(getActivity()) != null) {
                if (Objects.equals(LocaleManager.getLanguage(getContext()), LANGUAGE_ARABIC))
                    intent.putExtra(URL_INTENT_KEY, DOMAIN_AR + ISSUE_TRADE_LICENSE);
                else
                    intent.putExtra(URL_INTENT_KEY, DOMAIN_EN + ISSUE_TRADE_LICENSE);
                startActivity(intent);
            } else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        renewLicence.setOnClickListener(view1 -> {
            if (UserData.getUserObject(getActivity()) != null) {

                if (Objects.equals(LocaleManager.getLanguage(getContext()), LANGUAGE_ARABIC))
                    intent.putExtra(URL_INTENT_KEY, DOMAIN_AR + RENEW_TRADE_LICENSE);
                else
                    intent.putExtra(URL_INTENT_KEY, DOMAIN_EN + RENEW_TRADE_LICENSE);
                startActivity(intent);
            } else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        tradeName.setOnClickListener(view1 -> {
            if (UserData.getUserObject(getActivity()) != null) {

                if (Objects.equals(LocaleManager.getLanguage(getContext()), LANGUAGE_ARABIC))
                    intent.putExtra(URL_INTENT_KEY, DOMAIN_AR + TRADE_NAME_RESERVATION);
                else
                    intent.putExtra(URL_INTENT_KEY, DOMAIN_EN + TRADE_NAME_RESERVATION);
                startActivity(intent);
            } else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        newPermit.setOnClickListener(view1 -> {
            if (UserData.getUserObject(getActivity()) != null) {
                if (Objects.equals(LocaleManager.getLanguage(getContext()), LANGUAGE_ARABIC))
                    intent.putExtra(URL_INTENT_KEY, DOMAIN_AR + ISSUE_NEW_PERMIT);
                else
                    intent.putExtra(URL_INTENT_KEY, DOMAIN_EN + ISSUE_NEW_PERMIT);
                startActivity(intent);
            } else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        InputStream raw = getActivity().getResources().openRawResource(R.raw.service_centers);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        ServiceCenter[] list = gson.fromJson(rd, ServiceCenter[].class);
        List<ServiceCenter> models = new ArrayList<>();
        models.addAll(Arrays.asList(list));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMapView = view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;

            googleMap.setOnMapLoadedCallback(() -> {
                builder = new LatLngBounds.Builder();
                if (Objects.equals(LocaleManager.getLanguage(getActivity()), LANGUAGE_ARABIC)) {
                    for (ServiceCenter marker : models) {
                        LatLng location = new LatLng(marker.getLatitude(), marker.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(location).title(marker.getNameAr()).snippet(marker.getAddressAr()));
                        builder.include(location);
                    }
                } else {
                    for (ServiceCenter marker : models) {
                        LatLng location = new LatLng(marker.getLatitude(), marker.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(location).title(marker.getName()).snippet(marker.getAddress()));
                        builder.include(location);
                    }
                }
                LatLngBounds bounds = builder.build();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
            });
        });

        cardView1 = view.findViewById(R.id.card1);
        cardView2 = view.findViewById(R.id.card2);
        cardView3 = view.findViewById(R.id.card3);
        cardView5 = view.findViewById(R.id.card5);

        cardView1.setVisibility(View.INVISIBLE);
        cardView2.setVisibility(View.INVISIBLE);
        cardView3.setVisibility(View.INVISIBLE);
        cardView5.setVisibility(View.INVISIBLE);

        checkStatus.setOnClickListener(view12 -> {
            if (TextUtils.isEmpty(applicationNo.getText().toString())) {
                applicationNo.setError(getString(R.string.required_field));
                applicationNo.requestFocus();
            } else {
                getActivity().startActivity(new Intent(getActivity(), StatusActivity.class).putExtra("appNo", applicationNo.getText().toString()));
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
        new AnimatedFragment.animate(new View[]{cardView1, cardView2, cardView3, cardView5}).execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
