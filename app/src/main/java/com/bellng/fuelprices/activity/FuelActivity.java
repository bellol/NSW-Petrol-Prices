package com.bellng.fuelprices.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.bellng.fuelprices.FuelApplication;
import com.bellng.fuelprices.R;
import com.bellng.fuelprices.ServiceStationAdapter;
import com.bellng.fuelprices.dto.ServiceStation;
import com.bellng.fuelprices.presenter.FuelPresenter;
import com.bellng.fuelprices.view.FuelView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bell on 19-Nov-16.
 */

public class FuelActivity extends MvpActivity<FuelView, FuelPresenter> implements FuelView {
    @Inject
    FuelPresenter presenter;

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.average)
    TextView average;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    MapFragment mapFragment;
    GoogleMap map;

    ServiceStationAdapter serviceStationAdapter;

    @NonNull
    @Override
    public FuelPresenter createPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((FuelApplication) getApplication()).component().inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel);
        ButterKnife.bind(this);

        mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        serviceStationAdapter = new ServiceStationAdapter(new ServiceStationAdapter.OnSelect() {
            @Override
            public void onSelect(ServiceStation serviceStation) {
                presenter.onServiceStationSelected(serviceStation);
            }
        });

        recyclerView.setAdapter(serviceStationAdapter);

        ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                FuelActivity.this.map = googleMap;
                presenter.onMapReady();
            }
        });

        presenter.onAttach();
    }

    @Override
    public void centerMapTo(double latitude, double longitude) {
        map.setMyLocationEnabled(true);

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude,
                longitude));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(14.1f);

        map.moveCamera(center);
        map.animateCamera(zoom);
    }

    public void addServiceStationMarker(String name, double latitude, double longitude) {
        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(latitude, longitude)).title(name);
        map.addMarker(markerOptions);
    }

    @Override
    public void caaaasda() {
        ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
        Resources r = getResources();

        params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, r.getDisplayMetrics());
        //mapFragment.getView().setLayoutParams(params);
        map.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

    public void clearMarkers() {
        map.clear();
    }

    @Override
    public void setFuelTypeSpinner(List<String> items) {
        List<String> spinnerArray = new ArrayList<String>();

        for (String item : items) {
            spinnerArray.add(item);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void setServiceStationList(List<ServiceStation> serviceStations) {
        serviceStationAdapter.setServiceStations(serviceStations);
        serviceStationAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAveragePrice(double averagePrice) {
        DecimalFormat df = new DecimalFormat("#00.0");
        average.setText(df.format(averagePrice) + " c/L");
    }

    @OnClick(R.id.button)
    public void refresh() {
        presenter.buttonPressed(1, 1, spinner.getSelectedItem().toString());
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
}
