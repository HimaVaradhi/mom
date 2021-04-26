package in.amruthashala.momapp.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import in.amruthashala.momapp.Adapaters.OrdersAdapater;
import in.amruthashala.momapp.Product.ProductLandingActivity;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;
import in.amruthashala.momapp.common.Constant;
import in.amruthashala.momapp.common.SessionManagernew;
import in.amruthashala.momapp.cookies.PersistentHttpCookieStore;
import in.amruthashala.momapp.requestmodel.Myloginresponse;
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
    SessionManagernew session;
    APIService apiService;
    PersistentHttpCookieStore cookieStore;
    static final String COOKIES_HEADER = "Set-Cookie";
    HttpURLConnection connection;
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        cookieStore = new PersistentHttpCookieStore(getApplicationContext());
        CookieManager cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManagernew(getApplicationContext());
        apiService = ApiUtils.getAPIService();
        session.checkLogin();
        session.getUserDetails().get("token");
        SharedPreferences preferences = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
        editor= preferences.edit();
        String token    = preferences.getString("token", "");
        Constant.mom_TOKEN = token;

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, dlLayout, tbToolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        actionBarDrawerToggle.syncState();
        nvView.setNavigationItemSelectedListener(this);
        linearLayoutManager = new LinearLayoutManager(this);
        rcOrders.setLayoutManager(linearLayoutManager);
        ordersAdapater = new OrdersAdapater(this);
        rcOrders.setAdapter(ordersAdapater);
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
                                startActivity(new Intent(DashBoard.this, Login.class));
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
                break;
        }



        return false;
    }
}

