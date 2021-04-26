package in.amruthashala.momapp.Fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.hbb20.CountryCodePicker;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import in.amruthashala.momapp.BuildConfig;
import in.amruthashala.momapp.Interfaces.CommonClick;
import in.amruthashala.momapp.Presenter.PersonalFragmentPresenter;
import in.amruthashala.momapp.PresentersImpl.PersonalFragmentPresenterImpl;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewListners.PersonalFragmentViewListner;
import in.amruthashala.momapp.common.CommonDialog;
import in.amruthashala.momapp.common.Constant;
import in.amruthashala.momapp.common.DatePickerDialogFragment;
import in.amruthashala.momapp.common.FileCompressor;
import in.amruthashala.momapp.common.FilePath;
import in.amruthashala.momapp.common.PermissionCheck;
import in.amruthashala.momapp.common.RequestCodes;

import static android.app.Activity.RESULT_OK;

public class PersonalFragment extends Fragment implements PersonalFragmentViewListner, CommonClick, View.OnClickListener {

    @BindView(R.id.linear_edit_image)
    LinearLayout llEditImage;
    @BindView(R.id.circular_image)
    CircleImageView ciProfilePicture;
    PersonalFragmentPresenter personalFragmentPresenter;
    PermissionCheck permissionCheck;
    CommonDialog commonDialog;
    @BindView(R.id.et_fullname)
    EditText etFullName;
    @BindView(R.id.et_father_name)
    EditText etFatherName;
    @BindView(R.id.et_dob)
    EditText etDOB;
    @BindView(R.id.et_mobile_number)
    EditText etMobileNumber;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.spinner_nation)
    Spinner spNation;
    @BindView(R.id.txt_male)
    TextView txtMale;
    @BindView(R.id.txt_female)
    TextView txtFemale;
    @BindView(R.id.txt_other)
    TextView txtOther;
    @BindView(R.id.ccp_picker)
    CountryCodePicker codePicker;
    @BindView(R.id.btn_send_otp)
    AppCompatButton acSendOtp;
    @BindView(R.id.btn_send_otp_email)
    AppCompatButton acSendEmail;
    @BindView(R.id.ll_resend_otp_phone)
    LinearLayout llPhoneOtp;
    @BindView(R.id.ll_resend_otp_email)
    LinearLayout llEmailOtp;
    @BindView(R.id.sc_scroll)
    ScrollView scView;
    String strGender,strfullname,strfathername,stremail,strmobileno,strdob,strnationality;
    String strTempPhone="";
    String strTempEmail="";

    private File mPhotoFile;

    FileCompressor mCompressor;
    String imagecase = "A";
    public static File fileProfile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_sign_up, container, false);
        ButterKnife.bind(this, view);
        txtMale.setBackground(getResources().getDrawable(R.drawable.background_border_green));
        txtFemale.setBackground(getResources().getDrawable(R.drawable.three_side_border));
        txtOther.setBackground(getResources().getDrawable(R.drawable.three_side_border));
        txtMale.setTextColor(getResources().getColor(R.color.white));
        txtFemale.setTextColor(getResources().getColor(R.color.black));
        txtOther.setTextColor(getResources().getColor(R.color.black));
        acSendOtp.setOnClickListener(this);
        acSendEmail.setOnClickListener(this);
        strGender = txtMale.getText().toString();
        mCompressor = new FileCompressor(getActivity());
        etMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (codePicker.getTextView_selectedCountry().getText().toString().equals("+91")) {
                    if (charSequence.length() == 10) {
                        if(strTempPhone.equals(charSequence.toString())){
                            acSendOtp.setVisibility(View.GONE);
                            llPhoneOtp.setVisibility(View.VISIBLE);
                        }else {
                            acSendOtp.setVisibility(View.VISIBLE);
                            llPhoneOtp.setVisibility(View.GONE);
                        }
                    } else {
                        acSendOtp.setVisibility(View.GONE);
                    }
                } else {
                    if (charSequence.length() > 10) {
                        if(strTempPhone.equals(charSequence.toString())){
                            acSendOtp.setVisibility(View.GONE);
                            llPhoneOtp.setVisibility(View.VISIBLE);
                        }else {
                            acSendOtp.setVisibility(View.VISIBLE);
                            llPhoneOtp.setVisibility(View.GONE);
                        }
                    } else {
                        acSendOtp.setVisibility(View.GONE);
                        llPhoneOtp.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty()){
                    if (Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()) {
                        if(strTempEmail.equals(charSequence.toString())){
                            acSendEmail.setVisibility(View.GONE);
                            llEmailOtp.setVisibility(View.VISIBLE);
                        }else {
                            acSendEmail.setVisibility(View.VISIBLE);
                            llEmailOtp.setVisibility(View.GONE);
                        }

                    } else {
                        etEmail.setError("Invalid Email");
                        llEmailOtp.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (codePicker.getTextView_selectedCountry().getText().toString().equals("+91")) {
            etMobileNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        } else {
            etMobileNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        }
        codePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                etMobileNumber.setText("");
                if (codePicker.getTextView_selectedCountry().getText().toString().equals("+91")) {
                    etMobileNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                } else {
                    etMobileNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                }
            }
        });
        personalFragmentPresenter = new PersonalFragmentPresenterImpl(this, getActivity());
        permissionCheck = new PermissionCheck();
        commonDialog = new CommonDialog();
        codePicker.setDefaultCountryUsingNameCode("in");
        etDOB.setOnClickListener(this);
        return view;
    }


    @OnClick({R.id.linear_edit_image, R.id.img_edit})
    void onClickEditImage() {
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.ll_save_layout)
    void saveClick(){
        if(etFullName.getText().toString().isEmpty()){
            etFullName.setError("Please enter Full name");
            scView.scrollTo(0,0);
        }else if(etFatherName.getText().toString().isEmpty()){
            etFatherName.setError("Please enter Father name");
            scView.scrollTo(0,0);
        }else if(etDOB.getText().toString().isEmpty()){
            etDOB.setError("Please enter Date of Birth");
            scView.scrollTo(0,0);
        }else if(etMobileNumber.getText().toString().isEmpty()){
            etMobileNumber.setError("Please enter Mobile number");
        }else if(etEmail.getText().toString().isEmpty()){
            etEmail.setError("Please enter Email");
        }else if(strGender.isEmpty()){
            Toast.makeText(getActivity(),"Please Select Gender",Toast.LENGTH_LONG).show();
        }else if(ciProfilePicture.getDrawable()==null){
            Toast.makeText(getActivity(),"Please Uplode Profile picture",Toast.LENGTH_LONG).show();
            scView.scrollTo(0,0);
        }else{
            strfullname=etFullName.getText().toString();
            strfathername=etFatherName.getText().toString();
            strdob=etDOB.getText().toString();
            strmobileno=etMobileNumber.getText().toString();
            stremail=etEmail.getText().toString();
            strnationality=spNation.getSelectedItem().toString();
            Constant.FullName=strfullname;
            Constant.FatherName=strfathername;
            Constant.DOB=strdob;
            Constant.MobileNumber=strmobileno;
            Constant.Email=stremail;
            Constant.Gender=strGender;
            Constant.Nationality=strnationality;
            Log.e("applidetails", "applidetails: "+Constant.FullName+Constant.FatherName+Constant.DOB+Constant.MobileNumber+Constant.Email+Constant.Gender);
        }
    }
    @Override
    public void commonClick(int code) {
        switch (code) {
            case RequestCodes.CAMERA:
                dispatchTakePictureIntent();
                /*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, RequestCodes.CAMERA);*/
                break;
            case RequestCodes.STORAGE:
                dispatchGalleryIntent();
               /* Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, RequestCodes.STORAGE);*/
                break;
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


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }




    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.setType("image/*");
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, Constant.FROM_GALLERY);
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, Constant.FROM_CAMERA);

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.FROM_CAMERA && resultCode == RESULT_OK) {

            try {
                mPhotoFile = mCompressor.compressToFile(mPhotoFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (imagecase)
            {

                case "A":
                    setImageView(mPhotoFile);
                    break;

            }

        }

        if (requestCode == Constant.FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (imagecase)
            {

                case "A":
                    setImageView(mPhotoFile);
                    break;

            }



        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void setImageView(File mPhotoFile) {

        assert getActivity()!=null;

        switch (imagecase) {

            case "A":
                fileProfile = mPhotoFile;
                Glide.with(getActivity())
                        .load(mPhotoFile)
                        .centerCrop()
                        .placeholder(R.drawable.profile_pic_place_holder)
                        .into(ciProfilePicture);
               // Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ciProfilePicture);
                break;

        }

    }


    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @OnClick(R.id.txt_male)
    public void maleClick() {
        txtMale.setBackground(getResources().getDrawable(R.drawable.background_border_green));
        txtFemale.setBackground(getResources().getDrawable(R.drawable.three_side_border));
        txtOther.setBackground(getResources().getDrawable(R.drawable.three_side_border));
        txtMale.setTextColor(getResources().getColor(R.color.white));
        txtFemale.setTextColor(getResources().getColor(R.color.black));
        txtOther.setTextColor(getResources().getColor(R.color.black));
        strGender = txtMale.getText().toString();
    }


    @OnClick(R.id.txt_female)
    public void femaleClick() {
        txtMale.setBackground(getResources().getDrawable(R.drawable.background_border));
        txtFemale.setBackground(getResources().getDrawable(R.drawable.three_side_green_border));
        txtOther.setBackground(getResources().getDrawable(R.drawable.three_side_border));
        txtMale.setTextColor(getResources().getColor(R.color.black));
        txtFemale.setTextColor(getResources().getColor(R.color.white));
        txtOther.setTextColor(getResources().getColor(R.color.black));
        strGender = txtFemale.getText().toString();
    }

    @OnClick(R.id.txt_other)
    public void otherClick() {
        txtMale.setBackground(getResources().getDrawable(R.drawable.background_border));
        txtFemale.setBackground(getResources().getDrawable(R.drawable.three_side_border));
        txtOther.setBackground(getResources().getDrawable(R.drawable.three_side_green_border));
        txtMale.setTextColor(getResources().getColor(R.color.black));
        txtFemale.setTextColor(getResources().getColor(R.color.black));
        txtOther.setTextColor(getResources().getColor(R.color.white));
        strGender = txtOther.getText().toString();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_dob:
                DatePickerDialogFragment picker = new DatePickerDialogFragment(etDOB,"dd-MM-yyyy");
                picker.show(getFragmentManager(), "datePicker");
                break;
            case R.id.btn_send_otp:
                strTempPhone=etMobileNumber.getText().toString();
                acSendOtp.setVisibility(View.GONE);
                llPhoneOtp.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_send_otp_email:
                strTempEmail=etEmail.getText().toString();
                acSendEmail.setVisibility(View.GONE);
                llEmailOtp.setVisibility(View.VISIBLE);
                break;
        }

    }
}
