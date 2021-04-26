package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.ImageViewHolder;

public class ImagesAdapater extends RecyclerView.Adapter<ImageViewHolder> {

    Context context;
    ArrayList<Bitmap> bitmaps;

    public ImagesAdapater(Context context, ArrayList<Bitmap> bitmaps) {
        this.context = context;
        this.bitmaps = bitmaps;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(bitmaps, position, context,holder);
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }
}
