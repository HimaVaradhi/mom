package in.amruthashala.momapp.ViewHolders;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;

public class MenuListingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView ivProduct, ivSelectProduct;
    TextView tvActive;
    int count = 1;


    public MenuListingHolder(@NonNull View itemView) {
        super(itemView);
        ivProduct = itemView.findViewById(R.id.img_item);
        ivSelectProduct = itemView.findViewById(R.id.img_active);
        tvActive = itemView.findViewById(R.id.txt_active);

    }

    public void bind() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivProduct.setClipToOutline(true);
        }
        ivSelectProduct.setOnClickListener(this);
        tvActive.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_active:
            case R.id.txt_active:
                count++;
                if (count % 2 == 0) {
                    ivSelectProduct.setImageResource(R.drawable.ic_ribbion_deactive);
                    tvActive.setText("Deactivate");
                    tvActive.setBackgroundResource(R.drawable.border_grey);
                } else {
                    ivSelectProduct.setImageResource(R.drawable.ic_ribbon);
                    tvActive.setText("Active");
                    tvActive.setBackgroundResource(R.drawable.border_blue);
                }
                break;

        }
    }
}
