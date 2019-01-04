package com.ajman.ded.ae.audio_recorder;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.widget.AppCompatSeekBar;

public class AudioRecording {
    public static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 100;

    private String mFileName;
    private Context mContext;

    private MediaPlayer mMediaPlayer;
    private AudioListener audioListener;
    private MediaRecorder mRecorder;
    private long mStartingTimeMillis = 0;
    private long mElapsedMillis = 0;
    private AppCompatSeekBar mSeekBar;
    private Runnable mSeekbarPositionUpdateTask;

    private ScheduledExecutorService mExecutor;
    private TextView mMax;

    public AudioRecording(Context context) {
        mRecorder = new MediaRecorder();
        this.mContext = context;
        this.mMediaPlayer = new MediaPlayer();
    }

    public AudioRecording() {
        mRecorder = new MediaRecorder();
    }

    public AudioRecording setNameFile(String nameFile) {
        this.mFileName = nameFile;
        return this;
    }

    public AudioRecording start(AudioListener audioListener) {
        this.audioListener = audioListener;
        try {
            mRecorder.reset();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setOutputFile(mContext.getCacheDir() + mFileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            mRecorder.prepare();
            mRecorder.start();
            mStartingTimeMillis = System.currentTimeMillis();
        } catch (IOException e) {
            this.audioListener.onError(e);
        }
        return this;
    }

    public void setSeekBar(AppCompatSeekBar seekBar) {
        mSeekBar = seekBar;
    }

    public void setMax(TextView max) {
        mMax = max;
    }

    public void stop(Boolean cancel) {
        try {
            mRecorder.stop();
        } catch (RuntimeException e) {
            deleteOutput();
        }
        mRecorder.release();
        mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis);

        RecordingItem recordingItem = new RecordingItem();
        recordingItem.setFilePath(mContext.getCacheDir() + mFileName);
        recordingItem.setName(mFileName);
        recordingItem.setLength((int) mElapsedMillis);
        recordingItem.setTime(System.currentTimeMillis());

        if (cancel == false) {
            audioListener.onStop(recordingItem);
        } else {
            audioListener.onCancel();
        }
    }

    private void deleteOutput() {
        File file = new File(mContext.getCacheDir() + mFileName);
        if (file.exists()) {
            file.delete();
        }
    }

    public void prepare(RecordingItem recordingItem){
        try {
            mMediaPlayer.reset();
            this.mMediaPlayer.setDataSource(recordingItem.getFilePath());
            this.mMediaPlayer.prepare();
            this.mMediaPlayer.setOnCompletionListener(mp -> {
                mSeekBar.setProgress(0);
            });
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        startUpdatingCallbackWithPosition();
        this.mMediaPlayer.start();
    }

    public void pause() {
        stopUpdatingCallbackWithPosition();
        this.mMediaPlayer.pause();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
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
}
