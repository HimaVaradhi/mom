package in.amruthashala.momapp.screens;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import java.util.concurrent.Executor;

import butterknife.BindView;
import in.amruthashala.momapp.Adapaters.SignUpPagerAdapater;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;
import in.amruthashala.momapp.common.LocationTracker;
import in.amruthashala.momapp.common.RequestCodes;

public class SignUp extends BaseClass implements ViewPager.OnPageChangeListener {
    @BindView(R.id.tab_layout)
    TabLayout tbTabLayout;
    @BindView(R.id.viewpager)
    ViewPager vpPager;
    @BindView(R.id.toolbar)
    Toolbar tbToolBar;
    Menu menu;
    int tabPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.sign_up);
        SignUpPagerAdapater signUpPagerAdapater = new SignUpPagerAdapater(getSupportFragmentManager());
        vpPager.setAdapter(signUpPagerAdapater);
        //viewPager.setOffscreenPageLimit(4);
        tbTabLayout.setupWithViewPager(vpPager);
        vpPager.addOnPageChangeListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_sign_up;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_sign_up, menu);
        this.menu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.forward:
                tabPosition++;
                vpPager.setCurrentItem(tabPosition);
                break;
            case R.id.backward:
                tabPosition--;
                vpPager.setCurrentItem(tabPosition);
                break;

            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        tabPosition = position;
        try {
            if (position == 0) {
                menu.getItem(0).setVisible(false);
            } else {
                menu.getItem(0).setVisible(true);
            }

            if (position == 4) {
                menu.getItem(1).setVisible(false);
            } else {
                menu.getItem(1).setVisible(true);
            }
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    @Override
    public void onPageSelected(int position) {
        tabPosition = position;
        try {
            if (position == 0) {
                menu.getItem(0).setVisible(false);
            } else {
                menu.getItem(0).setVisible(true);
            }

            if (position == 4) {
                menu.getItem(1).setVisible(false);
            } else {
                menu.getItem(1).setVisible(true);
            }
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...

                /*Toast.makeText(SignUp2.this, "Gps already open",
                        Toast.LENGTH_LONG).show();*/
                Log.d("location settings", locationSettingsResponse.toString());
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(SignUp.this,
                                RequestCodes.LOCATION);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCodes.LOCATION) {

            if (resultCode == RESULT_OK) {

                /*location = new LocationTracker(getActivity());
                addresses = location.getAddress();*/
                /*etStreetName.setText(addresses.get(0).getSubThoroughfare() != null ? addresses.get(0).getSubThoroughfare() : "");
                etLocality.setText(addresses.get(0).getLocality() != null ? addresses.get(0).getLocality() : "");
                etLandMark.setText(addresses.get(0).getFeatureName() != null ? addresses.get(0).getFeatureName() : "");
                etCity.setText(addresses.get(0).getLocality() != null ? addresses.get(0).getLocality() : "");
                etPin.setText(addresses.get(0).getPostalCode() != null ? addresses.get(0).getPostalCode() : "");
                etState.setText(addresses.get(0).getAdminArea() != null ? addresses.get(0).getAdminArea() : "");
                etCountry.setText(addresses.get(0).getCountryName() != null ? addresses.get(0).getCountryName() : "");
  */              //if user allows to open gps
                Log.d("result ok", data.toString());

            } else if (resultCode == RESULT_CANCELED) {
                // in case user back press or refuses to open gps
                Log.d("result cancelled", data.toString());
            }
        }
    }
}
