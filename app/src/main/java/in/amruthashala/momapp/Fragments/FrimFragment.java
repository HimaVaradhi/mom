package in.amruthashala.momapp.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import in.amruthashala.momapp.Interfaces.CommonClick;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.CommonDialog;
import in.amruthashala.momapp.common.Constant;
import in.amruthashala.momapp.common.FilePath;
import in.amruthashala.momapp.common.PermissionCheck;
import in.amruthashala.momapp.common.RequestCodes;
import in.amruthashala.momapp.retrofit.APIService;
import in.amruthashala.momapp.retrofit.ApiUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FrimFragment extends Fragment implements CommonClick {
    @BindView(R.id.et_business_name)
    EditText etBusinessName;
    @BindView(R.id.et_reg_no)
    EditText etRegNo;
    @BindView(R.id.et_platform_name)
    EditText etPlatformName;
    @BindView(R.id.et_profile_link)
    EditText etProfileName;
    @BindView(R.id.cb_cooking)
    CheckBox cbCooking;
    @BindView(R.id.cb_baker)
    CheckBox cbBaker;
    @BindView(R.id.cb_selling)
    CheckBox cbSelling;
    @BindView(R.id.cb_food)
    CheckBox cbFood;
    @BindView(R.id.cb_mange)
    CheckBox cbManage;
    @BindView(R.id.cb_mange_cafe)
    CheckBox cbManageCafe;
    @BindView(R.id.cb_reseller)
    CheckBox cbReseller;
    @BindView(R.id.cb_others)
    CheckBox cbOthers;
    @BindView(R.id.edit_comments)
    EditText etComments;
    @BindView(R.id.image_pan)
    LinearLayout llImagePan;
    @BindView(R.id.image_pan_1)
    ImageView ivPan1;
    @BindView(R.id.image_pan_2)
    ImageView ivPan2;
    @BindView(R.id.linear_done_pan)
    LinearLayout llDoneLayout;
    @BindView(R.id.ll_register_no)
    LinearLayout llRegNo;
    @BindView(R.id.txt_yes)
    TextView tvYes;
    @BindView(R.id.txt_no)
    TextView tvNo;
    @BindView(R.id.ll_register_no_layout)
    LinearLayout llRegisterNo;
    @BindView(R.id.txt_business_yes)
    TextView tvBusinessYes;
    @BindView(R.id.txt_business_no)
    TextView tvBusinessNo;
    @BindView(R.id.ll_platform_layout)
            LinearLayout llPlatform;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.txt_rating)
            TextView tvRating;
    @BindView(R.id.Confirmbtn)
    AppCompatButton Confirmbtn;
    PermissionCheck permissionCheck;
    CommonDialog commonDialog;
    boolean isLayoutClicked = false, isImg1 = false, isImg2 = false;
    String strBusiness = "",strPlatform="";
    APIService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.firm_sign_up, container, false);
        ButterKnife.bind(this, view);
        apiService = ApiUtils.getAPIService(getActivity());
        permissionCheck = new PermissionCheck();
        commonDialog = new CommonDialog();
        tvYes.setBackground(getResources().getDrawable(R.drawable.background_border));
        tvNo.setBackground(getResources().getDrawable(R.drawable.three_side_green_border));
        tvYes.setTextColor(getResources().getColor(R.color.black));
        tvNo.setTextColor(getResources().getColor(R.color.white));
        tvBusinessYes.setBackground(getResources().getDrawable(R.drawable.background_border));
        tvBusinessNo.setBackground(getResources().getDrawable(R.drawable.three_side_green_border));
        tvBusinessYes.setTextColor(getResources().getColor(R.color.black));
        tvBusinessNo.setTextColor(getResources().getColor(R.color.white));
        llPlatform.setVisibility(View.GONE);
        etPlatformName.setVisibility(View.GONE);
        etProfileName.setVisibility(View.GONE);
        strBusiness = tvNo.getText().toString();
        strPlatform=tvBusinessNo.getText().toString();
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                tvRating.setText(String.valueOf(v));
            }
        });
        Confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPersonalData();
            }
        });

        return view;
    }

    @OnCheckedChanged(R.id.cb_others)
    public void checkOthers() {
        if (cbOthers.isChecked()) {
            etComments.setVisibility(View.VISIBLE);
        } else {
            etComments.setVisibility(View.GONE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                commonDialog.showPermissionAlert(getActivity(), "This Application need Camera and stoarge Permissions", this);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case RequestCodes.CAMERA:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    setImage(photo);
                    break;
                case RequestCodes.STORAGE:
                    Uri selectedUri = data.getData();
                    String filepath = FilePath.getPath(getActivity(), selectedUri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    setImage(bitmap);
                    break;
            }
        } catch (Exception e) {
            Log.d("error", e.getMessage());

        }
    }

    @Override
    public void commonClick(int code) {
        switch (code) {
            case RequestCodes.CAMERA:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, RequestCodes.CAMERA);
                break;
            case RequestCodes.STORAGE:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, RequestCodes.STORAGE);
                break;
            case RequestCodes.CANCEL:
                isLayoutClicked = false;
                isImg1 = false;
                isImg2 = false;
                break;
        }
    }

    @OnClick({R.id.ll_register_no, R.id.img_register})
    public void onClickImage() {
        isLayoutClicked = true;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_pan_1)
    public void image1Clicked() {
        isImg1 = true;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_pan_2)
    public void image2Clicked() {
        isImg2 = true;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }
    @OnClick({R.id.linear_done_pan,R.id.img_done_pan})
    public void doneLayout(){
        if(etRegNo.getText().toString().isEmpty()){
            etRegNo.setError("Please enter your Registration Number");
        }
    }

    @OnClick(R.id.txt_yes)
    public void yesClick() {
        tvYes.setBackground(getResources().getDrawable(R.drawable.background_border_green));
        tvNo.setBackground(getResources().getDrawable(R.drawable.three_side_border));
        tvYes.setTextColor(getResources().getColor(R.color.white));
        tvNo.setTextColor(getResources().getColor(R.color.black));
        strBusiness = tvYes.getText().toString();
        etBusinessName.setVisibility(View.VISIBLE);
        llRegisterNo.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txt_no)
    public void noClick() {
        tvYes.setBackground(getResources().getDrawable(R.drawable.background_border));
        tvNo.setBackground(getResources().getDrawable(R.drawable.three_side_green_border));

        tvYes.setTextColor(getResources().getColor(R.color.black));
        tvNo.setTextColor(getResources().getColor(R.color.white));

        strBusiness = tvNo.getText().toString();
        etBusinessName.setVisibility(View.GONE);
        llRegisterNo.setVisibility(View.GONE);
    }

    @OnClick(R.id.txt_business_yes)
    public void yesBusinessClick() {
        tvBusinessYes.setBackground(getResources().getDrawable(R.drawable.background_border_green));
        tvBusinessNo.setBackground(getResources().getDrawable(R.drawable.three_side_border));
        tvBusinessYes.setTextColor(getResources().getColor(R.color.white));
        tvBusinessNo.setTextColor(getResources().getColor(R.color.black));
        llPlatform.setVisibility(View.VISIBLE);
        etPlatformName.setVisibility(View.VISIBLE);
        etProfileName.setVisibility(View.VISIBLE);
        strPlatform=tvBusinessYes.getText().toString();
    }

    @OnClick(R.id.txt_business_no)
    public void noBusinessClick() {
        tvBusinessYes.setBackground(getResources().getDrawable(R.drawable.background_border));
        tvBusinessNo.setBackground(getResources().getDrawable(R.drawable.three_side_green_border));
        tvBusinessYes.setTextColor(getResources().getColor(R.color.black));
        tvBusinessNo.setTextColor(getResources().getColor(R.color.white));
        llPlatform.setVisibility(View.GONE);
        etPlatformName.setVisibility(View.GONE);
        etProfileName.setVisibility(View.GONE);
        strPlatform=tvBusinessNo.getText().toString();
    }


    private void setImage(Bitmap photo) {
        if (isLayoutClicked) {
            llRegNo.setVisibility(View.GONE);
            llImagePan.setVisibility(View.VISIBLE);
            ivPan1.setImageBitmap(photo);
            isLayoutClicked = false;
            if (ivPan1.getDrawable() != null && ivPan2.getDrawable() != null) {
                llDoneLayout.setVisibility(View.VISIBLE);
            } else {
                llDoneLayout.setVisibility(View.GONE);
            }
        } else if (isImg1) {
            ivPan1.setImageBitmap(photo);
            if (ivPan1.getDrawable() != null && ivPan2.getDrawable() != null) {
                llDoneLayout.setVisibility(View.VISIBLE);
            } else {
                llDoneLayout.setVisibility(View.GONE);
            }
            isImg1 = false;
        } else if (isImg2) {
            ivPan2.setImageBitmap(photo);
            ivPan2.setBackground(null);
            if (ivPan1.getDrawable() != null && ivPan2.getDrawable() != null) {
                llDoneLayout.setVisibility(View.VISIBLE);
            } else {
                llDoneLayout.setVisibility(View.GONE);
            }
            isImg2 = false;
        }
    }




    /*******************************personal data********************/
    private void uploadPersonalData() {


        JSONObject firm = new JSONObject();

        try {
            firm.put("running_business","Home_Chef");
            firm.put( "registed_business",strBusiness);
            firm.put("registration_no","47859");
            firm.put("available_in_any_platform",strPlatform);
            firm.put("paltform_type","Online");
            firm.put("platfrom_name",etPlatformName.getText().toString());
            firm.put("profile_link","https://www.zomato.com/bangalore");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Constant.FirmObject=firm.toString();

        Log.e("payapplidetails", "payapplidetails: "+Constant.FirmObject);



        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
          builder.addFormDataPart("mom_profile_pic",PersonalFragment.fileProfile.getName(), RequestBody.
                create(MediaType.parse("multipart/form-data"),PersonalFragment.fileProfile));

        Log.e("PersonalFragment******", "PersonalFragmentfile: "+PersonalFragment.fileProfile.getName());
        builder.addFormDataPart("full_name", Constant.FullName);
        builder.addFormDataPart("dob", Constant.DOB);
        builder.addFormDataPart("mobile_number", Constant.MobileNumber);
        builder.addFormDataPart("email", Constant.Email);
        builder.addFormDataPart("gender", Constant.Gender);
        builder.addFormDataPart("nationality", Constant.Nationality);
        builder.addFormDataPart("addresses", Constant.Addressobject);
        builder.addFormDataPart("payments", Constant.PaymentObject);
        builder.addFormDataPart("firm", Constant.FirmObject);
        MultipartBody requestBody = builder.build();
        Log.d("requestBody", "requestBody"+requestBody);
        apiService.applyregistration(requestBody).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Log.d("responseCode", response.code()+"");
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        Log.d("responseCode", "hgvghvgv"+object);
                        String status = object.getString("status");

                        String messagestr = object.getString("message");
                        Log.d("messagestr&&&", "hgvghvgv"+messagestr);
                        Toast.makeText(getActivity(), "Applied Successfully", Toast.LENGTH_SHORT).show();
                        Constant.DOCUMENTID=messagestr;
                          } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());
                        String message = object.getString("message");
                        //
                        Log.d("fail", message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                Log.d("fail123", "FAIL"+t.getMessage());
            }
        });
    }






