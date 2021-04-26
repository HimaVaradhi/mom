package in.amruthashala.momapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.OnClick;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;

public class ViewPayout extends BaseClass {

    @BindView(R.id.toolbar)
    Toolbar tbToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Item Id: 0019102020");

    }

    @Override
    public int getLayout() {
        return R.layout.activity_view_payout;
    }

    @OnClick(R.id.txt_view_order)
    public void viewOrderClick() {
        startActivity(new Intent(this, ViewPayoutOrder.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
