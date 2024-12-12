package com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.vehicle;

import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface GetVehicleUseCase {
    Observable<Resource<List<Vehicle>>> execute();
}
