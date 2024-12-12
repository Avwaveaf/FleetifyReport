package com.avwaveaf.fleetifyreport.core.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.avwaveaf.fleetifyreport.R;
import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;

import java.util.List;

public class VehicleSpinnerAdapter extends ArrayAdapter<Vehicle> {

    public VehicleSpinnerAdapter(Context context, List<Vehicle> vehicles) {
        super(context, R.layout.vehicle_spinner_dropdown_item, vehicles);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        // View for the dropdown
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // View for the selected item (the one shown on the spinner itself)
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.vehicle_spinner_dropdown_item, parent, false);
        }


        Vehicle vehicle = getItem(position);

        TextView vehicleType = view.findViewById(R.id.vehicle_type);
        TextView licenseNumber = view.findViewById(R.id.license_number);

        if (vehicle != null) {
            vehicleType.setText(vehicle.getType());
            licenseNumber.setText(vehicle.getLicenseNumber());
        }

        return view;
    }
}
