package com.avwaveaf.fleetifyreport.core.data.repository;

import com.avwaveaf.fleetifyreport.core.data.data_source.local.vehicle.VehicleLocalDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source.remote.vehicle.VehicleRemoteDataSource;
import com.avwaveaf.fleetifyreport.core.data.entity.VehicleDTO;
import com.avwaveaf.fleetifyreport.core.data.mapper.VehicleMapper;
import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;
import com.avwaveaf.fleetifyreport.core.domain.repository.VehicleRepository;
import com.avwaveaf.fleetifyreport.core.utils.NetworkBoundResource;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class VehicleRepositoryImpl implements VehicleRepository {

    private final VehicleRemoteDataSource vehicleRemoteDataSource;
    private final VehicleLocalDataSource vehicleLocalDataSource;


    @Inject
    public VehicleRepositoryImpl(VehicleRemoteDataSource vehicleRemoteDataSource, VehicleLocalDataSource vehicleLocalDataSource) {
        this.vehicleRemoteDataSource = vehicleRemoteDataSource;
        this.vehicleLocalDataSource = vehicleLocalDataSource;
    }


    @Override
    public Observable<Resource<List<Vehicle>>> getVehicles() {
        return new NetworkBoundResource<List<Vehicle>, Resource<List<VehicleDTO>>>() {

            @Override
            protected Observable<Resource<List<VehicleDTO>>> createCall() {
                return vehicleRemoteDataSource.fetchVehicles();
            }

            @Override
            protected List<Vehicle> processResponse(Resource<List<VehicleDTO>> response) {
                if (response.getData().isEmpty()) {
                    return new ArrayList<>();
                }
                return VehicleMapper.toDomainList(response.getData());
            }

            @Override
            protected void saveCallResult(List<Vehicle> items) {
                vehicleLocalDataSource.saveVehicles(items);
            }


            @Override
            protected Observable<List<Vehicle>> loadFromCache() {
                return vehicleLocalDataSource.getCachedVehicles();
            }
        }.asObservable();
    }
}
