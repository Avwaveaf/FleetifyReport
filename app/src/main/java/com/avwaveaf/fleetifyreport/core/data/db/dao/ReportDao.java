package com.avwaveaf.fleetifyreport.core.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.avwaveaf.fleetifyreport.core.domain.entity.Report;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface ReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReports(List<Report> vehicles);

    @Query("SELECT * FROM reports")
    Observable<List<Report>> getCachedReports();

    @Query("SELECT COUNT(*) FROM reports")
    Single<Long> getReportCounts();

    @Query("SELECT * FROM reports WHERE vehicleLicenseNumber LIKE :vehicleLicenseNumber")
    Observable<List<Report>> getReportsByLicense(String vehicleLicenseNumber);

    @Query("SELECT COUNT(*) FROM reports WHERE vehicleLicenseNumber LIKE :vehicleLicenseNumber")
    Single<Long> getReportCountsByLicense(String vehicleLicenseNumber);
}
