package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.MyProductListingHolder;
import in.amruthashala.momapp.ViewHolders.VHolder;
import in.amruthashala.momapp.inputresponsemodel.ProductVariant;
import in.amruthashala.momapp.requestmodel.GetProductModel;

public class VaraintAdapter extends RecyclerView.Adapter<VaraintAdapter.myViewHolder> {

    private Context context;
    private List<ProductVariant> varaints;
    private String recyclerTag;
    public VaraintAdapter(Context context, List<ProductVariant> varaints){
        this.context = context;
        this.varaints = varaints;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_productsv,parent,false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        ProductVariant s = varaints.get(position);
        holder.tv_stock.setText(s.getProductStock()+" ");


    }

    public void updateList(List<ProductVariant> varaints)
    {
        varaints = varaints;
        notifyDataSetChanged();
    }
    public void addItem(int position, ProductVariant stud)
    {
        varaints.add(position, stud);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return varaints.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView tv_stock;

        public myViewHolder(View itemView) {
            super(itemView);

            tv_stock=itemView.findViewById(R.id.tv_stock);

        }
    }

}