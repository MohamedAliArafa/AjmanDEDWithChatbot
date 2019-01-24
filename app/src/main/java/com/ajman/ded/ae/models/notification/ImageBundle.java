package com.ajman.ded.ae.models.notification;

import android.net.Uri;

public class ImageBundle {
    Uri ImageUri;
    String ImageAbsolutePath;

    public Uri getImageUri() {
        return ImageUri;
    }

    public void setImageUri(Uri imageUri) {
        ImageUri = imageUri;
    }

    public String getImageAbsolutePath() {
        return ImageAbsolutePath;
    }

    public void setImageAbsolutePath(String imageAbsolutePath) {
        ImageAbsolutePath = imageAbsolutePath;
    }
}
