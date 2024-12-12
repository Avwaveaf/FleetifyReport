package com.avwaveaf.fleetifyreport.core.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.avwaveaf.fleetifyreport.core.data.db.dao.VehicleDao;
import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;

@Database(entities = {Vehicle.class}, version = 1, exportSchema = false)
public abstract class FleetifyDatabase extends RoomDatabase {
    public abstract VehicleDao vehicleDao();
}
