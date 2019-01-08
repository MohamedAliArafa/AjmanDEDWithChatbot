package com.ajman.ded.ae.audio_recorder;

public interface AudioListener {

    boolean isPermissionGranted();

    void onStop(RecordingItem recordingItem);

    void onCancel();

    void onError(Exception e);
}
