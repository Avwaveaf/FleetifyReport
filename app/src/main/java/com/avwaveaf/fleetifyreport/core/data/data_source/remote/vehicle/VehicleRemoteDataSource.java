package com.avwaveaf.fleetifyreport.core.data.data_source.remote.vehicle;

import com.avwaveaf.fleetifyreport.core.data.entity.VehicleDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface VehicleRemoteDataSource {
    Observable<List<VehicleDTO>> fetchVehicles();
}
