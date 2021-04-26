package in.amruthashala.momapp.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.screens.ViewPendingOrder;

public class CancelledViewHolder extends RecyclerView.ViewHolder {

    ImageView ivProductImage;

    public CancelledViewHolder(@NonNull View itemView) {
        super(itemView);
        ivProductImage = itemView.findViewById(R.id.img_product);
    }

    public void bind(Context context,CancelledViewHolder cancelledViewHolder){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivProductImage.setClipToOutline(true);
        }
        cancelledViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ViewPendingOrder.class);
                intent.putExtra("status",3);
                context.startActivity(intent);
            }
        });
    }
}
