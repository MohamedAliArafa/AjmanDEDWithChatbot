package com.ajman.ded.ae;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import static com.ajman.ded.ae.screens.ded_eye.NewNotificationActivity.OPEN_CAMERA;
import static com.ajman.ded.ae.screens.ded_eye.NewNotificationActivity.OPEN_GALLERY;

public class ViewDialog {

    public void showChooseDialog(Activity activity, Uri uri) {
        Dialog dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.item_choose);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.4f);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
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


    public Dialog showShakeDialog(Activity activity) {
        Dialog dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.item_shake);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.4f);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        View closeImage = dialog.findViewById(R.id.close_image);
        View closeButton = dialog.findViewById(R.id.close_btn);
        closeButton.setOnClickListener(v -> dialog.dismiss());

        closeImage.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        return dialog;
    }

    public Dialog showNewFeatureDialog(Activity activity) {
        Dialog dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.item_feature);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.4f);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        View closeImage = dialog.findViewById(R.id.close_image);
        View closeButton = dialog.findViewById(R.id.close_btn);

        closeButton.setOnClickListener(v -> dialog.dismiss());

        closeImage.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        return dialog;
    }


    public void showRateDialog(Activity activity, Uri uri) {
        Dialog dialog = new Dialog(activity, R.style.DialogStyle);
        dialog.setContentView(R.layout.item_choose);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.4f);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

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