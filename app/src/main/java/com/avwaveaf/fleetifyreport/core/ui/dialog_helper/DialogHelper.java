package com.avwaveaf.fleetifyreport.core.ui.dialog_helper;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.avwaveaf.fleetifyreport.R;
import com.bumptech.glide.Glide;

public class DialogHelper {
    public static void showImagePreview(String imageUrl, Context context) {
        // Inflate the custom layout
        @SuppressLint("InflateParams")
        View customView = LayoutInflater.from(context).inflate(R.layout.dialog_image_preview, null);

        ImageView imageView = customView.findViewById(R.id.iv_image_preview_container);
        ImageButton closeIcon = customView.findViewById(R.id.iv_close_icon);

        // Use Glide to load the image
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_error)
                .into(imageView);

        // Create a dialog with a full-screen theme
        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(customView);

        // Close dialog on button click
        closeIcon.setOnClickListener(v -> dialog.dismiss());

        // Show the dialog
        dialog.show();
    }
}
