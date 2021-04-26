package in.amruthashala.momapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import in.amruthashala.momapp.Adapaters.MenuListingAdapater;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;

public class MenuListings extends BaseClass {


    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    @BindView(R.id.spinner_menu)
    Spinner spMenu;
    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_active)
    TextView tvActive;
    @BindView(R.id.txt_deactive)
            TextView tvDeactive;
    MenuListingAdapater menuListingAdapater;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Menu Items");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.menu_catagory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.custom_spinner_text);
        spMenu.setAdapter(adapter);
        menuListingAdapater = new MenuListingAdapater(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(menuListingAdapater);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_menu_listing;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

    @OnClick(R.id.txt_my_products)
    public void onClickProducts(){
        startActivity(new Intent(this,MyProducts.class));
    }

    @OnClick(R.id.txt_active)
    public void activeClick(){
        tvActive.setBackground(getResources().getDrawable(R.drawable.button_shapenew));
        tvDeactive.setBackground(getResources().getDrawable(R.drawable.button_shape_disabled));
    }
    @OnClick(R.id.txt_deactive)
    public void deactiveClick(){
        tvActive.setBackground(getResources().getDrawable(R.drawable.button_shape_disabled));
        tvDeactive.setBackground(getResources().getDrawable(R.drawable.button_shapeyellow));
    }
}
