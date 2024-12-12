package com.avwaveaf.fleetifyreport.core.data.data_source_impl.remote.vehicle;

import com.avwaveaf.fleetifyreport.core.data.api_services.VehicleApiService;
import com.avwaveaf.fleetifyreport.core.data.data_source.remote.vehicle.VehicleRemoteDataSource;
import com.avwaveaf.fleetifyreport.core.data.entity.VehicleDTO;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class VehicleRemoteDataSourceImpl implements VehicleRemoteDataSource {
    private final VehicleApiService vehicleApiService;

    @Inject
    public VehicleRemoteDataSourceImpl(VehicleApiService vehicleApiService) {
        this.vehicleApiService = vehicleApiService;
    }

    @Override
    public Observable<List<VehicleDTO>> fetchVehicles() {
        return vehicleApiService.getVehicleList()
                .toObservable();

    }
}
