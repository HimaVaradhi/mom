package in.amruthashala.momapp.screens;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import in.amruthashala.momapp.Adapaters.LastPayoutAdapater;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;

public class LastPayout extends BaseClass {

    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    LastPayoutAdapater lastPayoutAdapater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Last Payout");
        linearLayoutManager=new LinearLayoutManager(this);
        lastPayoutAdapater=new LastPayoutAdapater(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(lastPayoutAdapater);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_last_payout;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }
}
