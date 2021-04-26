package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.OrdersOverviewHolder;

public class OrdersOverviewAdapater extends RecyclerView.Adapter<OrdersOverviewHolder> {
    Context context;

    public OrdersOverviewAdapater(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public OrdersOverviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrdersOverviewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersOverviewHolder holder, int position) {
        holder.bind(context);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
