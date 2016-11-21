package com.bellng.fuelprices.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bellng.fuelprices.R;
import com.bellng.fuelprices.presenter.DashboardPresenter;
import com.bellng.fuelprices.view.DashboardView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends BaseMvpActivity<DashboardView, DashboardPresenter> implements DashboardView {

    @BindView(R.id.average)
    TextView average;

    IconGenerator generator;

    MapFragment mapFragment;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 20-Nov-16 inject this into presenter
        generator = new IconGenerator(this);
        generator.setContentRotation(270);
        generator.setStyle(IconGenerator.STYLE_BLUE);

        mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment));
        mapFragment.getMapAsync(googleMap -> {
            map = googleMap;
            presenter.onMapReady();
            map.setMyLocationEnabled(true);
        });

        presenter.onAttach();
    }

    @Override
    protected void inject() {
        appComponent.inject(this);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_dashboard;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                presenter.onSettingsClicked();
                break;
            case R.id.action_refresh:
                presenter.onRefreshClicked();
                break;
        }
        return true;
    }

    @Override
    public void showSettingsView() {
        startActivity(new Intent(this, SettingsActivity.class));
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

    @OnClick(R.id.list_view_tile)
    public void onListViewClicked() {
        presenter.onListViewClicked();
    }

    @Override
    public void showListViewActivity() {
        startActivity(new Intent(this, ServiceStationListActivity.class));
    }

    @Override
    public void moveCamera(double latitude, double longitude) {
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
        map.animateCamera(CameraUpdateFactory.zoomTo(14));
    }

    @Override
    public void clearMarkers() {
        map.clear();
    }

    @Override
    public void setAveragePrice(double averagePrice) {
        DecimalFormat df = new DecimalFormat("#00.0");
        average.setText(df.format(averagePrice) + " c/L");
    }

    @Override
    public void addServiceStationMarker(String name, String snippet, double latitude, double longitude) {
        map.addMarker(new MarkerOptions()
                .title(name)
                .icon(BitmapDescriptorFactory.fromBitmap(generator.makeIcon(snippet)))
                .position(new LatLng(latitude, longitude))
        );
    }
}