/*
    *//*******************************personal data********************//*
    private void uploadPersonalData() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("pancard_front_image",fileProfile.getName(), RequestBody.
                create(MediaType.parse("multipart/form-data"),PersonalFragment.fileProfile));
        builder.addFormDataPart("address_proof",fileAadhar.getName(), RequestBody.
                create(MediaType.parse("multipart/form-data"),fileAadhar));
        builder.addFormDataPart("gst_proof",filegst.getName(), RequestBody.
                create(MediaType.parse("multipart/form-data"),filegst));
        builder.addFormDataPart("fssai_food_license",filefoodlicense.getName(), RequestBody.
                create(MediaType.parse("multipart/form-data"),filefoodlicense));
        builder.addFormDataPart("other_docs",fileothers.getName(), RequestBody.
                create(MediaType.parse("multipart/form-data"),fileothers));
        Log.e("PersonalFragment******", "PersonalFragmentfile: "+fileProfile.getName()+fileothers.getName());
        builder.addFormDataPart("mom_id","a9a221694922cc179f745c2cd372a70e");
        MultipartBody requestBody = builder.build();
        Log.d("requestBody", "requestBody"+requestBody);
        apiService.uploaddocuments(requestBody).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Log.d("responseCode", response.code()+"");
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        Log.d("responseCode", "hgvghvgv"+object);
                        String status = object.getString("status");

                        String messagestr = object.getString("message");
                        Log.d("messagestr&&&", "hgvghvgv"+messagestr);

                        Constant.DOCUMENTID=messagestr;
                        //{"message":"a9a221694922cc179f745c2cd372a70e","status":"success","status code":201}
                        //Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTYxODgyNzk0OCwianRpIjoiNWQwY2UyYTEtZGUwZi00MGIwLWFkM2YtMDEzOTMxMmJjMzU5IiwibmJmIjoxNjE4ODI3OTQ4LCJ0eXBlIjoiYWNjZXNzIiwic3ViIjoiM2Y3NzljYTM5YmY0NzgyODliNjIwZWYzY2M5ZjE5MDUiLCJleHAiOjE2MjE0MTk5NDh9.ILSm9JH-3_EE9Ad1DwrVdHhV_OpYVvHljGiYWzoqRZs
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());
                        String message = object.getString("message");
                        //
                        Log.d("fail", message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                Log.d("fail123", "FAIL"+t.getMessage());
            }
        });
    }*/

}
