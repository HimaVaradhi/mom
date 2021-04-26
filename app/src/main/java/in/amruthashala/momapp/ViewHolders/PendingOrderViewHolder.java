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

public class PendingOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    ImageView ivProduct;
    LinearLayout llButtons, llSpinner;
    AppCompatButton btnAccept, btnReject;
    Context context;
    Spinner spOrderStatus;


    public PendingOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        ivProduct = itemView.findViewById(R.id.img_product);
        llButtons = itemView.findViewById(R.id.ll_buttons);
        llSpinner = itemView.findViewById(R.id.ll_spinner);
        btnAccept = itemView.findViewById(R.id.btn_accept);
        btnReject = itemView.findViewById(R.id.btn_reject);
        spOrderStatus = itemView.findViewById(R.id.spinner_order_status);
    }

    public void bind(Context context,PendingOrderViewHolder pendingOrderViewHolder) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivProduct.setClipToOutline(true);
        }
        btnReject.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
        pendingOrderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ViewPendingOrder.class);
                intent.putExtra("status",1);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
                llButtons.setVisibility(View.GONE);
                llSpinner.setVisibility(View.VISIBLE);
                String[] spinnerEntries={"Preparing","In Production","Complete"};
                ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item,spinnerEntries);
                adapter.setDropDownViewResource(R.layout.layout_spinner_text);
                spOrderStatus.setAdapter(adapter);
                spOrderStatus.setOnItemSelectedListener(this);
                break;
            case R.id.btn_reject:
                btnAccept.setBackground(context.getResources().getDrawable(R.drawable.border_grey));
                btnReject.setBackground(context.getResources().getDrawable(R.drawable.borderrectangle));
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(context.getResources().getColor(R.color.white));
        if(i==0){
            spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_9));
        }else if(i==1) {
            spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_10));
        }else {
            spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_8));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
