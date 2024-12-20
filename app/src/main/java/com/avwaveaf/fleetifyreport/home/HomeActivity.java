package com.avwaveaf.fleetifyreport.home;

import android.content.Intent;
import android.os.Bundle;
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
import com.avwaveaf.fleetifyreport.core.utils.ConnectivityUtil;
import com.avwaveaf.fleetifyreport.core.utils.Resource;
import com.avwaveaf.fleetifyreport.databinding.ActivityHomeBinding;
import com.avwaveaf.fleetifyreport.new_report.NewReportDialogFragment;
import com.avwaveaf.fleetifyreport.search.SearchActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {


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
        setupRefreshListener();

    }

    private void setupRefreshListener() {
        binding.srlRefresh.setOnRefreshListener(() -> {
            if (ConnectivityUtil.isNetworkAvailable(this)) {
                viewModel.refreshReports();
            } else {
                binding.srlRefresh.setRefreshing(false);
                Snackbar.make(binding.getRoot(), "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
        });
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
                    // Always stop refreshing on success
                    binding.srlRefresh.setRefreshing(false);

                    if (!state.getData().isEmpty()) {
                        reportAdapter.submitList(state.getData());
                        showEmptyStateOfData(false);
                    } else {
                        showEmptyStateOfData(true);
                    }
                    showLoading(false);
                    break;
                case ERROR:
                    binding.srlRefresh.setRefreshing(false);
                    showEmptyStateOfData(true);
                    Snackbar.make(binding.getRoot(), state.getMessage(), Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.coba_lagi_text_snackbar, v -> viewModel.loadAllReports())
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

    public void onSearchButtonClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
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


}