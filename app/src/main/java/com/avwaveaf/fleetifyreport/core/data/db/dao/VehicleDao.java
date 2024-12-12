package com.avwaveaf.fleetifyreport.core.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface VehicleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVehicles(List<Vehicle> vehicles);

    @Query("SELECT * FROM vehicles")
    Observable<List<Vehicle>> getCachedVehicles();

    @Query("SELECT COUNT(*) FROM vehicles")  // Adjust table name if needed
    Single<Long> getVehicleCount();
}
