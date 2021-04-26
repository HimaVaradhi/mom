package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.OrderViewHolder;

public class OrdersAdapater extends RecyclerView.Adapter<OrderViewHolder> {

    Context context;

    public OrdersAdapater(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_item, parent, false);
        OrderViewHolder holder = new OrderViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
