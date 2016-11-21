package com.bellng.fuelprices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bellng.fuelprices.activity.FuelActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, FuelActivity.class));
        finish();
    }

}
