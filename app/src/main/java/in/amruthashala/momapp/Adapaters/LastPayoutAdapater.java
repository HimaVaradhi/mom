package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.LastPayoutHolder;

public class LastPayoutAdapater extends RecyclerView.Adapter<LastPayoutHolder> {
    Context context;

    public LastPayoutAdapater(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public LastPayoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LastPayoutHolder(LayoutInflater.from(context).inflate(R.layout.layout_last_payout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LastPayoutHolder holder, int position) {
        holder.bind(context, holder);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
