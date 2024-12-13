package com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor.report;

import com.avwaveaf.fleetifyreport.core.domain.repository.ReportRepository;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report.AddReportUseCase;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddReportInteractor implements AddReportUseCase {
    private final ReportRepository reportRepository;

    @Inject
    public AddReportInteractor(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Single<Resource<Object>> execute(RequestBody vehicleId, RequestBody note, RequestBody userId, MultipartBody.Part photo) {
        return reportRepository.addReport(vehicleId, note, userId, photo);
    }
}
