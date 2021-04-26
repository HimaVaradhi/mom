package in.amruthashala.momapp.screens;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import in.amruthashala.momapp.Adapaters.OrdersOverviewAdapater;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;

public class Orders extends BaseClass {

    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    @BindView(R.id.recyler_view)
    RecyclerView rc_orders;
    LinearLayoutManager linearLayoutManager;
    OrdersOverviewAdapater ordersOverviewAdapater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        tbToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Orders Overview");
        tbToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        linearLayoutManager=new LinearLayoutManager(this);
        rc_orders.setLayoutManager(linearLayoutManager);
        ordersOverviewAdapater=new OrdersOverviewAdapater(this);
        rc_orders.setAdapter(ordersOverviewAdapater);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_orders;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

    @OnClick({R.id.ll_pending,R.id.iv_pending,R.id.txt_pending,R.id.txt_pending_label})
    public void onClickPending(){
        startActivity(new Intent(this,PendingOrders.class));
    }

    @OnClick({R.id.ll_in_production,R.id.iv_in_production,R.id.txt_in_production,R.id.txt_in_production_label})
    public void onClickInProduction(){
        Intent intent=new Intent(this,InProduction.class);
        intent.putExtra("position",1);
        startActivity(intent);
    }
    @OnClick({R.id.ll_shipping,R.id.iv_shipping,R.id.txt_shipping,R.id.txt_shipping_label})
    public void onClickShipping(){
        Intent intent=new Intent(this,InProduction.class);
        intent.putExtra("position",3);
        startActivity(intent);
    }
    @OnClick({R.id.ll_r_shipped,R.id.txt_r_shipped,R.id.iv_r_shipped,R.id.txt_r_shipped_label})
    public void onClickShipped(){
        Intent intent=new Intent(this,InProduction.class);
        intent.putExtra("position",2);
        startActivity(intent);
    }
    @OnClick({R.id.ll_cancelled,R.id.iv_cancelled,R.id.txt_cancelled,R.id.txt_cancelled_label})
    public void onClickedCancelled(){
        startActivity(new Intent(this,CancelledOrders.class));
    }
}
