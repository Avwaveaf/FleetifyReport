package com.avwaveaf.fleetifyreport.core.di;

import com.avwaveaf.fleetifyreport.core.domain.repository.ProfileRepository;
import com.avwaveaf.fleetifyreport.core.domain.repository.ReportRepository;
import com.avwaveaf.fleetifyreport.core.domain.repository.VehicleRepository;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.profile.ProfileUseCase;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report.AddReportUseCase;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report.DeleteAllReportUseCase;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report.GetAllReportUseCase;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.vehicle.GetVehicleUseCase;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor.DeleteAllReportInteractor;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor.profile.ProfileInteractor;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor.report.AddReportInteractor;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor.report.GetAllReportInteractor;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor.vehicle.GetVehicleInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class UseCaseModule {
    @Provides
    @Singleton
    public ProfileUseCase provideProfileUseCase(ProfileRepository profileRepository) {
        return new ProfileInteractor(profileRepository);
    }

    @Provides
    @Singleton
    public GetVehicleUseCase provideGetVehicleInteractor(VehicleRepository vehicleRepository) {
        return new GetVehicleInteractor(vehicleRepository);
    }

    @Provides
    @Singleton
    public AddReportUseCase provodeAddReportUseCase(ReportRepository reportRepository) {
        return new AddReportInteractor(reportRepository);
    }


    @Provides
    @Singleton
    public GetAllReportUseCase provideGetAllReportUseCase(ReportRepository reportRepository) {
        return new GetAllReportInteractor(reportRepository);
    }

    @Provides
    @Singleton
    public DeleteAllReportUseCase provideDeleteAllReport(ReportRepository reportRepository) {
        return new DeleteAllReportInteractor(reportRepository);
    }
}
