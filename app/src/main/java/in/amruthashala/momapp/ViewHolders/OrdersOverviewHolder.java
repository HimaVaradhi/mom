package in.amruthashala.momapp.ViewHolders;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import in.amruthashala.momapp.R;

public class OrdersOverviewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {

    ImageView ivProductImage;
    Spinner spOrderStatus;
    Context context;


    public OrdersOverviewHolder(@NonNull View itemView) {
        super(itemView);
        ivProductImage = itemView.findViewById(R.id.img_product);
        spOrderStatus = itemView.findViewById(R.id.spinner_order_status);
    }

    public void bind(Context context) {
        String[] spinnerEntries={"Ready to ship","Preparing","In Production","Complete"};
        this.context=context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivProductImage.setClipToOutline(true);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item,spinnerEntries);
        adapter.setDropDownViewResource(R.layout.layout_spinner_text);
        spOrderStatus.setAdapter(adapter);
        spOrderStatus.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(context.getResources().getColor(R.color.white));
        if(i==1){
            spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_9));
        }else if(i==2) {
            spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_10));
        }else {
            spOrderStatus.setBackground(context.getResources().getDrawable(R.drawable.spinner_background_8));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
