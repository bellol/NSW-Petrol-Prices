package com.bellng.fuelprices.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Bell on 20-Nov-16.
 */

public interface SettingsView extends MvpView {

    void setFuelTypeSpinner(List<String> items, String currentlySelected);

    void setSearchRadiusSpinner(List<Integer> items, int currentlySelected);
}
