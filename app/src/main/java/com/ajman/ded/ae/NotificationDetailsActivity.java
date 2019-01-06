package com.ajman.ded.ae;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.notification.details.NotificationDetailsResponse;
import com.ajman.ded.ae.models.notification.details.ResponseContent;
import com.ajman.ded.ae.utility.CustomMapView;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ajman.ded.ae.audio_recorder.AudioRecording.PLAYBACK_POSITION_REFRESH_INTERVAL_MS;
import static com.ajman.ded.ae.libs.LocaleManager.LANGUAGE_ARABIC;

public class NotificationDetailsActivity extends AppCompatActivity implements EyeDetailsImagesAdapter.AdapterCallback {

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.licence)
    TextView licenceNo;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.noti)
    TextView noti;
    @BindView(R.id.details)
    TextView details;
    @BindView(R.id.result)
    TextView result;
    @BindView(R.id.rate)
    Button rate;
    @BindView(R.id.main_container)
    ConstraintLayout container;
    @BindView(R.id.play_stop)
    ImageView play_stop;
    @BindView(R.id.seekbar)
    AppCompatSeekBar mSeekBar;
    @BindView(R.id.max)
    TextView mMax;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.map)
    CustomMapView map;

    private Api api;
    private EyeDetailsImagesAdapter mAdapter;
    private List<Integer> ImageList;
    private SimpleDateFormat simpleDateFormat;
    private MediaPlayer mMediaPlayer;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;
    private GoogleMap googleMap;
    private LatLngBounds.Builder builder;
    private String id;
    private String satisfied = "1";
    private Button cancel, send;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        ButterKnife.bind(this);

        toolbarTitle.setText(getString(R.string.notification_details));

        findViewById(R.id.up).setOnClickListener(view -> super.onBackPressed());

        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd - hh:mm a");
        if (map != null) {
            map.onCreate(null);
            map.onResume();
            MapsInitializer.initialize(this);
            map.getMapAsync(this::onMapReady);
        }

        ImageList = new ArrayList();
        ImageList.add(R.drawable.ic_image_one);
        ImageList.add(R.drawable.ic_image_two);
        ImageList.add(R.drawable.ic_image_third);

        mAdapter = new EyeDetailsImagesAdapter(this, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(mAdapter);

        id = getIntent().getStringExtra("id");

        api = ApiBuilder.basicApi();
        Call<NotificationDetailsResponse> call = api.details_notification(id);

        call.clone().enqueue(new Callback<NotificationDetailsResponse>() {
            @Override
            public void onResponse(Call<NotificationDetailsResponse> call, Response<NotificationDetailsResponse> response) {
                if (response.isSuccessful()) {
                    container.setVisibility(View.VISIBLE);
                    play_stop.setOnClickListener(NotificationDetailsActivity.this::Play_Stop);
                    mAdapter.setImage(ImageList);
                    if (Objects.equals(LocaleManager.getLanguage(NotificationDetailsActivity.this), LANGUAGE_ARABIC)) {
                        setArData(response.body().getResponseContent().get(0));
                    } else {
                        setENData(response.body().getResponseContent().get(0));
                    }
                    //MAP
                    builder = new LatLngBounds.Builder();
                    LatLng latLng = new LatLng(Double.valueOf(response.body().getResponseContent().get(0).getLl()), Double.valueOf(response.body().getResponseContent().get(0).getLg()));
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.your_location))).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                    builder.include(latLng);
                    LatLngBounds bounds = builder.build();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 8));
                }
            }

            @Override
            public void onFailure(Call<NotificationDetailsResponse> call, Throwable t) {

            }
        });
    }

    private void Play_Stop(View view) {
        play();
    }

    private void setENData(ResponseContent responseContent) {
        if (responseContent.getIsClosed() != null) {
            if (responseContent.getIsClosed()) {
                status.setText(getString(R.string.finished_notification));
                rate.setVisibility(View.VISIBLE);
                rate.setOnClickListener(this::onRate);
            } else {
                status.setText(getString(R.string.ongoing_notification));
                rate.setVisibility(View.INVISIBLE);
            }
        } else {
            status.setText(getString(R.string.ongoing_notification));
            rate.setVisibility(View.INVISIBLE);
        }

        date.setText(simpleDateFormat.format(responseContent.getRequestDate()));
        name.setText(responseContent.getEstablishmentNameEN());
        licenceNo.setText(responseContent.getLicenseNumber());
        area.setText(responseContent.getAreaNameEN());
        type.setText(responseContent.getNotificationTypeEN());
        noti.setText(responseContent.getNotificationTypeDescriptionEN());
        details.setText(responseContent.getNotificationDetails());
    }

    private void onRate(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_rate);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ImageView sad = dialog.findViewById(R.id.happy);
        ImageView happy = dialog.findViewById(R.id.sad);
        sad.setOnClickListener(v -> {
            satisfied = "0";
            sad.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
            happy.setColorFilter(getResources().getColor(R.color.colorPrimary));
        });
        happy.setOnClickListener(v -> {
            satisfied = "1";
            happy.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
            sad.setColorFilter(getResources().getColor(R.color.colorPrimary));
        });

        EditText note = dialog.findViewById(R.id.note);
        cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> dialog.dismiss());

        send = dialog.findViewById(R.id.send);
        send.setOnClickListener(v -> {
            Call<ResponseBody> call = api.rate_notification("10", id, satisfied, note.getText().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        });
        dialog.show();
    }

    private void setArData(ResponseContent responseContent) {
        if (responseContent.getIsClosed() != null) {
            if (responseContent.getIsClosed()) {
                status.setText(getString(R.string.finished_notification));
                rate.setVisibility(View.VISIBLE);
            } else {
                status.setText(getString(R.string.ongoing_notification));
                rate.setVisibility(View.INVISIBLE);
            }
        } else {
            status.setText(getString(R.string.ongoing_notification));
            rate.setVisibility(View.INVISIBLE);
        }

        date.setText(simpleDateFormat.format(responseContent.getRequestDate()));
        name.setText(responseContent.getEstablishmentNameAR());
        licenceNo.setText(responseContent.getLicenseNumber());
        area.setText(responseContent.getAreaNameAR());
        type.setText(responseContent.getNotificationTypeAR());
        noti.setText(responseContent.getNotificationTypeDescriptionAR());
        details.setText(responseContent.getNotificationDetails());
    }

    private void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    public void onOpenCallback(Integer photo) {
        Dialog mDialog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setContentView(R.layout.fullscreen);
        ImageView image = mDialog.findViewById(R.id.image);
        Glide.with(image.getContext())
                .load(photo)
                .into(image);
        mDialog.show();
    }


    private void startUpdatingCallbackWithPosition() {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if (mSeekbarPositionUpdateTask == null) {
            mSeekbarPositionUpdateTask = () -> updateProgressCallbackTask();
        }
        mExecutor.scheduleAtFixedRate(
                mSeekbarPositionUpdateTask,
                0,
                PLAYBACK_POSITION_REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    // Reports media playback position to mPlaybackProgressCallback.
    private void stopUpdatingCallbackWithPosition() {
        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
            mSeekbarPositionUpdateTask = null;
        }
    }


    private void updateProgressCallbackTask() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
        }
    }

    public void stop() {
        if (mMediaPlayer != null) {
            stopUpdatingCallbackWithPosition();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void play() {
        stop();
        startUpdatingCallbackWithPosition();
        mMediaPlayer = MediaPlayer.create(this, R.raw.sound_test);
        mSeekBar.setMax(mMediaPlayer.getDuration());
        mMax.setText(String.format("%d:%d", mMediaPlayer.getDuration() % (1000 * 60 * 60) / (1000 * 60), mMediaPlayer.getDuration() % (1000 * 60 * 60) % (1000 * 60) / 1000));
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mMediaPlayer != null && fromUser) {
                    mMediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mMediaPlayer.setOnCompletionListener(mediaPlayer -> stop());

        mMediaPlayer.start();
    }

    public void onDestroy() {
        if (mMediaPlayer != null)
            mMediaPlayer.stop();
        super.onDestroy();
    }
}
