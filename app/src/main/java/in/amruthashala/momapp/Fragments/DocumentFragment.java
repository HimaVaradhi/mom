package in.amruthashala.momapp.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.amruthashala.momapp.Interfaces.CommonClick;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.CommonDialog;
import in.amruthashala.momapp.common.Constant;
import in.amruthashala.momapp.common.FileCompressor;
import in.amruthashala.momapp.common.FilePath;
import in.amruthashala.momapp.common.PermissionCheck;
import in.amruthashala.momapp.common.RequestCodes;
import in.amruthashala.momapp.retrofit.APIService;
import in.amruthashala.momapp.retrofit.ApiUtils;
import in.amruthashala.momapp.screens.CreateProduct;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class DocumentFragment extends Fragment implements CommonClick {
    PermissionCheck permissionCheck;
    CommonDialog commonDialog;
    @BindView(R.id.linear_pan_layout)
    LinearLayout liPanLayout;
    @BindView(R.id.linear_done_pan)
    LinearLayout liPanDoneLayout;
    @BindView(R.id.image_pan)
    LinearLayout liPanEditLayout;
    @BindView(R.id.image_pan_1)
    ImageView ivPan1;
    @BindView(R.id.image_pan_2)
    ImageView ivPan2;
    @BindView(R.id.linear_aadhar_layout)
    LinearLayout liAadharLayout;
    @BindView(R.id.linear_done_aadhar)
    LinearLayout liAadharDoneLayout;
    @BindView(R.id.linear_edit_aadhar)
    LinearLayout liAadharEditLayout;
    @BindView(R.id.image_aadhar_1)
    ImageView ivAadhar1;
    @BindView(R.id.image_aadhar_2)
    ImageView ivAadhar2;
    @BindView(R.id.linear_fssai_layout)
    LinearLayout llFssaiLayout;
    @BindView(R.id.linear_edit_fssai)
    LinearLayout llFssaiEditLayout;
    @BindView(R.id.image_fssai_1)
    ImageView ivFssai1;
    @BindView(R.id.image_fssai_2)
    ImageView ivFssai2;
    @BindView(R.id.linear_done_fssai)
    LinearLayout llDoneFssai;
    @BindView(R.id.linear_gst_layout)
    LinearLayout llGstLayout;
    @BindView(R.id.linear_edit_gst)
    LinearLayout liGstEditLayout;
    @BindView(R.id.image_gst_1)
    ImageView ivGst1;
    @BindView(R.id.image_gst_2)
    ImageView ivGst2;
    @BindView(R.id.linear_done_gst)
    LinearLayout llDoneGst;
    @BindView(R.id.linear_other_layout)
    LinearLayout llOther;
    @BindView(R.id.linear_edit_other)
    LinearLayout llOtherEdit;
    @BindView(R.id.image_other_1)
    ImageView ivOther1;
    @BindView(R.id.image_other_2)
    ImageView ivOther2;
    @BindView(R.id.linear_done_other)
    LinearLayout llOtherDone;
    @BindView(R.id.et_pan)
    EditText etPan;
    @BindView(R.id.et_aadhar)
    EditText etAadhar;
    @BindView(R.id.et_gst)
    EditText etGST;
    @BindView(R.id.et_fssai)
    EditText etFssai;
    @BindView(R.id.et_other)
    EditText etOther;
    @BindView(R.id.ll_save_layout)
    LinearLayout ll_save_layout;
    private static final int PICK_IMAGE = 100;
    boolean isPanClicked = false, isAadharClicked = false, isGstClicked = false, isFssaiClicked = false, isOtherClicked = false;
    boolean isPan1Clicked = false, isPan2Cliked = false, isAadhar1Clicked = false, isAadhar2Clicked = false, isFssai1Clicked = false, isFssai2Cliked = false, isGst1Clicked = false, isGst2Clicked = false, isOther1Clicked = false, isOther2Clicked = false;
    private File mPhotoFile;
APIService apiService=ApiUtils.getAPIService();
    FileCompressor mCompressor;
    String imagecase = "A";
    private File fileProfile,fileProfile1, fileAadhar, filegst, filefoodlicense, fileothers;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.document_sign_up, container, false);
        ButterKnife.bind(this, view);
        permissionCheck = new PermissionCheck();
        commonDialog = new CommonDialog();
        mCompressor = new FileCompressor(getActivity());
        //apiService = ApiUtils.getAPIService();
        liPanDoneLayout.setVisibility(View.GONE);
        liAadharDoneLayout.setVisibility(View.GONE);
        llDoneFssai.setVisibility(View.GONE);
        llDoneGst.setVisibility(View.GONE);
        llOtherDone.setVisibility(View.GONE);
        return view;
    }
    @OnClick({R.id.ll_save_layout})
    public void SaveClick() {
        uploadPersonalData();
    }
    @Override
    public void commonClick(int code) {
        switch (code) {
            case RequestCodes.CAMERA:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, RequestCodes.CAMERA);
                break;
            case RequestCodes.STORAGE:
                /*Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, RequestCodes.STORAGE);*/
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, RequestCodes.STORAGE);
                break;
            case RequestCodes.CANCEL:
                isPanClicked = false;
                isAadharClicked = false;
                isGstClicked = false;
                isFssaiClicked = false;
                isOtherClicked = false;
                isPan1Clicked = false;
                isPan2Cliked = false;
                isAadhar1Clicked = false;
                isAadhar2Clicked = false;
                isFssai1Clicked = false;
                isFssai2Cliked = false;
                isGst1Clicked = false;
                isGst2Clicked = false;
                isOther1Clicked = false;
                isOther2Clicked = false;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case RequestCodes.CAMERA:
                   /* Bitmap photo = (Bitmap) data.getExtras().get("data");
                    setImage(photo);*/
                    try {
                        mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                        setImage(mPhotoFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case RequestCodes.STORAGE:
                    Uri selectedImage = data.getData();
                    try {
                        mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                        setImage(mPhotoFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                  /*  Uri selectedUri = data.getData();
                    String filepath = FilePath.getPath(getActivity(), selectedUri);
                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    setImage(bitmap);*/
                    break;
            }
        } catch (Exception e) {
            Log.d("error", e.getMessage());
            isPanClicked = false;
            isAadharClicked = false;
            isGstClicked = false;
            isFssaiClicked = false;
            isOtherClicked = false;
            isPan1Clicked = false;
            isPan2Cliked = false;
            isAadhar1Clicked = false;
            isAadhar2Clicked = false;
            isFssai1Clicked = false;
            isFssai2Cliked = false;
            isGst1Clicked = false;
            isGst2Clicked = false;
            isOther1Clicked = false;
            isOther2Clicked = false;

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

    @OnClick({R.id.linear_pan_layout})
    public void PanClick() {
        isPanClicked = true;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_pan_1)
    public void panImageClik() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = true;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_pan_2)
    public void panImage2Clik() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = true;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick({R.id.linear_aadhar_layout})
    public void aadharClick() {
        isPanClicked = false;
        isAadharClicked = true;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_aadhar_1)
    public void Aadhar1Clik() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = true;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_aadhar_2)
    public void aadharImage2Clik() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = true;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick({R.id.linear_gst_layout})
    public void gstClick() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = true;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_gst_1)
    public void gstImageClik() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = true;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_gst_2)
    public void gstImage2Clik() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = true;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick({R.id.linear_fssai_layout})
    public void FssaiClick() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = true;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_fssai_1)
    public void fssaiImageClik() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = true;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_fssai_2)
    public void fssaiImage2Clik() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = true;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick({R.id.linear_other_layout})
    public void OtherClick() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = true;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_other_1)
    public void otherImageClik() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = true;
        isOther2Clicked = false;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick(R.id.image_other_2)
    public void otherImage2Clik() {
        isPanClicked = false;
        isAadharClicked = false;
        isGstClicked = false;
        isFssaiClicked = false;
        isOtherClicked = false;
        isPan1Clicked = false;
        isPan2Cliked = false;
        isAadhar1Clicked = false;
        isAadhar2Clicked = false;
        isFssai1Clicked = false;
        isFssai2Cliked = false;
        isGst1Clicked = false;
        isGst2Clicked = false;
        isOther1Clicked = false;
        isOther2Clicked = true;
        if (permissionCheck.isCameraPermissionExist(getActivity()) && permissionCheck.isStoragePermissionExist(getActivity())) {
            commonDialog.ImageUploadDialog(getActivity(), this);
        } else {
            permissionCheck.cameraGalleryRequestPermissionForFragment(this);
        }
    }

    @OnClick({R.id.linear_done_pan,R.id.img_done_pan})
    public void panDone(){
        if(etPan.getText().toString().isEmpty()){
            etPan.setError("Please enter PAN number");
        }
    }

    @OnClick({R.id.linear_done_aadhar,R.id.img_done_aadhar})
    public void aadharDone(){
        if(etAadhar.getText().toString().isEmpty()){
            etAadhar.setError("Please enter your document number");
        }
    }

    @OnClick({R.id.linear_done_fssai,R.id.img_done_fssai})
    public void fssaiDone(){
        if(etFssai.getText().toString().isEmpty()){
            etFssai.setError("Please enter your FSSAI number");
        }
    }

    @OnClick({R.id.linear_done_gst,R.id.img_done_gst})
    public void gstDone(){
        if(etGST.getText().toString().isEmpty()){
            etGST.setError("Please eneter your GST number");
        }
    }

    @OnClick({R.id.linear_other_layout,R.id.img_done_other})
    public void otherDone(){
        if(etOther.getText().toString().isEmpty()){
            etOther.setError("Please enter your Document number");
        }
    }




    public void setImage(File mPhotoFile) {
        if (isPanClicked) {
            liPanLayout.setVisibility(View.GONE);
            liPanEditLayout.setVisibility(View.VISIBLE);
            fileProfile = mPhotoFile;
            Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ivPan1);

          //  ivPan1.setImageBitmap(bitmap);
            isPanClicked = false;
            if (ivPan1.getDrawable() != null && ivPan2.getDrawable() != null) {
                liPanDoneLayout.setVisibility(View.GONE);
            }
        } else if (isPan1Clicked) {
            //ivPan1.setImageBitmap(bitmap);
            fileProfile= mPhotoFile;
            Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ivPan1);

            isPan1Clicked = false;
            if (ivPan1.getDrawable() != null && ivPan2.getDrawable() != null) {
                liPanDoneLayout.setVisibility(View.GONE);
            }
        } else if (isPan2Cliked) {
            fileProfile1= mPhotoFile;
            Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ivPan2);

           // ivPan2.setImageBitmap(bitmap);
            ivPan2.setBackground(null);
            isPan2Cliked = false;
            if (ivPan1.getDrawable() != null && ivPan2.getDrawable() != null) {
                liPanDoneLayout.setVisibility(View.GONE);
            }
        } else if (isAadharClicked) {
            liAadharLayout.setVisibility(View.GONE);
            liAadharEditLayout.setVisibility(View.VISIBLE);
            fileAadhar= mPhotoFile;
            Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ivAadhar1);

            // ivAadhar1.setImageBitmap(bitmap);
            isAadharClicked = false;
            if (ivAadhar1.getDrawable() != null) {
                liAadharDoneLayout.setVisibility(View.GONE);
                ivAadhar2.setVisibility(View.GONE);
            }
        }else if (isAadhar1Clicked) {
            fileAadhar= mPhotoFile;
            Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ivAadhar1);
            isAadhar1Clicked = false;
        } /*else if (isAadhar2Clicked) {

            ivAadhar2.setImageBitmap(bitmap);
            ivAadhar2.setBackground(null);
            isAadhar2Clicked = false;
            if (ivAadhar1.getDrawable() != null && ivAadhar2.getDrawable() != null) {
                liAadharDoneLayout.setVisibility(View.VISIBLE);
            }
        }*/ else if (isGstClicked) {
            llGstLayout.setVisibility(View.GONE);
            liGstEditLayout.setVisibility(View.VISIBLE);
            filegst= mPhotoFile;
            Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ivGst1);

           // ivGst1.setImageBitmap(bitmap);
            isGstClicked = false;
            if (ivGst1.getDrawable() != null) {
                llDoneGst.setVisibility(View.GONE);
                ivGst2.setVisibility(View.GONE);
            }
        } else if (isGst1Clicked) {
            filegst= mPhotoFile;
            Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ivGst1);

            isGst1Clicked = false;
            if (ivGst1.getDrawable() != null && ivGst2.getDrawable() != null) {
                llDoneGst.setVisibility(View.GONE);
            }
        } /*else if (isGst2Clicked) {
            ivGst2.setImageBitmap(bitmap);
            ivGst2.setBackground(null);
            isGst2Clicked = false;
            if (ivGst1.getDrawable() != null && ivGst2.getDrawable() != null) {
                llDoneGst.setVisibility(View.VISIBLE);
            }
        }*/
        else if (isFssaiClicked) {
            llFssaiLayout.setVisibility(View.GONE);
            llFssaiEditLayout.setVisibility(View.VISIBLE);
            filefoodlicense= mPhotoFile;
            Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ivFssai1);

            //  ivFssai1.setImageBitmap(bitmap);
            isFssaiClicked = false;
            if (ivFssai1.getDrawable() != null) {
                llDoneFssai.setVisibility(View.GONE);
                ivFssai2.setVisibility(View.GONE);
            }
        }
        else if (isFssai1Clicked) {
            filefoodlicense= mPhotoFile;
            Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ivFssai1);
            isFssai1Clicked = false;
            if (ivFssai1.getDrawable() != null && ivFssai2.getDrawable() != null) {
                llDoneFssai.setVisibility(View.VISIBLE);
            }
        }/* else if (isFssai2Cliked) {
            ivFssai2.setImageBitmap(bitmap);
            ivFssai2.setBackground(null);
            isFssai2Cliked = false;
            if (ivFssai1.getDrawable() != null && ivFssai2.getDrawable() != null) {
                llDoneFssai.setVisibility(View.VISIBLE);
            }
        }*/ else if (isOtherClicked) {
            llOther.setVisibility(View.GONE);
            llOtherEdit.setVisibility(View.VISIBLE);
            fileothers= mPhotoFile;
            Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ivOther1);

            //  ivOther1.setImageBitmap(bitmap);
            isOtherClicked = false;
            if (ivOther1.getDrawable() != null) {
                llOtherDone.setVisibility(View.GONE);
                ivOther2.setVisibility(View.GONE);
            }
        } else if (isOther1Clicked) {
            fileothers= mPhotoFile;
            Glide.with(getActivity()).load(mPhotoFile).apply(new RequestOptions().placeholder(R.drawable.profile_pic_place_holder)).into(ivOther1);
            ivOther1.setBackground(null);
            isOther1Clicked = false;
            if (ivOther1.getDrawable() != null) {
                llOtherDone.setVisibility(View.GONE);
            }
        } /*else if (isOther2Clicked) {
            ivOther2.setImageBitmap(bitmap);
            ivOther2.setBackground(null);
            isOther2Clicked = false;
            if (ivOther1.getDrawable() != null && ivOther2.getDrawable() != null) {
                llOtherDone.setVisibility(View.VISIBLE);
            }
        }*/
    }
  /////////////////

   /* D/OkHttp: <-- HTTP FAILED: java.net.ProtocolException: HTTP 205 had non-zero Content-Length: 72
    D/NativeCrypto: ssl=0x7336526f80 NativeCrypto_SSL_interrupt
    D/fail123: FAILHTTP 205 had non-zero Content-Length: 72*/

    private void uploadPersonalData() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
      builder.addFormDataPart("mom_id",Constant.DOCUMENTID);
    // builder.addFormDataPart("mom_id","9d40c445eda7db472e356741f2be00a9");
       // File file = new File("/storage/emulated/0/Download/scan0003.jpg");
        if (fileProfile == null) {

        } else {
            builder.addFormDataPart("pancard_front_image",fileProfile.getName(), RequestBody.
                    create(MediaType.parse("multipart/form-data"),fileProfile));
        }

        if (fileProfile1 == null) {

        } else {
            builder.addFormDataPart("pancard_back_image",fileProfile1.getName(), RequestBody.
                    create(MediaType.parse("multipart/form-data"),fileProfile1));
        }

        if (fileAadhar== null) {

        } else {
            builder.addFormDataPart("address_proof",fileAadhar.getName(), RequestBody.
                    create(MediaType.parse("multipart/form-data"),fileAadhar));
        }

        if (filegst == null) {

        } else {
            builder.addFormDataPart("gst_proof",filegst.getName(), RequestBody.
                    create(MediaType.parse("multipart/form-data"),filegst));
        }

        if (filefoodlicense == null) {

        } else {
            builder.addFormDataPart("fssai_food_license",filefoodlicense.getName(), RequestBody.
                    create(MediaType.parse("multipart/form-data"),filefoodlicense));

        }if (fileothers == null) {

        } else {
            builder.addFormDataPart("other_docs",fileothers.getName(), RequestBody.
                    create(MediaType.parse("multipart/form-data"),fileothers));

        }
      //  Log.e("PersonalFragment******", "PersonalFragmentfile: "+fileProfile.getName()+fileothers.getName()+fileProfile1.getName()+filefoodlicense.getName()+fileAadhar.getName()+filegst.getName());
        MultipartBody requestBody = builder.build();
        Log.d("requestBody", "requestBody"+requestBody);
        apiService.uploaddocuments(requestBody).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call,Response<Object> response) {

                Log.d("responseCode", response.code()+"");
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        Log.d("responseCode", "hgvghvgv"+object);
                        String status = object.getString("status");
                        String messagestr = object.getString("message");
                        Log.d("messagestr&&&", "hgvghvgv"+messagestr);
                        Toast.makeText(getActivity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());
                        String message = object.getString("message");
                        //
                        Log.d("fail", message);
                        if(message.contains("FAILHTTP 205 had non-zero Content-Length: 72")){
                            Toast.makeText(getActivity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                Log.d("fail123", "FAIL"+t.getMessage());
                if(t.getMessage().contains("FAILHTTP 205 had non-zero Content-Length: 72")){
                    Toast.makeText(getActivity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
