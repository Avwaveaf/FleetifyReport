package com.avwaveaf.fleetifyreport.core.data.repository;

import com.avwaveaf.fleetifyreport.core.data.data_source.local.report.ReportLocalDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source.remote.report.ReportRemoteDataSource;
import com.avwaveaf.fleetifyreport.core.data.entity.ReportDTO;
import com.avwaveaf.fleetifyreport.core.data.mapper.ReportMapper;
import com.avwaveaf.fleetifyreport.core.domain.entity.Report;
import com.avwaveaf.fleetifyreport.core.domain.repository.ReportRepository;
import com.avwaveaf.fleetifyreport.core.utils.NetworkBoundResource;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReportRepositoryImpl implements ReportRepository {
    private final ReportRemoteDataSource reportRemoteDataSource;
    private final ReportLocalDataSource reportLocalDataSource;

    @Inject
    public ReportRepositoryImpl(ReportRemoteDataSource reportRemoteDataSource, ReportLocalDataSource reportLocalDataSource) {
        this.reportRemoteDataSource = reportRemoteDataSource;
        this.reportLocalDataSource = reportLocalDataSource;
    }

    @Override
    public Single<Resource<Object>> addReport(RequestBody vehicleId, RequestBody note, RequestBody userId, MultipartBody.Part body) {
        return reportRemoteDataSource.addReport(vehicleId, note, userId, body);
    }

    @Override
    public Observable<Resource<List<Report>>> getAllReport(String userId, String vehicleLicenseNumber) {
        return new NetworkBoundResource<List<Report>, List<ReportDTO>>() {


            @Override
            protected Observable<List<ReportDTO>> createCall() {
                return reportRemoteDataSource.getAllReport(userId, vehicleLicenseNumber);
            }

            @Override
            protected List<Report> processResponse(List<ReportDTO> response) {
                return ReportMapper.toDomainList(response);
            }

            @Override
            protected void saveCallResult(List<Report> items) {
                reportLocalDataSource.saveReports(items);
            }

            @Override
            protected Single<Boolean> shouldFetch() {
                if (vehicleLicenseNumber.trim().isEmpty()) {
                    return reportLocalDataSource.getReportsCount()
                            .map(count -> count == 0);
                }

                return reportLocalDataSource.getReportsCountByLicense("%" + vehicleLicenseNumber + "%")
                        .map(count -> count == 0);
            }

            @Override
            protected Observable<List<Report>> loadFromCache() {
                if (vehicleLicenseNumber.trim().isEmpty()) {
                    return reportLocalDataSource.getCachedReports();
                }

                return reportLocalDataSource.getReportsByLicense("%" + vehicleLicenseNumber + "%");
            }
        }.asObservable();
    }
}
