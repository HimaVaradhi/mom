package in.amruthashala.momapp.screens;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import in.amruthashala.momapp.Adapaters.InstantItemAdapater;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;

public class ViewPendingOrder extends BaseClass {

    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_pending)
    LinearLayout llPending;
    @BindView(R.id.ll_tracking_details)
    LinearLayout llTracking;
    @BindView(R.id.ll_cancelled)
    LinearLayout llCancelled;
    @BindView(R.id.txt_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.ll_ready_to_ship)
    LinearLayout llReadyToShip;
    @BindView(R.id.ll_shipping)
    LinearLayout llShipping;
    LinearLayoutManager linearLayoutManager;
    InstantItemAdapater instantItemAdapater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        tbToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Details");
        tbToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        linearLayoutManager = new LinearLayoutManager(this);
        instantItemAdapater = new InstantItemAdapater(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(instantItemAdapater);
        if (getIntent().getIntExtra("status", 0) == 1) {
            llPending.setVisibility(View.VISIBLE);
            llTracking.setVisibility(View.GONE);
            llCancelled.setVisibility(View.GONE);
            llReadyToShip.setVisibility(View.GONE);
            llShipping.setVisibility(View.GONE);
            tvOrderStatus.setText("Pending");
        } else if (getIntent().getIntExtra("status", 0) == 2) {
            llPending.setVisibility(View.GONE);
            llTracking.setVisibility(View.VISIBLE);
            llCancelled.setVisibility(View.GONE);
            llReadyToShip.setVisibility(View.GONE);
            llShipping.setVisibility(View.VISIBLE);
            tvOrderStatus.setText("Shipped");
        } else if (getIntent().getIntExtra("status", 0) == 3) {
            llPending.setVisibility(View.GONE);
            llTracking.setVisibility(View.VISIBLE);
            llCancelled.setVisibility(View.VISIBLE);
            llShipping.setVisibility(View.VISIBLE);
            tvOrderStatus.setText("Canceled");
            llReadyToShip.setVisibility(View.GONE);
        } else {
            llPending.setVisibility(View.GONE);
            llTracking.setVisibility(View.GONE);
            llCancelled.setVisibility(View.GONE);
            tvOrderStatus.setText("Order Accepted");
            llShipping.setVisibility(View.GONE);
            llReadyToShip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_order_details;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }
}
