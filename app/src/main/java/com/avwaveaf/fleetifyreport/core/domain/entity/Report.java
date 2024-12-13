package com.avwaveaf.fleetifyreport.core.domain.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;


@Getter
@Entity(tableName = "reports")
public class Report {

    @NonNull
    private final String note;
    @NonNull
    private final String createdAt;
    @NonNull
    private final String vehicleName;
    @NonNull
    private final String vehicleLicenseNumber;
    @NonNull
    private final String createdBy;
    @NonNull
    private final String photo;
    @NonNull
    private final String reportStatus;
    @NonNull
    private final String vehicleId;
    @PrimaryKey
    @NonNull
    private String reportId;

    public Report(@NonNull String reportId, @NonNull String note, @NonNull String createdAt, @NonNull String vehicleName, @NonNull String vehicleLicenseNumber, @NonNull String createdBy, @NonNull String photo, @NonNull String reportStatus, @NonNull String vehicleId) {
        this.reportId = reportId;
        this.note = note;
        this.createdAt = createdAt;
        this.vehicleName = vehicleName;
        this.vehicleLicenseNumber = vehicleLicenseNumber;
        this.createdBy = createdBy;
        this.photo = photo;
        this.reportStatus = reportStatus;
        this.vehicleId = vehicleId;
    }
}