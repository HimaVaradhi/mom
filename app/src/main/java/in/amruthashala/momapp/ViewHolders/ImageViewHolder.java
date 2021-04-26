package in.amruthashala.momapp.ViewHolders;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.screens.CreateProduct;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    ImageView ivClose,ivImage;


    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        ivClose=itemView.findViewById(R.id.iv_close);
        ivImage=itemView.findViewById(R.id.iv_image);
    }

    public void bind(ArrayList<Bitmap> bitmaps, int position, Context context,ImageViewHolder holder){
        ivImage.setImageBitmap(bitmaps.get(position));
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CreateProduct)context).removeItem(position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CreateProduct)context).viewImage(position);
            }
        });
    }
}
