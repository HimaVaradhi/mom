package in.amruthashala.momapp.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import in.amruthashala.momapp.Adapaters.OrdersAdapater;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;
import in.amruthashala.momapp.common.Constant;
import in.amruthashala.momapp.retrofit.APIService;
import in.amruthashala.momapp.retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;

public class DashBoard extends BaseClass implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout dlLayout;
    @BindView(R.id.nav_view)
    NavigationView nvView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @BindView(R.id.recyler_view)
    RecyclerView rcOrders;
    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    LinearLayoutManager linearLayoutManager;
    OrdersAdapater ordersAdapater;
    private SharedPreferences.Editor editor = null;
    private SharedPreferences preferences = null;
    private SharedPreferences sharedPref;
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    APIService apiService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        apiService = ApiUtils.getAPIService(DashBoard.this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, dlLayout, tbToolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        actionBarDrawerToggle.syncState();
        nvView.setNavigationItemSelectedListener(this);
        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String token    = shp.getString("token", "");
        String momid    = shp.getString("momidedt", "");
        Constant.mom_TOKEN = token;
        Constant.MOM_momuuid = momid;
        CheckLogin();
        linearLayoutManager = new LinearLayoutManager(this);
        rcOrders.setLayoutManager(linearLayoutManager);
        ordersAdapater = new OrdersAdapater(this);
        rcOrders.setAdapter(ordersAdapater);
    }
    public void CheckLogin() {
        if (shp == null)
            shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String Token = shp.getString("token", "");
        if (Token != null && !Token.equals("")) {
            Toast.makeText(this, Token, Toast.LENGTH_SHORT).show();
        } else
        {
            Intent i = new Intent(DashBoard.this, Login.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    public int getLayout() {
        return R.layout.navigation_view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_menu_listing:
                startActivity(new Intent(this, MyProducts.class));
                break;
            case R.id.navigation_login:
                try {
                    if (shp == null)
                        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
                    shpEditor = shp.edit();
                    shpEditor.putString("token", "");
                    shpEditor.commit();
                    apiService.momlogout("Bearer "+Constant.mom_TOKEN).enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                                    Log.d("responseCode", "hgvghvgv"+object);
                                    String status = object.getString("status");
                                    String messagestr = object.getString("message");
                                    Log.d("messagestr&&&", "hgvghvgv"+messagestr);
                                    Toast.makeText(DashBoard.this, messagestr, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(DashBoard.this, Login.class);
                                    startActivity(i);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Toast.makeText(DashBoard.this, response.message(), Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(DashBoard.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Log.d("fail::::", "fail"+t.getMessage());
                        }
                    });
                } catch (Exception ex) {
                    Toast.makeText(DashBoard.this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
                break;
        }
        return false;
    }
}

