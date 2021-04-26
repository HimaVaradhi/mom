package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.CancelledViewHolder;

public class CancelledAdapater extends RecyclerView.Adapter<CancelledViewHolder> {
    Context context;

    public CancelledAdapater(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public CancelledViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CancelledViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_cancelled, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CancelledViewHolder holder, int position) {
        holder.bind(context,holder);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
