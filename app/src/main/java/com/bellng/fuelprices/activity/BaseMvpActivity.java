package com.bellng.fuelprices.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.bellng.fuelprices.AppComponent;
import com.bellng.fuelprices.FuelApplication;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * Created by Bell on 20-Nov-16.
 */

public abstract class BaseMvpActivity<V extends MvpView, P extends MvpPresenter<V>> extends MvpActivity<V, P> implements MvpView {

    @Inject
    P presenter;

    AppComponent appComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appComponent = ((FuelApplication) getApplication()).component();
        inject();

        super.onCreate(savedInstanceState);

        if (getContentLayout() > 0) setContentView(getContentLayout());
        ButterKnife.bind(this);
    }

    @NonNull
    @Override
    public P createPresenter() {
        return presenter;
    }

    protected abstract void inject();

    protected abstract
    @LayoutRes
    int getContentLayout();
}
