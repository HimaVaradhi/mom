package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.PendingOrderViewHolder;

public class PendingOverViewAdapater extends RecyclerView.Adapter<PendingOrderViewHolder> {
    Context context;

    public PendingOverViewAdapater(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public PendingOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PendingOrderViewHolder(LayoutInflater.from(context).inflate(R.layout.dashboard_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrderViewHolder holder, int position) {
        holder.bind(context,holder);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
