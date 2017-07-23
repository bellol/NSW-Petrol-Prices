package com.bellng.fuelprices.presenter;

import android.content.SharedPreferences;

import com.bellng.fuelprices.Constants;
import com.bellng.fuelprices.view.SettingsView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bell on 20-Nov-16.
 */

public class SettingsPresenter extends MvpBasePresenter<SettingsView> {

    SharedPreferences sharedPreferences;

    public SettingsPresenter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void onAttach() {
        List<String> fuelTypes = new ArrayList<>();
        fuelTypes.add("E10");
        fuelTypes.add("U91");
        getView().setFuelTypeSpinner(fuelTypes, sharedPreferences.getString(Constants.FUEL_TYPE, "E10"));

        List<Integer> searchRadius = new ArrayList<>();
        searchRadius.add(3);
        searchRadius.add(5);
        getView().setSearchRadiusSpinner(searchRadius, sharedPreferences.getInt(Constants.SEARCH_RADIUS, 3));

    }

    public void onDestroy(String fuelType, int searchRadius) {
        sharedPreferences.edit().putString(Constants.FUEL_TYPE, fuelType).apply();
        sharedPreferences.edit().putInt(Constants.SEARCH_RADIUS, searchRadius).apply();
    }
}
