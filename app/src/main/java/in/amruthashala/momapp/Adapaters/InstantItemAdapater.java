package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.InstantItemHolder;

public class InstantItemAdapater extends RecyclerView.Adapter<InstantItemHolder> {
    Context context;

    public InstantItemAdapater(Context context){
        this.context=context;
    }


    @NonNull
    @Override
    public InstantItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstantItemHolder(LayoutInflater.from(context).inflate(R.layout.item_instant_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InstantItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
