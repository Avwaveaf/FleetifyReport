package com.avwaveaf.fleetifyreport.core.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;


@Getter
public class ReportDTO {

    @SerializedName("note")
    private String note;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("vehicleName")
    private String vehicleName;

    @SerializedName("reportId")
    private String reportId;

    @SerializedName("vehicleLicenseNumber")
    private String vehicleLicenseNumber;

    @SerializedName("createdBy")
    private String createdBy;

    @SerializedName("photo")
    private String photo;

    @SerializedName("reportStatus")
    private String reportStatus;

    @SerializedName("vehicleId")
    private String vehicleId;

}