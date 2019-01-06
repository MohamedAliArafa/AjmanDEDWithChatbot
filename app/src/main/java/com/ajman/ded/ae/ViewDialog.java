package com.ajman.ded.ae;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;

import java.io.File;
import java.io.IOException;

import static com.ajman.ded.ae.screens.complaints.SubmitActivity.OPEN_CAMERA;
import static com.ajman.ded.ae.screens.complaints.SubmitActivity.OPEN_GALLERY;

public class ViewDialog {

    public void showChooseDialog(Activity activity, Uri uri) {
        Dialog dialog = new Dialog(activity, R.style.Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.item_choose);

        View camera = dialog.findViewById(R.id.camera);

        View gallery = dialog.findViewById(R.id.gallery);
        gallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setType("image/*");
            activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), OPEN_GALLERY);
            dialog.dismiss();
        });

        camera.setOnClickListener(v -> {
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(takePicture, OPEN_CAMERA);
            dialog.dismiss();
        });

        dialog.show();
    }
}