package com.ajman.ded.ae.utility.pdfViewer.asset;

import android.content.Context;

import com.ajman.ded.ae.utility.pdfViewer.service.CopyAssetService;


public class CopyAssetServiceImpl implements CopyAsset {
    private Context context;

    public CopyAssetServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void copy(String assetName, String destinationPath) {
        CopyAssetService.startCopyAction(context, assetName, destinationPath);
    }
}
