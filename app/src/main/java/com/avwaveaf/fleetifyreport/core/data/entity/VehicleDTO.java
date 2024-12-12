package com.avwaveaf.fleetifyreport.core.data.entity;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

@Getter
public class VehicleDTO {
    @SerializedName("licenseNumber")
    private String licenseNumber;

    @SerializedName("vehicleId")
    private String vehicleId;

    @SerializedName("type")
    private String type;
}