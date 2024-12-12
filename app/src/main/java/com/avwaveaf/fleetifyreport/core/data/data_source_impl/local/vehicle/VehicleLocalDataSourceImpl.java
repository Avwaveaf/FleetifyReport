package com.avwaveaf.fleetifyreport.core.data.data_source_impl.local.vehicle;

import com.avwaveaf.fleetifyreport.core.data.data_source.local.vehicle.VehicleLocalDataSource;
import com.avwaveaf.fleetifyreport.core.data.db.dao.VehicleDao;
import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class VehicleLocalDataSourceImpl implements VehicleLocalDataSource {
    private final VehicleDao vehicleDao;

    @Inject
    public VehicleLocalDataSourceImpl(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public void saveVehicles(List<Vehicle> vehicles) {
        vehicleDao.insertVehicles(vehicles);
    }

    @Override
    public Observable<List<Vehicle>> getCachedVehicles() {
        return vehicleDao.getCachedVehicles();
    }

    @Override
    public Single<Long> getVehicleCount() {
        return vehicleDao.getVehicleCount();
    }
}
