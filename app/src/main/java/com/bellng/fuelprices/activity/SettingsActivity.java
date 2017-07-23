package com.bellng.fuelprices.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bellng.fuelprices.R;
import com.bellng.fuelprices.presenter.SettingsPresenter;
import com.bellng.fuelprices.view.SettingsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SettingsActivity extends BaseMvpActivity<SettingsView, SettingsPresenter> implements SettingsView {

    @BindView(R.id.spinner_fuel_type)
    Spinner fuelSpinner;

    @BindView(R.id.spinner_search_radius)
    Spinner radiusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onAttach();
    }

    @Override
    protected void inject() {
        appComponent.inject(this);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_settings;
    }

    @Override
    public void setFuelTypeSpinner(List<String> items, String currentPreference) {
        List<String> spinnerArray = new ArrayList<String>();

        int position = 0;
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            if (item.equals(currentPreference)) position = i;
            spinnerArray.add(item);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelSpinner.setAdapter(adapter);
        fuelSpinner.setSelection(position);
    }

    @Override
    public void setSearchRadiusSpinner(List<Integer> items, int currentPreference) {
        List<Integer> spinnerArray = new ArrayList<Integer>();

        int position = 0;
        for (int i = 0; i < items.size(); i++) {
            int item = items.get(i);
            if (item == currentPreference) position = i;
            spinnerArray.add(item);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        radiusSpinner.setAdapter(adapter);
        radiusSpinner.setSelection(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy((String) fuelSpinner.getSelectedItem(), (int) radiusSpinner.getSelectedItem());
    }
}
