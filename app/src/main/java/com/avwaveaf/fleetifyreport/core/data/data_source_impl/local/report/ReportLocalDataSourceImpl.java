package com.avwaveaf.fleetifyreport.core.data.data_source_impl.local.report;

import com.avwaveaf.fleetifyreport.core.data.data_source.local.report.ReportLocalDataSource;
import com.avwaveaf.fleetifyreport.core.data.db.dao.ReportDao;
import com.avwaveaf.fleetifyreport.core.domain.entity.Report;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class ReportLocalDataSourceImpl implements ReportLocalDataSource {
    private final ReportDao reportDao;

    @Inject
    public ReportLocalDataSourceImpl(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Override
    public void saveReports(List<Report> reports) {
        reportDao.insertReports(reports);
    }

    @Override
    public Observable<List<Report>> getCachedReports() {
        return reportDao.getCachedReports();
    }

    @Override
    public Single<Long> getReportsCount() {
        return reportDao.getReportCounts();
    }

    public Observable<List<Report>> getReportsByLicense(String vehicleLicenseNumber) {
        return reportDao.getReportsByLicense(vehicleLicenseNumber);
    }

    public Single<Long> getReportsCountByLicense(String vehicleLicenseNumber) {
        return reportDao.getReportCountsByLicense(vehicleLicenseNumber);
    }

    @Override
    public void deleteAll() {
        reportDao.deleteAllReports();
    }
}
