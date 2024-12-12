package com.avwaveaf.fleetifyreport.core.domain.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;

@Getter
@Entity(tableName = "vehicles")
public class Vehicle {

    @PrimaryKey
    @NonNull  // Ensure the primary key is non-null
    private final String id;

    private final String licenseNumber;
    private final String type;

    // Constructor for Room
    public Vehicle(@NonNull String id, String licenseNumber, String type) {
        this.id = id;
        this.licenseNumber = licenseNumber;
        this.type = type;
    }
}
