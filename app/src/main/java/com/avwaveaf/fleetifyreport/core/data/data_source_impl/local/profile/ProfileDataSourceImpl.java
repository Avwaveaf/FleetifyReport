package com.avwaveaf.fleetifyreport.core.data.data_source_impl.local.profile;

import com.avwaveaf.fleetifyreport.core.data.data_source.local.profile.ProfileDataSource;
import com.avwaveaf.fleetifyreport.core.domain.entity.Profile;

public class ProfileDataSourceImpl implements ProfileDataSource {
    @Override
    public Profile getProfile() {
        return new Profile("Muhamad Afif Fadillah");
    }
}
