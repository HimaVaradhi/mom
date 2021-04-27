package in.amruthashala.momapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import in.amruthashala.momapp.Adapaters.MyProductsListingAdapater;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;
import in.amruthashala.momapp.common.Constant;
import in.amruthashala.momapp.requestmodel.GetProductModel;
import in.amruthashala.momapp.retrofit.APIService;
import in.amruthashala.momapp.retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProducts extends BaseClass {

    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    @BindView(R.id.scrollView)
    HorizontalScrollView hsView;
    @BindView(R.id.txt_approval_pending)
    TextView tvApprovalPending;
    String status="";
    @BindView(R.id.txt_my_listings)
    TextView tvMyListings;
    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;
    @BindView(R.id.txt_date)
    TextView date;
    MyProductsListingAdapater myProductsListingAdapater;
    LinearLayoutManager linearLayoutManager;
    APIService apiService;
    ArrayList<GetProductModel> productlist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = ApiUtils.getAPIService(MyProducts.this);
        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Products");
         productlist = new ArrayList<>();
        getproductlist();

    }
    private void setRecyclerview() {
        myProductsListingAdapater = new MyProductsListingAdapater(this,productlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myProductsListingAdapater);
    }
    @Override
    public int getLayout() {
        return R.layout.activity_my_product;
    }


    @OnClick(R.id.txt_approval_pending)
    public void aprrovalonOnClick() {
        hsView.scrollTo(500, 0);
        myProductsListingAdapater.updateItem(2);
        tvApprovalPending.setBackgroundResource(R.drawable.button_shapeyellow);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.btn_add)
            startActivity(new Intent(MyProducts.this, CreateProduct.class));
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_products, menu);
        return true;
    }

    private void getproductlist() {
        Log.d("mom_TOKEN","mom_TOKEN"+Constant.mom_TOKEN+Constant.MOM_momuuid);
        Call<Object> call = apiService.getproduct("Bearer "+Constant.mom_TOKEN,Constant.MOM_momuuid);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("sklheh",response.code()+" "+response.raw());

                try {
                    Log.e("TAG", "onResponse: "+response.message() );
                    JSONObject object=new JSONObject(new Gson().toJson(response.body()));
                    Log.e("TAG", "onResponse: "+object );

                    JSONArray jsonArray=object.getJSONArray("data");
                     status = object.getString("status");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Log.i("DAta***", "" + jsonObject1);
                        String productname=jsonObject1.getString("product_name");
                        Log.i("productname***", "" + productname);
                        String productid=jsonObject1.getString("product_id");
                        Log.i("productid***", "" + productid);
                        JSONArray product_filtersarray = jsonObject1.getJSONArray("product_filters");
                        String productfranchise="";
                        for (int j = 0;  j< product_filtersarray.length(); j++) {
                            JSONObject jsonArrayJSONObject = product_filtersarray.getJSONObject(j);
                            Log.i("ArrayJSONObject***", "" + jsonArrayJSONObject);
                            productfranchise = jsonArrayJSONObject.getString("product_franchise");
                            Log.i("productfranchise***", "" + productfranchise);
                            JSONArray product_variantsarray = jsonObject1.getJSONArray("product_variants");
                            String  productstatus="",productprice="",productstock="";

                            for (int k = 0;  k< product_variantsarray.length(); k++) {
                                JSONObject varraiantobj = product_variantsarray.getJSONObject(k);
                                Log.i("ArrayJSONObject***", "" + jsonArrayJSONObject);
                                productstock = varraiantobj.getString("product_stock");
                                productprice = varraiantobj.getString("product_price_piece");
                                productstatus = varraiantobj.getString("status");
                                productfranchise = jsonArrayJSONObject.getString("product_franchise");

                                Log.i("productstock***", "" + productstock);
                                productlist.add(new GetProductModel(productid,productname,productfranchise,productstock,productprice,
                                        productstatus));
                                setRecyclerview();
                            }


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("order_api_fail", t.getMessage());
            }
        });
    }
}
