package com.avwaveaf.fleetifyreport.core.data.data_source.local.report;

import com.avwaveaf.fleetifyreport.core.domain.entity.Report;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface ReportLocalDataSource {
    void saveReports(List<Report> reports);

    Observable<List<Report>> getCachedReports();

    Single<Long> getReportsCount();

    Observable<List<Report>> getReportsByLicense(String vehicleLicenseNumber);

    Single<Long> getReportsCountByLicense(String vehicleLicenseNumber);
}
