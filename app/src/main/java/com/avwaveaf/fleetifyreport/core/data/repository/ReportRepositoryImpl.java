package com.avwaveaf.fleetifyreport.core.data.repository;

import android.content.Context;

import com.avwaveaf.fleetifyreport.core.data.data_source.local.report.ReportLocalDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source.remote.report.ReportRemoteDataSource;
import com.avwaveaf.fleetifyreport.core.data.entity.ReportDTO;
import com.avwaveaf.fleetifyreport.core.data.mapper.ReportMapper;
import com.avwaveaf.fleetifyreport.core.domain.entity.Report;
import com.avwaveaf.fleetifyreport.core.domain.repository.ReportRepository;
import com.avwaveaf.fleetifyreport.core.utils.ConnectivityUtil;
import com.avwaveaf.fleetifyreport.core.utils.NetworkBoundResource;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReportRepositoryImpl implements ReportRepository {
    private final ReportRemoteDataSource reportRemoteDataSource;
    private final ReportLocalDataSource reportLocalDataSource;
    private final Context context;

    @Inject
    public ReportRepositoryImpl(ReportRemoteDataSource reportRemoteDataSource, ReportLocalDataSource reportLocalDataSource, Context context) {
        this.reportRemoteDataSource = reportRemoteDataSource;
        this.reportLocalDataSource = reportLocalDataSource;
        this.context = context;
    }

    @Override
    public Single<Resource<Object>> addReport(RequestBody vehicleId, RequestBody note, RequestBody userId, MultipartBody.Part body) {
        return reportRemoteDataSource.addReport(vehicleId, note, userId, body);
    }

    @Override
    public Observable<Resource<List<Report>>> getAllReport(String userId, String vehicleLicenseNumber) {
        return new NetworkBoundResource<List<Report>, Resource<List<ReportDTO>>>() {

            @Override
            protected Observable<Resource<List<ReportDTO>>> createCall() {
                return reportRemoteDataSource.getAllReport(userId, vehicleLicenseNumber);
            }

            @Override
            protected List<Report> processResponse(Resource<List<ReportDTO>> response) {
                if (response.getData().isEmpty()) {
                    return new ArrayList<>();
                }
                return ReportMapper.toDomainList(response.getData());
            }


            @Override
            protected void saveCallResult(List<Report> items) {
                reportLocalDataSource.saveReports(items);
            }

            @Override
            protected Single<Boolean> shouldFetch(List<Report> cachedData) {
                boolean net = ConnectivityUtil.isNetworkAvailable(context);
                if (net) {
                    if (vehicleLicenseNumber.trim().isEmpty()) {
                        return reportLocalDataSource.getReportsCount()
                                .map(count -> count == 0);
                    } else {
                        return reportLocalDataSource.getReportsCountByLicense("%" + vehicleLicenseNumber + "%")
                                .map(count -> count == 0);
                    }
                } else {
                    return Single.just(Boolean.FALSE);
                }
            }


            @Override
            protected Observable<List<Report>> loadFromCache() {
                // Your existing cache loading logic
                if (vehicleLicenseNumber.trim().isEmpty()) {
                    return reportLocalDataSource.getCachedReports();
                }
                return reportLocalDataSource.getReportsByLicense("%" + vehicleLicenseNumber + "%");
            }
        }.asObservable();
    }

    @Override
    public void deleteAllReports() {
        reportLocalDataSource.deleteAll();
    }
}
