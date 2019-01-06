package com.ajman.ded.ae.screens.complaints;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ajman.ded.ae.EyeImagesAdapter;
import com.ajman.ded.ae.R;
import com.ajman.ded.ae.ViewDialog;
import com.ajman.ded.ae.adapters.SpinnerAdapter;
import com.ajman.ded.ae.audio_recorder.AudioListener;
import com.ajman.ded.ae.audio_recorder.AudioRecordButton;
import com.ajman.ded.ae.audio_recorder.AudioRecording;
import com.ajman.ded.ae.audio_recorder.RecordingItem;
import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.dependency.component.DaggerLocationComponent;
import com.ajman.ded.ae.dependency.module.ContextModule;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.InsertNotificationResponse;
import com.ajman.ded.ae.models.Tybe;
import com.ajman.ded.ae.utility.CustomMapView;
import com.ajman.ded.ae.utility.sweetDialog.SweetAlertDialog;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitActivity extends AppCompatActivity implements EyeImagesAdapter.AdapterCallback, LocationListener, OnMapReadyCallback {
    public static final int TYPE_TYPE = 2;

    private static final int REQUEST_CHECK_SETTINGS = 9000;
    final private int REQUEST_CODE_AUDIO_PERMISSIONS = 9001;
    final private int REQUEST_CODE_IMAGE_PERMISSIONS = 9002;
    final private int REQUEST_CODE_LOCATION_PERMISSIONS = 9003;
    public static final int OPEN_CAMERA = 9004;
    public static final int OPEN_GALLERY = 9005;
    private String[] perms;

    @Inject
    Task<LocationSettingsResponse> locationTask;

    @Inject
    LocationManager locationManager;

    @BindView(R.id.recorder)
    AudioRecordButton audioRecordButton;
    @BindView(R.id.seekbar)
    AppCompatSeekBar mSeekBar;
    @BindView(R.id.play_stop)
    ImageView play_stop;
    @BindView(R.id.voice_cardview)
    CardView voice_cardview;
    @BindView(R.id.audio_container)
    ConstraintLayout audioContainer;
    @BindView(R.id.max)
    TextView max;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.add_image)
    Button addImage;
    @BindView(R.id.location)
    Button location;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.map)
    CustomMapView map;
    @BindView(R.id.complaint_type)
    Spinner typeSpinner;
    @BindView(R.id.complaint)
    EditText complaint;
    @BindView(R.id.complaint_details)
    EditText complaint_details;
    @BindView(R.id.facility_title)
    EditText establishmentTitle;
    @BindView(R.id.licence_no)
    EditText licenceNo;
    private AudioRecording audioRecord;
    private RecordingItem recordingAudio;
    private EyeImagesAdapter mAdapter;
    private ArrayList<String> imagesEncodedList;
    private String imageEncoded;
    private GoogleMap googleMap;
    private LatLngBounds.Builder builder;
    private SpinnerAdapter typeAdapter;
    private String tybeId;
    private Api api;
    private String lat = "";
    private String lng = "";
    private String tybePeriod, tybeStatus;
    private SweetAlertDialog pDialog;
    private Uri uri;

    private Context viewContext() {
        return this;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        ButterKnife.bind(this);
        toolbarTitle.setText(getString(R.string.submit_notification));

        findViewById(R.id.up).setOnClickListener(view -> super.onBackPressed());
        findViewById(R.id.cancel).setOnClickListener(view -> super.onBackPressed());
        DaggerLocationComponent.builder().contextModule(new ContextModule(viewContext())).build().inject(this);

        if (map != null) {
            map.onCreate(null);
            map.onResume();
            MapsInitializer.initialize(this);
            map.getMapAsync(this::onMapReady);
        }

        perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestAudioPermission();

        mAdapter = new EyeImagesAdapter(this, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(mAdapter);

        addImage.setOnClickListener(this::onAddImage);
        location.setOnClickListener(this::onRequestLocation);

        audioRecordButton.setOnAudioListener(new AudioListener() {
            @Override
            public void onStop(RecordingItem recordingItem) {
                audioContainer.setVisibility(View.VISIBLE);
                audioRecord.prepare(recordingItem);
                recordingAudio = recordingItem;
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(Exception e) {

            }
        });

        InputStream raw = getResources().openRawResource(R.raw.type);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        Tybe obj = gson.fromJson(rd, Tybe.class);
        typeAdapter = new SpinnerAdapter(this, R.layout.spinner_text, obj.getResponseContent(), TYPE_TYPE);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos != 0) {
                    tybeId = obj.getResponseContent().get(pos - 1).getID();
                    tybePeriod = obj.getResponseContent().get(pos - 1).getPeriodInDays();
                    tybeStatus = obj.getResponseContent().get(pos - 1).getNotificationStatusNameAR();
                    typeSpinner.clearFocus();
                    complaint.setInputType(InputType.TYPE_NULL);
                    complaint.setText(obj.getResponseContent().get(pos - 1).getDescriptionAR());
                    complaint_details.requestFocusFromTouch();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        String date = df.format(Calendar.getInstance().getTime());

        api = ApiBuilder.basicApi();

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getString(R.string.loading));
        pDialog.setCancelable(true);

        findViewById(R.id.send).setOnClickListener(view -> {
            if (lat.length() > 0 && lng.length() > 0 && establishmentTitle.getText().toString().length() > 0 && licenceNo.getText().toString().length() > 0 && complaint_details.getText().toString().length() > 0) {
                Call<InsertNotificationResponse> call = api.insert_notification("10", date, establishmentTitle.getText().toString(), licenceNo.getText().toString(), tybeId, complaint_details.getText().toString(), lat, lng);
                pDialog.show();
                call.clone().enqueue(new Callback<InsertNotificationResponse>() {
                    @Override
                    public void onResponse(Call<InsertNotificationResponse> call, Response<InsertNotificationResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResponseCode() == 1) {
                                pDialog.setTitleText("بلاغ " + tybeStatus + " سوف يستغرق مدة " + tybePeriod + " من الأيام ليتم مراجعتها").setConfirmClickListener(sweetAlertDialog -> SubmitActivity.super.onBackPressed()).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        } else {
                            pDialog.dismissWithAnimation();
                        }
                    }

                    @Override
                    public void onFailure(Call<InsertNotificationResponse> call, Throwable t) {
                        pDialog.dismissWithAnimation();
                    }
                });
            } else {
                Toast.makeText(this, getString(R.string.fill_all), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onRequestLocation(View view) {
        requestLocationPermission();
    }

    private void play_stop(View view) {
        if (audioRecord.isPlaying()) {
            audioRecord.pause();
        } else {
            if (audioRecord != null)
                audioRecord.play();
        }
    }

    private void requestAudioPermission() {
        int hasAudioPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        if (hasAudioPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_AUDIO_PERMISSIONS);
        } else {
            initAudio();
        }
    }

    private void requestLocationPermission() {
        int hasAudioPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasAudioPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSIONS);
        } else {
            accessUserLocation();
        }
    }

    private boolean requestStoragePermission() {
        int hasReadPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWritePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (hasReadPermission != PackageManager.PERMISSION_GRANTED && hasWritePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, REQUEST_CODE_IMAGE_PERMISSIONS);
            return false;
        } else {
            return true;
        }
    }

    private void onAddImage(View view) {
        if (requestStoragePermission()) {
            openGallery();
        }
    }

    private void initAudio() {
        audioRecord = new AudioRecording(SubmitActivity.this);
        audioRecord.setSeekBar(mSeekBar);
        audioRecord.setMax(max);
        voice_cardview.setVisibility(View.VISIBLE);
        play_stop.setOnClickListener(this::play_stop);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_AUDIO_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAudio();
                }
                break;
            case REQUEST_CODE_IMAGE_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
            case REQUEST_CODE_LOCATION_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
        }
    }

    private void accessUserLocation() {
        if (checkIfLocationIsEnabled()) {
            listenToLocations();
        } else {
            resolveLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void listenToLocations() {
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
    }

    private void resolveLocation() {
        locationRequestTask().addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                requestLocationPermission();
            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) exception;
                            resolvable.startResolutionForResult(
                                    SubmitActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException | ClassCastException ignored) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }


    private boolean checkIfLocationIsEnabled() {
        return locationManager().isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private Task<LocationSettingsResponse> locationRequestTask() {
        return locationTask;
    }

    private LocationManager locationManager() {
        return locationManager;
    }

    @Override
    public void onAddCallback() {
        if (requestStoragePermission()) {
            openGallery();
        }
    }

    private void openGallery() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imageFile = new File(this.getApplicationContext().getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + "ajmanded_" + timeStamp + ".jpg");
        uri = FileProvider.getUriForFile(this, "com.ajman.ded.ae.provider", imageFile);
        new ViewDialog().showChooseDialog(this, uri);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultData) {
        if (requestCode == OPEN_GALLERY && resultCode == RESULT_OK) {
            imagesEncodedList = new ArrayList<>();
            if (resultData.getData() != null) {
                Uri mImageUri = resultData.getData();
//                imageEncoded = mImageUri.getPath();
                imagesEncodedList.clear();
                imagesEncodedList.add(mImageUri.toString());
                mAdapter.addImage(imagesEncodedList);
            } else {
                if (resultData.getClipData() != null) {
                    ClipData mClipData = resultData.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        imagesEncodedList.clear();
//                        imageEncoded = uri.getPath();
                        imagesEncodedList.add(uri.toString());
                        mAdapter.addImage(imagesEncodedList);
                    }
                }
            }
        }

        if (requestCode == OPEN_CAMERA && resultCode == RESULT_OK) {
            imagesEncodedList = new ArrayList<>();
            imagesEncodedList.clear();
//            try {
//                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            imagesEncodedList.add(uri.toString());
            mAdapter.addImage(imagesEncodedList);
        }

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    requestLocationPermission();
                case Activity.RESULT_CANCELED:
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(Location location) {
        lat = String.valueOf(location.getLatitude());
        lng = String.valueOf(location.getLongitude());
        googleMap.setMyLocationEnabled(true);
        builder = new LatLngBounds.Builder();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.your_location))).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
        builder.include(latLng);
        LatLngBounds bounds = builder.build();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 8));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        int hasAudioPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasAudioPermission == PackageManager.PERMISSION_GRANTED)
            googleMap.setMyLocationEnabled(true);
    }

    public void onDestroy() {
        if (audioRecord != null)
            audioRecord.stop();
        super.onDestroy();
    }
}
