package com.avwaveaf.fleetifyreport.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.avwaveaf.fleetifyreport.R;
import com.avwaveaf.fleetifyreport.core.domain.entity.Report;
import com.avwaveaf.fleetifyreport.core.ui.adapters.ReportAdapter;
import com.avwaveaf.fleetifyreport.core.utils.Resource;
import com.avwaveaf.fleetifyreport.databinding.ActivityHomeBinding;
import com.avwaveaf.fleetifyreport.new_report.NewReportDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {

    private static final long DEBOUNCE_DELAY = 1600;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;

    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;
    private ReportAdapter reportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupWindowInsets();
        setupViewModel();
        observeViewModel();
        setupBtnCrateReportListener();
        setupRecyclerView();
        setupSearchListener();
    }

    private void setupRecyclerView() {
        reportAdapter = new ReportAdapter(this);
        binding.rvReports.setLayoutManager(new LinearLayoutManager(this));
        binding.rvReports.setAdapter(reportAdapter);
    }

    private void setupBtnCrateReportListener() {
        binding.btnNewReport.setOnClickListener(v -> showCreateNewReportDialog());
        binding.btnSearch.setOnClickListener(this::onSearchButtonClick);
    }

    private void showCreateNewReportDialog() {
        NewReportDialogFragment dialog = new NewReportDialogFragment();
        dialog.show(getSupportFragmentManager(), NewReportDialogFragment.NEW_REPORT_DIALOG_FRAGMENT_TAG);
    }

    private void observeViewModel() {
        viewModel.getProfileLiveData().observe(this, state -> binding.tvFullName.setText(state.getFullName()));
        viewModel.reportState.observe(this, this::handleReportState);
    }

    private void handleReportState(Resource<List<Report>> state) {
        if (state != null) {
            switch (state.getStatus()) {
                case SUCCESS:
                    reportAdapter.submitList(state.getData());
                    showLoading(false);
                    break;
                case ERROR:
                    Snackbar.make(binding.getRoot(), state.getMessage(), Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.coba_lagi_text_snackbar, v -> viewModel.loadAllReports())
                            .show();
                    break;
                case LOADING:
                    showLoading(true);
                    break;
            }
        }
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            binding.rvReports.setVisibility(View.INVISIBLE);
            binding.shimmerLoading.setVisibility(View.VISIBLE);
            binding.shimmerLoading.startShimmer();
        } else {
            binding.rvReports.setVisibility(View.VISIBLE);
            binding.shimmerLoading.setVisibility(View.GONE);
            binding.shimmerLoading.stopShimmer();
        }
    }

    public void onSearchButtonClick(View view) {
        if (binding.edtSearchReport.getVisibility() == View.GONE) {
            // Show the EditText with fade-in animation
            fadeInEditText();
        } else {
            // Hide the EditText with fade-out animation
            fadeOutEditText();
        }
    }

    private void fadeInEditText() {
        binding.edtSearchReport.setVisibility(View.VISIBLE);
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(300);
        binding.edtSearchReport.startAnimation(fadeIn);
        binding.edtSearchReport.setAlpha(1f);
    }

    private void fadeOutEditText() {
        AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
        fadeOut.setDuration(300);
        binding.edtSearchReport.startAnimation(fadeOut);
        binding.edtSearchReport.setAlpha(0f);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.edtSearchReport.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }


    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void setupSearchListener() {
        binding.edtSearchReport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                handler.removeCallbacks(searchRunnable);
                searchRunnable = () -> {
                    String query = String.valueOf(editable);
                    viewModel.loadAllReports(query);
                };

                handler.postDelayed(searchRunnable, DEBOUNCE_DELAY);
            }
        });
    }
}