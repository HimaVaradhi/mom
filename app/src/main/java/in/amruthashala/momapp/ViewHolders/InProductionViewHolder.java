package in.amruthashala.momapp.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.screens.ViewPendingOrder;

public class InProductionViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {

    LinearLayout llProductionStatus,llOrderStatus;
    ImageView ivProductImage;
    Spinner spOrderStatus;
    Context context;
    int selectedItem;
    AppCompatButton btnPlay;
    TextView tvProductId;



    public InProductionViewHolder(@NonNull View itemView) {
        super(itemView);
        ivProductImage = itemView.findViewById(R.id.img_product);
        spOrderStatus = itemView.findViewById(R.id.spinner_order_status);
        llProductionStatus=itemView.findViewById(R.id.ll_production_status);
        llOrderStatus=itemView.findViewById(R.id.ll_order_status);
        btnPlay=itemView.findViewById(R.id.btn_play);
        tvProductId=itemView.findViewById(R.id.txt_product_id);
    }

    public void bind(Context context,int selectionItem,InProductionViewHolder inProductionViewHolder){
        this.context=context;
        selectedItem=selectionItem;
        if(selectionItem==1) {
            String[] spinnerEntries = {"Ready to ship", "Preparing", "In Production", "Complete"};
            this.context = context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivProductImage.setClipToOutline(true);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerEntries);
            adapter.setDropDownViewResource(R.layout.layout_spinner_text);
            spOrderStatus.setAdapter(adapter);
            llProductionStatus.setVisibility(View.VISIBLE);
            llOrderStatus.setVisibility(View.GONE);
            tvProductId.setBackground(context.getResources().getDrawable(R.drawable.borderblue));
            btnPlay.setVisibility(View.VISIBLE);
        }else if(selectionItem==0){
            String[] spinnerEntries = {"Preparing", "In Production", "Complete"};
            this.context = context;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivProductImage.setClipToOutline(true);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerEntries);
            adapter.setDropDownViewResource(R.layout.layout_spinner_text);
            spOrderStatus.setAdapter(adapter);
            spOrderStatus.setSelection(1);
            llProductionStatus.setVisibility(View.VISIBLE);
            llOrderStatus.setVisibility(View.GONE);
            tvProductId.setBackground(context.getResources().getDrawable(R.drawable.border_yellow));
            btnPlay.setVisibility(View.GONE);
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivProductImage.setClipToOutline(true);
            }
            llProductionStatus.setVisibility(View.GONE);
            llOrderStatus.setVisibility(View.VISIBLE);
            tvProductId.setBackground(context.getResources().getDrawable(R.drawable.border_green));
        }
        inProductionViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectionItem==2){
                    Intent intent=new Intent(context, ViewPendingOrder.class);
                    intent.putExtra("status",2);
                    context.startActivity(intent);
                }else if(selectionItem==1){
                    Intent intent=new Intent(context, ViewPendingOrder.class);
                    intent.putExtra("status",4);
                    context.startActivity(intent);
                }
            }
        });
        spOrderStatus.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(context.getResources().getColor(R.color.white));
        if(selectedItem==0){
            if(i==0){
                spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_9));
            }else if(i==1) {
                spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_10));
            }else {
                spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_8));
            }
        }else {
            if(i==1){
                spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_9));
                btnPlay.setVisibility(View.GONE);
            }else if(i==2) {
                spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_10));
                btnPlay.setVisibility(View.GONE);
            }else {
                spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_8));
                btnPlay.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
