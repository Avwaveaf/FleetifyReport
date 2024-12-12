package com.avwaveaf.fleetifyreport.core.domain.repository;

import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface VehicleRepository {
    Observable<Resource<List<Vehicle>>> getVehicles();
}
