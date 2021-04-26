package in.amruthashala.momapp.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

public class FilePickers {

    public void getImageFromCamera(Activity activity) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, RequestCodes.CAMERA);
    }

    public void getImageFromGallery(Activity activity){
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, RequestCodes.STORAGE);
    }
}
