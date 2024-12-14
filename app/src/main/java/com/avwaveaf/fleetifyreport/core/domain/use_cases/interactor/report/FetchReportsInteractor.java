package com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor.report;

import com.avwaveaf.fleetifyreport.core.domain.entity.Report;
import com.avwaveaf.fleetifyreport.core.domain.repository.ReportRepository;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report.FetchReportsUseCase;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FetchReportsInteractor implements FetchReportsUseCase {
    private final ReportRepository reportRepository;

    @Inject
    public FetchReportsInteractor(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Observable<Resource<List<Report>>> execute(String userId) {
        return reportRepository.fetchReports(userId)
                .subscribeOn(Schedulers.io());
    }
}
