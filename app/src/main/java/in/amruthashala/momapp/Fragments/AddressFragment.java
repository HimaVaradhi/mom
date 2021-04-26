package in.amruthashala.momapp.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.amruthashala.momapp.Interfaces.CommonClick;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.CommonDialog;
import in.amruthashala.momapp.common.Constant;
import in.amruthashala.momapp.common.LocationTracker;
import in.amruthashala.momapp.common.PermissionCheck;
import in.amruthashala.momapp.common.RequestCodes;
import in.amruthashala.momapp.inputresponsemodel.AddressList;
import in.amruthashala.momapp.inputresponsemodel.ProductVariant;
import in.amruthashala.momapp.screens.SignUp;
import in.amruthashala.momapp.screens.SignUp2;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddressFragment extends Fragment implements CommonClick {

    PermissionCheck permissionCheck;
    CommonDialog commonDialog;
    LocationTracker location;
    List<Address> addresses;
    String address = "";
    boolean isResidenceClicked = false;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.txt_residence)
    TextView txtResidence;
    EditText etDoorNo, etStreetName, etLocality, etLandMark, etCity, etPin, etState, etCountry;
    @BindView(R.id.ll_save_layout)
    LinearLayout ll_save_layout;
    ArrayList<AddressList> addressLists;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_sign_up, container, false);
        ButterKnife.bind(this, view);
        permissionCheck = new PermissionCheck();
        commonDialog = new CommonDialog();
        if (!permissionCheck.isLocationPermissionExist(getActivity())) {
            permissionCheck.requestLocationPermission(getActivity());
        } else {
            location = new LocationTracker(getActivity());
            addresses = location.getAddress();
        }
        addressLists = new ArrayList<>();
        ll_save_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveaddress();
            }
        });




        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RequestCodes.LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    location = new LocationTracker(getActivity());
                    addresses = location.getAddress();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                    } else {
                        commonDialog.showPermissionAlert(getActivity(), "This app need location to access so please kindly provide permission", this);
                    }
                }
        }
    }


    @OnClick({R.id.ll_address, R.id.img_edit, R.id.txt_modify})
    public void addressClick() {
        isResidenceClicked = false;
        showDialog();
    }

    @OnClick({R.id.ll_residence_address, R.id.img_edit_residence, R.id.txt_edit_residence})
    public void residenceClick() {
        isResidenceClicked = true;
        showDialog();
    }

    public void showDialog() {
        address = "";
        Dialog dialog = new Dialog(getActivity());
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
                if (!permissionCheck.isLocationPermissionExist(getActivity())) {
                    permissionCheck.requestLocationPermission(getActivity());
                } else {
                    try {
                        location = new LocationTracker(getActivity());
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
                        ((SignUp) getActivity()).createLocationRequest();
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
                    if(isResidenceClicked){
                        txtResidence.setText(address);
                        addressLists.add(new AddressList("",etStreetName.getText().toString(),etLandMark.getText().toString(),etState.getText().toString(),etCity.getText().toString(),"",
                                "",etPin.getText().toString(),""));

                    }else {
                        txtAddress.setText(address);
                        addressLists.add(new AddressList("",etStreetName.getText().toString(),etLandMark.getText().toString(),etState.getText().toString(),etCity.getText().toString(),"",
                                "",etPin.getText().toString(),""));

                    }
                    //txtAddress.setText(address);

                }
            }
        });
        dialog.show();
    }
    public void saveaddress() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();


        for (int i = 0; i < addressLists.size(); i++) {

                   try {
                       jsonObject.put("name", "");
                       jsonObject.put("address",addressLists.get(i).getAddress());
                       jsonObject.put("landmark", addressLists.get(i).getLandmark());
                       jsonObject.put("state",  addressLists.get(i).getState());
                       jsonObject.put("city", addressLists.get(i).getCity());
                       jsonObject.put("latitude", "");
                       jsonObject.put("longitude","");
                       jsonObject.put("pincode",addressLists.get(i).getPincode());
                       jsonObject.put("alternate_mobile_number","");

                jsonObject1.put("" + (i + 1), jsonObject);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        Log.d("dsdsdsd", jsonObject1.toString());
        Constant.Addressobject=jsonObject1.toString();//applidetails: himahimabindhu19-04-20219866568698hima1234@gmail.comFemale
        //2021-04-19 15:06:07.045 31831-31831/in.amruthashala.momapp D/dsdsdsd: {"1":{"name":"","address":"btm layout","landmark":"Nandhini parlour","state":"karnataka","city":"karnataka","latitude":"","longitude":"","pincode":"560098","alternate_mobile_number":""}}
    }// E/payapplidetails: payapplidetails: {"bank_account_details":{"account_holder_name":"Sharat kumar","account_no":"56565656563","ifsc_code":"IFSC76677","bank_name":"Syndicate Bank","branch_name":"banglore"},"other_account":{"account_type":"Savings","mobile_number":"9859868685"}}
    //payapplidetails:{"running_business":"Home_Chef","registed_business":"No","registration_no":"47859","available_in_any_platform":"Yes","paltform_type":"Online","platfrom_name":"restaurants","profile_link":"https:\/\/www.zomato.com\/bangalore"}
    @Override
    public void commonClick(int code) {

    }
}
