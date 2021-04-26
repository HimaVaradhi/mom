package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.MenuListingHolder;

public class MenuListingAdapater extends RecyclerView.Adapter<MenuListingHolder> {
    Context context;

    public MenuListingAdapater(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MenuListingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_listing, parent, false);
        return new MenuListingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListingHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
