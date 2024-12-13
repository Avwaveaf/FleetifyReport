package com.avwaveaf.fleetifyreport.core.di;

import com.avwaveaf.fleetifyreport.core.data.data_source.local.profile.ProfileDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source.local.report.ReportLocalDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source.local.vehicle.VehicleLocalDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source.remote.report.ReportRemoteDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source.remote.vehicle.VehicleRemoteDataSource;
import com.avwaveaf.fleetifyreport.core.data.repository.ProfileRepositoryImpl;
import com.avwaveaf.fleetifyreport.core.data.repository.ReportRepositoryImpl;
import com.avwaveaf.fleetifyreport.core.data.repository.VehicleRepositoryImpl;
import com.avwaveaf.fleetifyreport.core.domain.repository.ProfileRepository;
import com.avwaveaf.fleetifyreport.core.domain.repository.ReportRepository;
import com.avwaveaf.fleetifyreport.core.domain.repository.VehicleRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    public ProfileRepository provideProfileRepository(ProfileDataSource profileDataSource) {
        return new ProfileRepositoryImpl(profileDataSource);
    }

    @Provides
    @Singleton
    public VehicleRepository provideVehicleRepository(VehicleRemoteDataSource vehicleRemoteDataSource, VehicleLocalDataSource vehicleLocalDataSource) {
        return new VehicleRepositoryImpl(vehicleRemoteDataSource, vehicleLocalDataSource);
    }

    @Provides
    @Singleton
    public ReportRepository provideReportRepository(ReportRemoteDataSource reportRemoteDataSource, ReportLocalDataSource reportLocalDataSource) {
        return new ReportRepositoryImpl(reportRemoteDataSource, reportLocalDataSource);
    }
}
