package com.avwaveaf.fleetifyreport.core.di;

import com.avwaveaf.fleetifyreport.core.data.api_services.ReportApiService;
import com.avwaveaf.fleetifyreport.core.data.api_services.VehicleApiService;
import com.avwaveaf.fleetifyreport.core.data.data_source.local.profile.ProfileDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source.local.report.ReportLocalDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source.local.vehicle.VehicleLocalDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source.remote.report.ReportRemoteDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source.remote.vehicle.VehicleRemoteDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source_impl.local.profile.ProfileDataSourceImpl;
import com.avwaveaf.fleetifyreport.core.data.data_source_impl.local.report.ReportLocalDataSourceImpl;
import com.avwaveaf.fleetifyreport.core.data.data_source_impl.local.vehicle.VehicleLocalDataSourceImpl;
import com.avwaveaf.fleetifyreport.core.data.data_source_impl.remote.report.ReportRemoteDataSourceImpl;
import com.avwaveaf.fleetifyreport.core.data.data_source_impl.remote.vehicle.VehicleRemoteDataSourceImpl;
import com.avwaveaf.fleetifyreport.core.data.db.dao.ReportDao;
import com.avwaveaf.fleetifyreport.core.data.db.dao.VehicleDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataSourceModule {
    @Provides
    @Singleton
    public ProfileDataSource provideProfileDataSource(){
        return new ProfileDataSourceImpl();
    }

    @Provides
    @Singleton
    public VehicleRemoteDataSource provideVehicleRemoteDataSource(VehicleApiService vehicleApiService){
        return new VehicleRemoteDataSourceImpl(vehicleApiService);
    }

    @Provides
    @Singleton
    public VehicleLocalDataSource provideVehicleLocalDataSource(VehicleDao vehicleDao) {
        return new VehicleLocalDataSourceImpl(vehicleDao);
    }

    @Provides
    @Singleton
    public ReportRemoteDataSource provideReportRemoteDataSource(ReportApiService reportApiService) {
        return new ReportRemoteDataSourceImpl(reportApiService);
    }

    @Provides
    @Singleton
    public ReportLocalDataSource provideReportLocalDataSource(ReportDao reportDao) {
        return new ReportLocalDataSourceImpl(reportDao);
    }
}
