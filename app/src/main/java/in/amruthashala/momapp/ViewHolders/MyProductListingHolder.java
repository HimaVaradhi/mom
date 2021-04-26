package in.amruthashala.momapp.ViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;

public class MyProductListingHolder extends RecyclerView.ViewHolder {


    ImageView ivBanner;
    public TextView tvActie,tv_productname,tv_productfranchise,tv_productstock,tv_productprice,txt_active,tv_productid;

    public MyProductListingHolder(@NonNull View itemView) {
        super(itemView);
        ivBanner=itemView.findViewById(R.id.img_active);
        tvActie=itemView.findViewById(R.id.txt_active);
        tv_productname=itemView.findViewById(R.id.tv_productname);
        tv_productfranchise=itemView.findViewById(R.id.tv_productfranchise);
        tv_productstock=itemView.findViewById(R.id.tv_productstock);
        tv_productprice=itemView.findViewById(R.id.tv_productprice);
        txt_active=itemView.findViewById(R.id.txt_active);
        tv_productid=itemView.findViewById(R.id.tv_productid);
    }

    public void bind(int selectedItem, Context context) {
       switch (selectedItem){
           case 0:
               ivBanner.setImageResource(R.drawable.ic_ribbon);
               tvActie.setBackgroundResource(R.drawable.border_blue);
               tvActie.setTextColor(context.getResources().getColor(R.color.black));
               tvActie.setText("ACTIVATE");
               break;
           case 1:
               ivBanner.setImageResource(R.drawable.ic_audit_pending);
               tvActie.setBackgroundResource(R.drawable.border_rectangle_orange);
               tvActie.setText("Audit Pending");
               tvActie.setTextColor(context.getResources().getColor(R.color.white));
               break;
           case 2:
               ivBanner.setImageResource(R.drawable.ic_approval_pending);
               tvActie.setBackgroundResource(R.drawable.border_rectangle_yellow);
               tvActie.setText("Approval Pending");
               tvActie.setTextColor(context.getResources().getColor(R.color.white));
               break;
           case 3:
               ivBanner.setImageResource(R.drawable.ic_draft);
               tvActie.setBackgroundResource(R.drawable.border_rectangle_blue);
               tvActie.setText("Draft");
               tvActie.setTextColor(context.getResources().getColor(R.color.white));
               break;
       }
    }
}
