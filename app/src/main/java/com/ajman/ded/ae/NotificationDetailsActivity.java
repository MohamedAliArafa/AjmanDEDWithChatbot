package com.ajman.ded.ae;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ajman.ded.ae.data.Api;
import com.ajman.ded.ae.data.ApiBuilder;
import com.ajman.ded.ae.libs.LocaleManager;
import com.ajman.ded.ae.models.notification.details.NotificationDetailsResponse;
import com.ajman.ded.ae.models.notification.details.ResponseContent;
import com.ajman.ded.ae.models.notification.files.FilesRsponse;
import com.ajman.ded.ae.utility.CustomMapView;
import com.ajman.ded.ae.utility.SharedTool.UserData;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
    private String BASE_URL = "http://site1.ajmanded.ae/apis/GetFileById?id=";
    private Api api;
    private EyeDetailsImagesAdapter mAdapter;
    private SimpleDateFormat simpleDateFormat;
    private MediaPlayer mMediaPlayer;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;
    private GoogleMap googleMap;
    private LatLngBounds.Builder builder;
    private String id;
    private String satisfied = "1";
    private Button cancel, send;
    private List<Bitmap> images;

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
        mMediaPlayer = new MediaPlayer();

        if (map != null) {
            map.onCreate(null);
            map.onResume();
            MapsInitializer.initialize(this);
            map.getMapAsync(this::onMapReady);
        }

        mAdapter = new EyeDetailsImagesAdapter(this, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        recyclerView.setAdapter(mAdapter);

        id = getIntent().getStringExtra("id");

        api = ApiBuilder.basicApi();

    }

    private void getFiles() {
        images = new ArrayList<>();
        Call<FilesRsponse> callFiles = api.files_notification(id);
        callFiles.enqueue(new Callback<FilesRsponse>() {
            @Override
            public void onResponse(Call<FilesRsponse> call, Response<FilesRsponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponseCode() == 1) {
                        for (com.ajman.ded.ae.models.notification.files.ResponseContent link : response.body().getResponseContent()) {
                            if (getFileExtension(link.getName()).equals("jpg") || getFileExtension(link.getName()).equals("png") || getFileExtension(link.getName()).equals("JPEG")) {
                                getImage(link.getFileId());
                            } else {
                                getAudio(link.getFileId(), link.getName().substring(0, link.getName().lastIndexOf(".")), getFileExtension(link.getName()));
                            }
                        }
                        mAdapter.setImage(images);
                    }
                }
            }

            @Override
            public void onFailure(Call<FilesRsponse> call, Throwable t) {
                Log.d("here", t.toString());
            }
        });
    }

    private void getAudio(String id, String fileName, String type) {
        Call<ResponseBody> callFile = api.file_notification(id);
        callFile.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    prepareAudio(response.body().bytes(), fileName, type);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getImage(String id) {
        Call<ResponseBody> callFile = api.file_notification(id);
        callFile.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mAdapter.addImage(BitmapFactory.decodeStream(response.body().byteStream()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void prepareAudio(byte[] audioByteArray, String filename, String type) throws IOException {
        File tempAudio = File.createTempFile(filename, type, getCacheDir());
        tempAudio.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tempAudio);
        fos.write(audioByteArray);
        fos.close();
        FileInputStream fis = new FileInputStream(tempAudio);
        setPlayer(fis);
    }

    private void getDetails() {
        Call<NotificationDetailsResponse> call = api.details_notification(id);
        call.clone().enqueue(new Callback<NotificationDetailsResponse>() {
            @Override
            public void onResponse(Call<NotificationDetailsResponse> call, Response<NotificationDetailsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponseContent() != null && response.body().getResponseContent().size() > 0) {
                        container.setVisibility(View.VISIBLE);
                        play_stop.setOnClickListener(NotificationDetailsActivity.this::Play_Stop);
                        if (Objects.equals(LocaleManager.getLanguage(NotificationDetailsActivity.this), LANGUAGE_ARABIC)) {
                            setArData(response.body().getResponseContent().get(0));
                        } else {
                            setENData(response.body().getResponseContent().get(0));
                        }

                        builder = new LatLngBounds.Builder();
                        LatLng latLng = new LatLng(Double.valueOf(response.body().getResponseContent().get(0).getLl()), Double.valueOf(response.body().getResponseContent().get(0).getLg()));
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.your_location))).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location));
                        builder.include(latLng);
                        LatLngBounds bounds = builder.build();
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 8));
                        googleMap.moveCamera(CameraUpdateFactory.zoomTo(14));

                    }
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

        if (responseContent.getRequestDate() != null)
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
        ImageView sad = dialog.findViewById(R.id.sad);
        ImageView happy = dialog.findViewById(R.id.happy);
        EditText note = dialog.findViewById(R.id.note);
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

        cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> dialog.dismiss());

        send = dialog.findViewById(R.id.send);
        send.setOnClickListener(v -> {
            String notes = note.getText().toString();
            Call<ResponseBody> call = api.rate_notification(UserData.getUserObject(NotificationDetailsActivity.this).getUserId(), id, satisfied, notes);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        rate.setVisibility(View.GONE);
                        Toast.makeText(NotificationDetailsActivity.this, getString(R.string.evaluation), Toast.LENGTH_SHORT).show();
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
                result.setText(getString(R.string.Notification_Result_Value_Solved));
                status.setText(getString(R.string.finished_notification));
                rate.setVisibility(View.VISIBLE);
                rate.setOnClickListener(this::onRate);
            } else {
                status.setText(getString(R.string.ongoing_notification));
                result.setText(R.string.Notification_Result_Value_Not_Solved);
                rate.setVisibility(View.INVISIBLE);
            }
        } else {
            result.setText(R.string.Notification_Result_Value_Not_Solved);
            status.setText(getString(R.string.ongoing_notification));
            rate.setVisibility(View.INVISIBLE);
        }

        if (responseContent.getRequestDate() != null)
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
        getDetails();

        getFiles();
    }

    @Override
    public void onOpenCallback(Bitmap photo) {
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
            mSeekbarPositionUpdateTask = () -> NotificationDetailsActivity.this.updateProgressCallbackTask();
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
            mMediaPlayer.pause();
        }
    }

    public void play() {
        if (mMediaPlayer.isPlaying()) {
            stop();
        } else {
            startUpdatingCallbackWithPosition();
            mMediaPlayer.start();
        }
    }

    private void setPlayer(FileInputStream audio) throws IOException {
        mMediaPlayer.reset();
        mMediaPlayer.setDataSource(audio.getFD());
        mMediaPlayer.prepare();
        mSeekBar.setMax(mMediaPlayer.getDuration());
        mMax.setText(String.format("%d:%d", mMediaPlayer.getDuration() % (1000 * 60 * 60) / (1000 * 60), mMediaPlayer.getDuration() % (1000 * 60 * 60) % (1000 * 60) / 1000, Locale.US));
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
    }

    public String getFileExtension(String url) {

        Objects.requireNonNull(url, "url is null");

        final String file = url;

        if (file.contains(".")) {
            final String sub = file.substring(file.lastIndexOf('.') + 1);

            if (sub.length() == 0) {
                return "";
            }

            if (sub.contains("?")) {
                return sub.substring(0, sub.indexOf('?'));
            }

            return sub;
        }

        return "";
    }

    public void onDestroy() {
        if (mMediaPlayer != null)
            mMediaPlayer.stop();
        super.onDestroy();
    }
}
