package com.avwaveaf.fleetifyreport.search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

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
import com.avwaveaf.fleetifyreport.databinding.ActivitySearchBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchActivity extends AppCompatActivity {

    private static final long DEBOUNCE_DELAY = 1200;

    private Handler handler;
    private Runnable searchRunnable;

    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;

    private ReportAdapter reportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupWindowInsets();
        setupViewModel();
        observeViewModel();
        setupClearButton();
        setupAdapter();
        setupSearchListener();
        setupBackButton();
    }

    private void setupBackButton() {
        binding.ivBack.setOnClickListener(view -> SearchActivity.this.finish());
    }

    private void setupAdapter() {
        reportAdapter = new ReportAdapter(this);
        binding.rvReports.setLayoutManager(new LinearLayoutManager(this));
        binding.rvReports.setAdapter(reportAdapter);
    }

    private void observeViewModel() {

        viewModel.reportState.observe(this, this::handleReportState);

    }

    private void handleReportState(Resource<List<Report>> state) {
        if (state != null) {
            switch (state.getStatus()) {
                case SUCCESS:
                    if (!state.getData().isEmpty()) {
                        reportAdapter.submitList(state.getData());
                        showEmptyStateOfData(false);
                    } else {
                        showEmptyStateOfData(true);
                    }
                    showLoading(false);
                    break;
                case ERROR:
                    showEmptyStateOfData(true);
                    Snackbar.make(binding.getRoot(), state.getMessage(), Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.coba_lagi_text_snackbar, v -> viewModel.loadAllReports(""))
                            .show();
                    break;
                case LOADING:

                    showEmptyStateOfData(false);
                    showLoading(true);
                    break;
            }
        }
    }

    private void showEmptyStateOfData(boolean isVisible) {
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        binding.emptyStateLayout.setVisibility(visibility);
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

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
    }

    private void setupClearButton() {
        binding.btnClearSearch.setOnClickListener(l -> {
            binding.edtSearchReport.setText("");
            viewModel.loadAllReports(""); // Load all reports
        });
    }

    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupSearchListener() {
        handler = new Handler(Looper.getMainLooper());
        TextWatcher searchTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    binding.btnClearSearch.setVisibility(View.VISIBLE);
                    handler.removeCallbacks(searchRunnable);

                    searchRunnable = () -> {
                        String query = editable.toString();
                        viewModel.loadAllReports(query);
                    };

                    // Trigger search after a delay (debounce)
                    handler.postDelayed(searchRunnable, DEBOUNCE_DELAY);
                } else {
                    binding.btnClearSearch.setVisibility(View.GONE);
                    handler.removeCallbacks(searchRunnable);
                }
            }
        };

        binding.edtSearchReport.addTextChangedListener(searchTextWatcher);
    }
}