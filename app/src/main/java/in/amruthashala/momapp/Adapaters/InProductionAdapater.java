package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.InProductionViewHolder;

public class InProductionAdapater extends RecyclerView.Adapter<InProductionViewHolder> {

    Context context;
    int selectionItem = 0;

    public InProductionAdapater(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public InProductionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InProductionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_in_production, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InProductionViewHolder holder, int position) {
        holder.bind(context, selectionItem,holder);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void UpdateItem(int selectionItem) {
        this.selectionItem = selectionItem;
        notifyDataSetChanged();
    }
}
