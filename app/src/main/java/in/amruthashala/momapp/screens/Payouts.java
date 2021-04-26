package in.amruthashala.momapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.amruthashala.momapp.Adapaters.PayoutReportAdapater;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;

public class Payouts extends BaseClass {


    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    @BindView(R.id.pie_chart)
    PieChart pcChart;
    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PayoutReportAdapater payoutReportAdapater;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Payout Overview");
        init();

    }

    private void init() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(32.2f, "Category 1"));
        entries.add(new PieEntry(20.1f, "Category 2"));
        entries.add(new PieEntry(11.3f, "Category 3"));
        entries.add(new PieEntry(8.2f, "Category 4"));
        entries.add(new PieEntry(27.3f, "Category 5"));
        PieDataSet set = new PieDataSet(entries, "Category");
        set.setColors(getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.blueColor),getResources().getColor(R.color.colorYellow),getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.orange));
        PieData data = new PieData(set);
        pcChart.setData(data);
        pcChart.getData().setDrawValues(false);
        pcChart.setDrawEntryLabels(false);
        pcChart.getDescription().setEnabled(false);
        pcChart.getLegend().setEnabled(false);
        pcChart.setRotationEnabled(false);
        pcChart.invalidate();
        linearLayoutManager=new LinearLayoutManager(this);
        payoutReportAdapater=new PayoutReportAdapater(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(payoutReportAdapater);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_payouts;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return true;
    }

    @OnClick({R.id.ll_next_payout,R.id.txt_next_payout_amount,R.id.txt_next_payout})
    public void nextPayoutClick(){
        startActivity(new Intent(this,NextPayout.class));
    }

    @OnClick({R.id.ll_last_payout,R.id.txt_last_payout_amount,R.id.txt_last_payout})
    public void lastPayoutClick(){
        startActivity(new Intent(this,LastPayout.class));
    }
}
