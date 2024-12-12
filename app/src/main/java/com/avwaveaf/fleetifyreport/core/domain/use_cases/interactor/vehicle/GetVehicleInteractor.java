package com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor.vehicle;

import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;
import com.avwaveaf.fleetifyreport.core.domain.repository.VehicleRepository;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.vehicle.GetVehicleUseCase;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetVehicleInteractor implements GetVehicleUseCase {
    private final VehicleRepository vehicleRepository;

    @Inject
    public GetVehicleInteractor(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Observable<Resource<List<Vehicle>>> execute() {
        return vehicleRepository.getVehicles()
                .subscribeOn(Schedulers.io());
    }
}
