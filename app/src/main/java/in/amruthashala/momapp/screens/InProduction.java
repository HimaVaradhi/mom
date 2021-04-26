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
import in.amruthashala.momapp.Adapaters.InProductionAdapater;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;

public class InProduction extends BaseClass {

    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_production)
    TextView tvProduction;
    @BindView(R.id.txt_ready_to_ship)
    TextView tvReadyToShip;
    @BindView(R.id.txt_shipped)
    TextView tvShipped;
    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    LinearLayoutManager linearLayoutManager;
    InProductionAdapater inProductionAdapater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        tbToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("In Production");
        tbToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        linearLayoutManager=new LinearLayoutManager(this);
        inProductionAdapater=new InProductionAdapater(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(inProductionAdapater);
        switch (getIntent().getIntExtra("position", 0)) {
            case 1:
                onClickProduction();
                break;
            case 2:
                onClickReadyToShip();
                break;
            case 3:
                onClickShipped();
                break;
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_in_production;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @OnClick(R.id.txt_production)
    public void onClickProduction() {
        getSupportActionBar().setTitle("In Production");
        tvProduction.setBackground(getResources().getDrawable(R.drawable.button_shapeblue));
        tvReadyToShip.setBackground(getResources().getDrawable(R.drawable.button_shape_disabled));
        tvShipped.setBackground(getResources().getDrawable(R.drawable.button_shape_disabled));
        inProductionAdapater.UpdateItem(0);
    }

    @OnClick(R.id.txt_ready_to_ship)
    public void onClickReadyToShip() {
        getSupportActionBar().setTitle("Ready To Ship");
        tvProduction.setBackground(getResources().getDrawable(R.drawable.button_shape_disabled));
        tvReadyToShip.setBackground(getResources().getDrawable(R.drawable.button_shapelgreen));
        tvShipped.setBackground(getResources().getDrawable(R.drawable.button_shape_disabled));
        inProductionAdapater.UpdateItem(1);
    }

    @OnClick(R.id.txt_shipped)
    public void onClickShipped() {
        getSupportActionBar().setTitle("Shipped");
        tvProduction.setBackground(getResources().getDrawable(R.drawable.button_shape_disabled));
        tvReadyToShip.setBackground(getResources().getDrawable(R.drawable.button_shape_disabled));
        tvShipped.setBackground(getResources().getDrawable(R.drawable.button_shapelgreen));
        inProductionAdapater.UpdateItem(2);
    }
}
