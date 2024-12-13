package com.avwaveaf.fleetifyreport.core.ui.adapters;

import static com.avwaveaf.fleetifyreport.core.ui.dialog_helper.DialogHelper.showImagePreview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avwaveaf.fleetifyreport.R;
import com.avwaveaf.fleetifyreport.core.domain.entity.Report;
import com.avwaveaf.fleetifyreport.databinding.ReportListItemLayoutBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private final Context context;
    private final List<Report> reportList = new ArrayList<>();

    public ReportAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void submitList(List<Report> reports) {
        reportList.clear();
        reportList.addAll(reports);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReportListItemLayoutBinding binding = ReportListItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ReportViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        Report report = reportList.get(position);
        holder.bind(report);
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    class ReportViewHolder extends RecyclerView.ViewHolder {

        private final ReportListItemLayoutBinding binding;

        public ReportViewHolder(@NonNull ReportListItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Report report) {
            binding.tvReportTitle.setText(R.string.laporan_keluhan_card_title);
            binding.tvReportId.setText(report.getReportId());
            binding.tvReportDate.setText(report.getCreatedAt());
            binding.tvVehicleName.setText(report.getVehicleName());
            binding.tvVehicleNumber.setText(report.getVehicleLicenseNumber());
            binding.tvUserName.setText(report.getCreatedBy());
            binding.tvNote.setText(report.getNote());
            binding.tvStatusTitle.setText(report.getReportStatus());


            // Load image using Glide
            Glide.with(context)
                    .load(report.getPhoto())
                    .placeholder(R.drawable.ic_image_placeholder)
                    .error(R.drawable.ic_image_error)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivNoteImage);

            binding.ivNoteImage.setOnClickListener(v -> showImagePreview(report.getPhoto(), context));
        }


    }
}
