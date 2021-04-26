package in.amruthashala.momapp.ViewHolders;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.screens.CreateProduct;

public class VHolder extends RecyclerView.ViewHolder {
    public TextView tv_stock;


    public VHolder(@NonNull View itemView) {
        super(itemView);
        tv_stock=itemView.findViewById(R.id.tv_stock);

    }


}

