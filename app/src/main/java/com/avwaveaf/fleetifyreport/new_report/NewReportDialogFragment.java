package com.avwaveaf.fleetifyreport.new_report;

import static android.app.Activity.RESULT_OK;
import static com.avwaveaf.fleetifyreport.R.string.permission_denied_please_grant_the_permission_to_proceed;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.avwaveaf.fleetifyreport.R;
import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;
import com.avwaveaf.fleetifyreport.core.ui.adapters.VehicleSpinnerAdapter;
import com.avwaveaf.fleetifyreport.core.utils.DateTimeUtil;
import com.avwaveaf.fleetifyreport.databinding.CreateNewReportDialogBinding;
import com.avwaveaf.fleetifyreport.take_picture.CameraActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewReportDialogFragment extends DialogFragment {
    public static final String NEW_REPORT_DIALOG_FRAGMENT_TAG = "NewReportDialogFragment";

    private static final long DEBOUNCE_DELAY = 300;
    private final Handler debounceHandler = new Handler();
    private Runnable debounceRunnable;

    private CreateNewReportDialogBinding binding;

    private VehicleViewModel vehicleViewModel;
    private Uri selectedImageUri;
    private final ActivityResultLauncher<Intent> cameraResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri imageUri;
                    if (result.getData() != null) {
                        imageUri = Uri.parse(result.getData().getStringExtra(CameraActivity.CAMERA_RESULT_EXTRA));
                        selectedImageUri = imageUri;
                        binding.ivNoteImage.setImageURI(imageUri);
                        validateForm();
                    }
                }
            });
    private final ActivityResultLauncher<Intent> filePickerResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    binding.ivNoteImage.setImageURI(selectedImageUri);
                    validateForm();
                }

            });

    private final ActivityResultLauncher<String> permissionRequestLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    if (isCameraPermission()) {
                        openCamera();
                    } else if (isStoragePermission()) {
                        openFileExplorer();
                    }
                } else {
                    showPermissionDeniedSnackbar();
                }
            });

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.TransparentDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreateNewReportDialogBinding.inflate(inflater, container, false);
        vehicleViewModel = new ViewModelProvider(this).get(VehicleViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupInitialUI();
        setupObservers();
        vehicleViewModel.fetchVehicles();
    }

    private void setupObservers() {
        setupVehicleObserver();
        setupSubmitReportObserver();
    }

    private void setupSubmitReportObserver() {
    }

    private void setupVehicleObserver() {
        vehicleViewModel.vehicleList.observe(getViewLifecycleOwner(), vehicles -> {
            if (!vehicles.isEmpty()) {
                setupVehicleSpinner(vehicles);
                handleLoadingSpinner(false);
            }
        });

        vehicleViewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> handleLoadingSpinner(true));

        vehicleViewModel.errorMessage.observe(getViewLifecycleOwner(), errorMsg -> {
            handleLoadingSpinner(true);
            if (errorMsg != null) {
                showErrorSnackbar(errorMsg);
            }
        });
    }

    private void handleLoadingSpinner(boolean isLoading) {
        if (isLoading) {
            binding.simmerLoadSpinner.startShimmer();
            binding.simmerLoadSpinner.setVisibility(View.VISIBLE);
            binding.vehicleSpinner.setVisibility(View.INVISIBLE);
        } else {
            binding.simmerLoadSpinner.stopShimmer();
            binding.simmerLoadSpinner.setVisibility(View.GONE);
            binding.vehicleSpinner.setVisibility(View.VISIBLE);
        }
    }

    private void setupVehicleSpinner(List<Vehicle> vehicles) {
        VehicleSpinnerAdapter adapter = new VehicleSpinnerAdapter(requireContext(), vehicles);
        binding.vehicleSpinner.setAdapter(adapter);
    }

    private void showErrorSnackbar(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", v -> vehicleViewModel.fetchVehicles())
                .show();
    }

    private void setupInitialUI() {
        binding.tvReportDate.setText(DateTimeUtil.getCurrentTimeFormatted());
        binding.btnIvCloseDialog.setOnClickListener(v -> dismiss());
        binding.edtNoteReport.addTextChangedListener(getNoteTextWatcher());
        binding.btnSendReport.setOnClickListener(v-> handleReportSubmission());
        binding.btnTakePhoto.setOnClickListener(v -> showImageSourceDialog());
    }

    private @NonNull TextWatcher getNoteTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Cancel any pending callbacks
                if (debounceRunnable != null) {
                    debounceHandler.removeCallbacks(debounceRunnable);
                }

                // Post a new callback with the debounce delay
                debounceRunnable = () -> validateForm();
                debounceHandler.postDelayed(debounceRunnable, DEBOUNCE_DELAY);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    private void handleReportSubmission() {
        if (validateForm()) {
            Toast.makeText(requireActivity(), "fdsf", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean isNoteEmpty(){
        return TextUtils.isEmpty(binding.edtNoteReport.getText().toString().trim());
    }

    private Boolean isNoteAttachmentExist(){
        return selectedImageUri != null;
    }

    private Boolean validateForm() {
        boolean isNoteValid = !isNoteEmpty();
        boolean isImageValid = isNoteAttachmentExist();

        // Update error visibility
        binding.tvNoteError.setVisibility(isNoteValid ? View.GONE : View.VISIBLE);
        binding.tvImageError.setVisibility(isImageValid ? View.GONE : View.VISIBLE);

        // Enable or disable the button
        boolean isFormValid = isNoteValid && isImageValid;
        binding.btnSendReport.setEnabled(isFormValid);

        // Debug logs
        Log.d("FormValidation", "isNoteValid: " + isNoteValid + ", isImageValid: " + isImageValid);
        Log.d("FormValidation", "Submit Button Enabled: " + isFormValid);

        return isFormValid;
    }



    private void showImageSourceDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.pilih_sumber_ambil_gambar_prompt)
                .setItems(new String[]{getString(R.string.take_photo_prompt), getString(R.string.choose_from_gallery_prompt)}, (dialog, which) -> {
                    if (which == 0) {
                        // Check camera permission before opening the camera
                        if (checkPermission(Manifest.permission.CAMERA)) {
                            openCamera();
                        }
                    } else {
                        // Check storage permission before opening the file explorer
                        if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            openFileExplorer();
                        }
                    }
                })
                .show();
    }


    private void openCamera() {
        Intent cameraIntent = new Intent(requireContext(), CameraActivity.class);
        cameraResultLauncher.launch(cameraIntent);
    }

    @SuppressLint("IntentReset")
    private void openFileExplorer() {
        Intent fileIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fileIntent.setType("image/*");
        filePickerResultLauncher.launch(fileIntent);
    }

    private boolean isCameraPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isStoragePermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission)) {
                showPermissionRationale(permission);
            } else {
                permissionRequestLauncher.launch(permission);
            }
            return false;
        }
        return true;
    }

    private void showPermissionDeniedSnackbar() {
        Snackbar.make(binding.getRoot(), permission_denied_please_grant_the_permission_to_proceed, Snackbar.LENGTH_LONG)
                .show();
    }

    private void showPermissionRationale(String permission) {
        String message = getString(R.string.this_permission_is_required_to_select_an_image);
        new AlertDialog.Builder(requireContext())
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> permissionRequestLauncher.launch(permission))
                .setNegativeButton("Batal", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
