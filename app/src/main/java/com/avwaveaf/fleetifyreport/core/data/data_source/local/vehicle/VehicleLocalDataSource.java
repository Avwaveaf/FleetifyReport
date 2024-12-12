package com.avwaveaf.fleetifyreport.core.data.data_source.local.vehicle;

import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface VehicleLocalDataSource {
    void saveVehicles(List<Vehicle> vehicles);

    Observable<List<Vehicle>> getCachedVehicles();

    Single<Long> getVehicleCount();
}
