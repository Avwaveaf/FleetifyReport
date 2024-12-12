package com.avwaveaf.fleetifyreport.take_picture;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.avwaveaf.fleetifyreport.R;
import com.avwaveaf.fleetifyreport.databinding.ActivityCameraBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;

public class CameraActivity extends AppCompatActivity {
    public static final String CAMERA_RESULT_EXTRA = "capturedImageUri";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 102;
    private ActivityCameraBinding binding;
    private CameraViewModel viewModel;
    private ImageCapture imageCapture;
    private ProcessCameraProvider cameraProvider;
    private boolean isUsingBackCamera = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(CameraViewModel.class);

        // Request camera permissions
        if (checkPermission()) {
            setupCamera();
        } else {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }

        binding.btnCapture.setOnClickListener(v -> capturePhoto());
        binding.btnSwap.setOnClickListener(v -> swapCamera());

        viewModel.getCapturedImageUri().observe(this, uri -> {
            Intent intent = new Intent();
            intent.putExtra(CAMERA_RESULT_EXTRA, uri.toString());
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void swapCamera() {
        if (cameraProvider == null) {
            Toast.makeText(this, "Camera not initialized", Toast.LENGTH_SHORT).show();
            return;
        }

        // Toggle the camera state
        isUsingBackCamera = !isUsingBackCamera;

        // Unbind all use cases and rebind with the new camera selector
        cameraProvider.unbindAll();
        bindCameraUseCases();
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void setupCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();

                bindCameraUseCases();
            } catch (Exception e) {
                Snackbar.make(binding.getRoot(), "Error setting up camera", Snackbar.LENGTH_LONG).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindCameraUseCases() {
        if (cameraProvider == null) return;

        try {
            Preview preview = new Preview.Builder().build();
            imageCapture = new ImageCapture.Builder().build();

            // Select the camera based on the current state
            CameraSelector cameraSelector = isUsingBackCamera
                    ? CameraSelector.DEFAULT_BACK_CAMERA
                    : CameraSelector.DEFAULT_FRONT_CAMERA;

            // Bind use cases to the camera
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

            // Attach preview to the SurfaceProvider
            preview.setSurfaceProvider(binding.viewFinder.getSurfaceProvider());
        } catch (Exception e) {
            Snackbar.make(binding.getRoot(), "Error binding camera use cases", Snackbar.LENGTH_LONG).show();
        }
    }

    private void capturePhoto() {
        File photoFile = new File(getExternalFilesDir(null), "photo_" + System.currentTimeMillis() + ".jpg");
        ImageCapture.OutputFileOptions options = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(options, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                viewModel.onImageCaptured(Uri.fromFile(photoFile));
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Snackbar.make(binding.getRoot(), getString(R.string.gagal_mengambil_foto), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupCamera();
            } else {
                finish();
            }
        }
    }
}
