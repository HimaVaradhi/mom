package in.amruthashala.momapp.screens;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;

public class ViewPayoutOrder extends BaseClass implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    @BindView(R.id.spinner_order_status)
    Spinner spOrderStatus;
    @BindView(R.id.img_product)
    ImageView ivProductImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Id: 0019102020");
        String[] spinnerEntries={"Preparing","In Production","Complete"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivProductImage.setClipToOutline(true);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,spinnerEntries);
        adapter.setDropDownViewResource(R.layout.layout_spinner_text);
        spOrderStatus.setAdapter(adapter);
        spOrderStatus.setOnItemSelectedListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_view_payout_order;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView)adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
        if(i==1){
            spOrderStatus.setBackground(getResources().getDrawable(R.drawable.spinner_background_10));
        }else if(i==2) {
            spOrderStatus.setBackground(getResources().getDrawable(R.drawable.spinner_background_8));
        }else {
            spOrderStatus.setBackground(getResources().getDrawable(R.drawable.spinner_background_11));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }
}
