package com.ajman.ded.ae.audio_recorder;

public interface AudioListener {

    void onStop(RecordingItem recordingItem);

    void onCancel();

    void onError(Exception e);
}
