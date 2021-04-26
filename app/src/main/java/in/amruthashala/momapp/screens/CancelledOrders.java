package in.amruthashala.momapp.screens;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import in.amruthashala.momapp.Adapaters.CancelledAdapater;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;

public class CancelledOrders extends BaseClass {

    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CancelledAdapater cancelledAdapater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        tbToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cancelled Orders");
        tbToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        linearLayoutManager = new LinearLayoutManager(this);
        cancelledAdapater = new CancelledAdapater(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cancelledAdapater);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_orders_cancelled;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }
}
