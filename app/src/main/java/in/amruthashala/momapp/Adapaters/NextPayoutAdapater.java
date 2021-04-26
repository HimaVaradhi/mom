package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.NextPayoutHolder;

public class NextPayoutAdapater extends RecyclerView.Adapter<NextPayoutHolder> {

    Context context;

    public NextPayoutAdapater(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public NextPayoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NextPayoutHolder(LayoutInflater.from(context).inflate(R.layout.item_payout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NextPayoutHolder holder, int position) {
        holder.bind(context, holder);
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
