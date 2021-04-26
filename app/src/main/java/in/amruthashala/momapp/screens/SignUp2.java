package in.amruthashala.momapp.screens;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.amruthashala.momapp.Interfaces.CommonClick;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;
import in.amruthashala.momapp.common.CommonDialog;
import in.amruthashala.momapp.common.LocationTracker;
import in.amruthashala.momapp.common.PermissionCheck;
import in.amruthashala.momapp.common.RequestCodes;

public class SignUp2 extends BaseClass implements CommonClick {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.ll_own)
    LinearLayout llOwn;
    @BindView(R.id.ll_apartment)
    LinearLayout llApartment;
    @BindView(R.id.ll_independent)
    LinearLayout llIndependent;
    PermissionCheck permissionCheck;
    CommonDialog commonDialog;
    LocationTracker location;
    List<Address> addresses;
    String address = "";
    EditText etDoorNo, etStreetName, etLocality, etLandMark, etCity, etPin, etState, etCountry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        llOwn.setBackground(getResources().getDrawable(R.drawable.border_grey));
        permissionCheck = new PermissionCheck();
        commonDialog = new CommonDialog();
        if (!permissionCheck.isLocationPermissionExist(this)) {
            permissionCheck.requestLocationPermission(this);
        } else {
            location = new LocationTracker(this);
            addresses = location.getAddress();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestCodes.LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    location = new LocationTracker(this);
                    addresses = location.getAddress();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(SignUp2.this, Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(SignUp2.this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                    } else {
                        commonDialog.showPermissionAlert(SignUp2.this, "This app need location to access so please kindly provide permission", this);
                    }
                }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.sign_up_2;
    }

    @OnClick(R.id.linear_layout_modify)
    public void showAlert() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.sign_up_alert);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.flag_transparent);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        AppCompatButton acSave = dialog.findViewById(R.id.btn_save);
        LinearLayout llLoaction = dialog.findViewById(R.id.ll_location);
        etDoorNo = dialog.findViewById(R.id.et_door_no);
        etStreetName = dialog.findViewById(R.id.et_street_name);
        etLocality = dialog.findViewById(R.id.et_locality);
        etLandMark = dialog.findViewById(R.id.et_land_mark);
        etCity = dialog.findViewById(R.id.et_city);
        etPin = dialog.findViewById(R.id.et_pin);
        etState = dialog.findViewById(R.id.et_state);
        etCountry = dialog.findViewById(R.id.et_country);
        llLoaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!permissionCheck.isLocationPermissionExist(SignUp2.this)) {
                    permissionCheck.requestLocationPermission(SignUp2.this);
                } else {
                    try {
                        location = new LocationTracker(SignUp2.this);
                        addresses = location.getAddress();
                        etStreetName.setText(addresses.get(0).getSubThoroughfare() != null ? addresses.get(0).getSubThoroughfare() : "");
                        etLocality.setText(addresses.get(0).getLocality() != null ? addresses.get(0).getLocality() : "");
                        etLandMark.setText(addresses.get(0).getFeatureName() != null ? addresses.get(0).getFeatureName() : "");
                        etCity.setText(addresses.get(0).getLocality() != null ? addresses.get(0).getLocality() : "");
                        etPin.setText(addresses.get(0).getPostalCode() != null ? addresses.get(0).getPostalCode() : "");
                        etState.setText(addresses.get(0).getAdminArea() != null ? addresses.get(0).getAdminArea() : "");
                        etCountry.setText(addresses.get(0).getCountryName() != null ? addresses.get(0).getCountryName() : "");
                    } catch (Exception e) {
                        Log.d("error", e.toString());
                        createLocationRequest();
                    }
                }
            }
        });
        acSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etLocality.getText() == null || etLocality.getText().toString().isEmpty()) {
                    etLocality.setError("Please enter locality");
                } else if (etLandMark.getText() == null || etLandMark.getText().toString().isEmpty()) {
                    etLandMark.setError("Please enter landmark");
                } else {
                    if (etDoorNo.getText() != null && !etDoorNo.getText().toString().isEmpty()) {
                        address = address + etDoorNo.getText().toString();
                    }
                    if (etStreetName.getText() != null && !etStreetName.getText().toString().isEmpty()) {
                        if (address.isEmpty()) {
                            address = address + etStreetName.getText().toString();
                        } else {
                            address = address + "," + etStreetName.getText().toString();
                        }
                    }
                    if (etLocality.getText() != null && !etLocality.getText().toString().isEmpty()) {
                        if (address.isEmpty()) {
                            address = address + etLocality.getText().toString();
                        } else {
                            address = address + "," + etLocality.getText().toString();
                        }
                    }
                    if (etLandMark.getText() != null && !etLandMark.getText().toString().isEmpty()) {
                        if (address.isEmpty()) {
                            address = address + etLandMark.getText().toString();
                        } else {
                            address = address + "," + etLandMark.getText().toString();
                        }
                    }
                    if (etCity.getText() != null && !etCity.getText().toString().isEmpty()) {
                        if (address.isEmpty()) {
                            address = address + etCity.getText().toString();
                        } else {
                            address = address + "," + etCity.getText().toString();
                        }
                    }
                    if (etPin.getText() != null && !etPin.getText().toString().isEmpty()) {
                        if (address.isEmpty()) {
                            address = address + etPin.getText().toString();
                        } else {
                            address = address + "," + etPin.getText().toString();
                        }
                    }
                    if (etState.getText() != null && !etState.getText().toString().isEmpty()) {
                        if (address.isEmpty()) {
                            address = address + etState.getText().toString();
                        } else {
                            address = address + "," + etState.getText().toString();
                        }
                    }
                    if (etCountry.getText() != null && !etCountry.getText().toString().isEmpty()) {
                        if (address.isEmpty()) {
                            address = address + etCountry.getText().toString();
                        } else {
                            address = address + "," + etCountry.getText().toString();
                        }
                    }
                    dialog.dismiss();
                    txtAddress.setText(address);

                }
            }
        });
        dialog.show();
    }

    @OnClick({R.id.ll_own, R.id.cv_own, R.id.tv_own})
    public void card1Clicked() {
        llOwn.setBackground(getResources().getDrawable(R.drawable.border_grey));
        llIndependent.setBackgroundResource(0);
        llApartment.setBackgroundResource(0);
    }

    @OnClick({R.id.ll_independent, R.id.cv_independent, R.id.tv_independent})
    public void card2Clicked() {
        llIndependent.setBackground(getResources().getDrawable(R.drawable.border_grey));
        llOwn.setBackgroundResource(0);
        llApartment.setBackgroundResource(0);
    }

    @OnClick({R.id.ll_apartment, R.id.cv_apartment, R.id.tv_apartment})
    public void card3Clicked() {
        llApartment.setBackground(getResources().getDrawable(R.drawable.border_grey));
        llIndependent.setBackgroundResource(0);
        llOwn.setBackgroundResource(0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        /*if (!permissionCheck.isLocationPermissionExist(this)) {
            permissionCheck.requestLocationPermission(this);
        } else {
            location = new Location(this);
            addresses = location.getCurrentAddress();
        }*/
    }

    @OnClick(R.id.btn_proceed)
    public void onClick() {
        startActivity(new Intent(this, SignUp.class));
    }

    @Override
    public void commonClick(int code) {

    }

    protected void createLocationRequest() {
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
                        resolvable.startResolutionForResult(SignUp2.this,
                                RequestCodes.LOCATION);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  RequestCodes.LOCATION) {

            if (resultCode == RESULT_OK) {

                location = new LocationTracker(SignUp2.this);
                addresses = location.getAddress();
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
