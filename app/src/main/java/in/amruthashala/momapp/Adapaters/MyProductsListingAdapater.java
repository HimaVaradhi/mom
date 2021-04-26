package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.MyProductListingHolder;
import in.amruthashala.momapp.requestmodel.GetProductModel;

public class MyProductsListingAdapater extends RecyclerView.Adapter<MyProductListingHolder> {

    Context context;
    int selectedItem = 0;
    ArrayList<GetProductModel> productlist;
    public MyProductsListingAdapater(Context context,ArrayList<GetProductModel> productlist) {
        this.context = context;
        this.productlist=productlist;
    }
    @NonNull
    @Override
    public MyProductListingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyProductListingHolder(LayoutInflater.from(context).inflate(R.layout.item_my_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductListingHolder holder, int position) {
     holder.bind(selectedItem,context);
        holder.tv_productname.setText(productlist.get(position).getProductname());
        holder.tv_productfranchise.setText(productlist.get(position).getProductfranchise());
        holder.tv_productprice.setText(productlist.get(position).getProductprice());
        holder.txt_active.setText((productlist.get(position).getProductstatus()));
        holder.tv_productstock.setText((productlist.get(position).getProductstock()));
        holder.tv_productid.setText((productlist.get(position).getProductid()));
    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }

    public void updateItem(int selectedItem){
        this.selectedItem=selectedItem;
        notifyDataSetChanged();
    }
}
