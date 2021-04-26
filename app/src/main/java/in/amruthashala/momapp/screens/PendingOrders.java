package in.amruthashala.momapp.screens;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import in.amruthashala.momapp.Adapaters.PendingOverViewAdapater;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;

public class PendingOrders extends BaseClass {
    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    @BindView(R.id.txt_approved)
    TextView tvApproved;
    @BindView(R.id.txt_pending)
    TextView tvPending;
    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PendingOverViewAdapater pendingOverViewAdapater;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        tbToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pending Orders");
        tbToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        linearLayoutManager=new LinearLayoutManager(this);
        pendingOverViewAdapater=new PendingOverViewAdapater(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(pendingOverViewAdapater);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_pending_orders;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return true;
    }

    @OnClick(R.id.txt_approved)
    public void onClickApproved(){
        tvApproved.setBackground(getResources().getDrawable(R.drawable.button_shapelgreen));
        tvPending.setBackground(getResources().getDrawable(R.drawable.button_shape_disabled));
    }
    @OnClick(R.id.txt_pending)
    public void onClickPending(){
        tvPending.setBackground(getResources().getDrawable(R.drawable.button_shapeyellow));
        tvApproved.setBackground(getResources().getDrawable(R.drawable.button_shape_disabled));
    }
}
