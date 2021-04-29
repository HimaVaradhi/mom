package in.amruthashala.momapp.screens;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.DrawableMarginSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import in.amruthashala.momapp.Adapaters.CustomSpinnerAdapter;
import in.amruthashala.momapp.Adapaters.ImagesAdapater;
import in.amruthashala.momapp.Adapaters.MyProductsListingAdapater;
import in.amruthashala.momapp.Adapaters.VaraintAdapter;
import in.amruthashala.momapp.Interfaces.CommonClick;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;
import in.amruthashala.momapp.common.CommonDialog;
import in.amruthashala.momapp.common.Constant;
import in.amruthashala.momapp.common.DatePickerDialogFragment;
import in.amruthashala.momapp.common.FilePath;
import in.amruthashala.momapp.common.PermissionCheck;
import in.amruthashala.momapp.common.RequestCodes;
import in.amruthashala.momapp.inputresponsemodel.AddNewProductResponse;
import in.amruthashala.momapp.inputresponsemodel.ProductFilter;

import in.amruthashala.momapp.inputresponsemodel.ProductVariant;
import in.amruthashala.momapp.requestmodel.GetProductModel;
import in.amruthashala.momapp.requestmodel.RequestModelClass;
import in.amruthashala.momapp.retrofit.APIService;
import in.amruthashala.momapp.retrofit.ApiClient;
import in.amruthashala.momapp.retrofit.ApiUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProduct extends BaseClass implements CommonClick {
    @BindView(R.id.toolbar)
    Toolbar tbToolbar;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.ll_0)
    LinearLayout ll0;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.ll_2)
    LinearLayout ll2;
    @BindView(R.id.ll_3)
    LinearLayout ll3;
    @BindView(R.id.ll_4)
    LinearLayout ll4;
    @BindView(R.id.txt_tab_index)
    TextView tv0;
    @BindView(R.id.txt_tab_index_1)
    TextView tv1;
    @BindView(R.id.txt_tab_index_2)
    TextView tv2;
    @BindView(R.id.txt_tab_index_3)
    TextView tv3;
    @BindView(R.id.txt_tab_index_4)
    TextView tv4;
    @BindView(R.id.txt_tab_label)
    TextView tvLabel0;
    @BindView(R.id.txt_tab_label_1)
    TextView tvLabel1;
    @BindView(R.id.txt_tab_label_2)
    TextView tvLabel2;
    @BindView(R.id.txt_tab_label_3)
    TextView tvLabel3;
    @BindView(R.id.txt_tab_label_4)
    TextView tvLabel4;
    TextView add_varients_images;
    private int SpannedLength = 0,chipLength = 4;
    EditText edt_productname;
    EditText edt_desc,edt_vurl,edt_localc,edt_zonalcharges,edt_ncharges,manufacture_date
            ,regional_speciality,edit_taste,franchise,sp_energy,sp_protin,sp_fat,sp_carbo
            ,sp_fiber,sp_sugar,edt_basicin,edt_ingredents,et_stock,
            et_quality,et_weight,et_offer,et_hsn,et_tax,et_upc,mandate,et_preservation,et_price;
    AppCompatEditText sub_cat_names;
    ChipGroup chipGroup;
    Spinner sp_catagory,sp_sub_catagory,sp_ratting,sp_maxsh,sp_byorganic,sp_preference,sp_country,sp_food_type,sp_type,sp_brand;
    Calendar myCalendar = Calendar.getInstance();
    RecyclerView recycler_varients;
    String sp_catagorystr,sp_sub_catagorystr,sp_maxshstr,sp_byorganicstr,sp_countrystr,sp_food_typestr,sp_brandstr,sp_typestr,sp_rattingstr,sp_preferencestr;
    PermissionCheck permissionCheck;
    CommonDialog commonDialog;
    ArrayList<Bitmap> images;
    RecyclerView recyclerView;
    HorizontalRecyclerView adapter;
    ImagesAdapater imagesAdapater;
    ImageView ivViewImage;
    FloatingActionButton button;
    APIService apiService;
    ArrayList<Uri> mArrayUri;
    ArrayList<ProductVariant> productVariants;
    ArrayList<ProductFilter> productFilters;
    VaraintAdapter varaintAdapter;
    public Button addproduct;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(tbToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Product");
        apiService = ApiUtils.getAPIService(CreateProduct.this);
        permissionCheck = new PermissionCheck();
        images = new ArrayList<>();
        productVariants = new ArrayList<>();
        productFilters = new ArrayList<>();
        commonDialog = new CommonDialog();
        mArrayUri = new ArrayList<>();
        if (!permissionCheck.isStoragePermissionExist(this)) {
            permissionCheck.cameraGalleryRequestPermissionForActivity(this);
        }

        View view = View.inflate(CreateProduct.this, R.layout.add_product_one, null);
        sp_catagory = view.findViewById(R.id.sp_catagory);
        edt_productname = view.findViewById(R.id.edt_productname);
        edt_desc = view.findViewById(R.id.edt_desc);
        edt_vurl = view.findViewById(R.id.edt_vurl);
        edt_localc = view.findViewById(R.id.edt_localc);
        edt_zonalcharges = view.findViewById(R.id.edt_zonalcharges);
        edt_ncharges = view.findViewById(R.id.edt_ncharges);
        sub_cat_names = view.findViewById(R.id.sub_cat_names);
        chipGroup = view.findViewById(R.id.chip_group);
        sp_sub_catagory = view.findViewById(R.id.sp_sub_catagory);
        sp_brand=view.findViewById(R.id.sp_brand);
        sp_type=view.findViewById(R.id.sp_type);
        llContainer.removeAllViews();
        llContainer.addView(view);
        ArrayList<String> catarray = new ArrayList<>();
        catarray.add("ed7b1226a487b0aff3e96b7ce9ec78ba");
        catarray.add("ed1fdf27e74baf61490a5c3b2d79be63");
        catarray.add("dee31ee251660480c8526a8cc7755a0b");
        CustomSpinnerAdapter Productwtspnad  =new CustomSpinnerAdapter(this, catarray,"White");
        sp_catagory.setAdapter(Productwtspnad);
        sp_catagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_catagorystr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> sp_typearray = new ArrayList<>();
        sp_typearray.add("mom");
        CustomSpinnerAdapter sp_typearrayad  =new CustomSpinnerAdapter(this, sp_typearray,"White");
        sp_type.setAdapter(sp_typearrayad);
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_typestr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> sp_brandarray = new ArrayList<>();
        sp_brandarray.add("606d92d527d3b8df3aa72bcd");
        CustomSpinnerAdapter sp_brandad  =new CustomSpinnerAdapter(this, sp_brandarray,"White");
        sp_brand.setAdapter(sp_brandad);
        sp_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_brandstr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> subcatarray = new ArrayList<>();
        subcatarray.add("bbc74f4284bd82092948da4c658c5bee");
        subcatarray.add("b1e473cb3099aa284c8dd9d7f9e7bf42");
        subcatarray.add("8eed9d1e31b097be167e4df8d686ba5f");
        CustomSpinnerAdapter subada  =new CustomSpinnerAdapter(CreateProduct.this,subcatarray,"White");
        sp_sub_catagory.setAdapter(subada);
        sp_sub_catagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_sub_catagorystr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public int getLayout() {
        return R.layout.activity_create_product;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.done:
                if(Constant.ProductID.isEmpty()){
                    addNewProductToServer();
                }else{
                    uploadimage();
                }

               break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_product,menu);
        return true;
    }

    @OnClick({R.id.ll_0, R.id.txt_tab_label, R.id.txt_tab_index})
    public void ll0Click() {
        tvLabel0.setVisibility(View.VISIBLE);
        tvLabel1.setVisibility(View.GONE);
        tvLabel2.setVisibility(View.GONE);
        tvLabel3.setVisibility(View.GONE);
        tvLabel4.setVisibility(View.GONE);
        tv0.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv1.setBackground(getResources().getDrawable(R.drawable.circle_disabled));
        tv2.setBackground(getResources().getDrawable(R.drawable.circle_disabled));
        tv3.setBackground(getResources().getDrawable(R.drawable.circle_disabled));
        tv4.setBackground(getResources().getDrawable(R.drawable.circle_disabled));
        View view = View.inflate(CreateProduct.this, R.layout.add_product_one, null);
        ArrayList<String> catarray = new ArrayList<>();
        catarray.add("ed7b1226a487b0aff3e96b7ce9ec78ba");
        catarray.add("ed1fdf27e74baf61490a5c3b2d79be63");
        catarray.add("dee31ee251660480c8526a8cc7755a0b");
        CustomSpinnerAdapter Productwtspnad  =new CustomSpinnerAdapter(this, catarray,"White");
        sp_catagory.setAdapter(Productwtspnad);
        sp_catagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_catagorystr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> sp_typearray = new ArrayList<>();
        sp_typearray.add("mom");
        CustomSpinnerAdapter sp_typearrayad  =new CustomSpinnerAdapter(this, sp_typearray,"White");
        sp_type.setAdapter(sp_typearrayad);
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_typestr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> sp_brandarray = new ArrayList<>();
        sp_brandarray.add("606d92d527d3b8df3aa72bcd");
        CustomSpinnerAdapter sp_brandad  =new CustomSpinnerAdapter(this, sp_brandarray,"White");
        sp_brand.setAdapter(sp_brandad);
        sp_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_brandstr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> subcatarray = new ArrayList<>();
        subcatarray.add("bbc74f4284bd82092948da4c658c5bee");
        subcatarray.add("b1e473cb3099aa284c8dd9d7f9e7bf42");
        subcatarray.add("8eed9d1e31b097be167e4df8d686ba5f");
        CustomSpinnerAdapter subada  =new CustomSpinnerAdapter(CreateProduct.this,subcatarray,"White");
        sp_sub_catagory.setAdapter(subada);
        sp_sub_catagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_sub_catagorystr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        llContainer.removeAllViews();
        llContainer.addView(view);
    }
    @OnClick({R.id.ll_1, R.id.txt_tab_index_1, R.id.txt_tab_label_1})
    public void ll1Click() {
        tvLabel0.setVisibility(View.GONE);
        tvLabel1.setVisibility(View.VISIBLE);
        tvLabel2.setVisibility(View.GONE);
        tvLabel3.setVisibility(View.GONE);
        tvLabel4.setVisibility(View.GONE);
        tv0.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv2.setBackground(getResources().getDrawable(R.drawable.circle_disabled));
        tv3.setBackground(getResources().getDrawable(R.drawable.circle_disabled));
        tv4.setBackground(getResources().getDrawable(R.drawable.circle_disabled));
        View view = View.inflate(CreateProduct.this, R.layout.add_product_two, null);
        manufacture_date = view.findViewById(R.id.manufacture_date);
        regional_speciality = view.findViewById(R.id.regional_speciality);
        edit_taste = view.findViewById(R.id.edit_taste);
        franchise = view.findViewById(R.id.franchise);
        sp_ratting=view.findViewById(R.id.sp_ratting);
        sp_maxsh=view.findViewById(R.id.sp_maxsh);
        sp_country=view.findViewById(R.id.sp_country);
        sp_food_type=view.findViewById(R.id.sp_food_type);
        sp_preference=view.findViewById(R.id.sp_preference);
        sp_byorganic=view.findViewById(R.id.sp_byorganic);
        sp_energy = view.findViewById(R.id.sp_energy);
        sp_protin = view.findViewById(R.id.sp_protin);
        sp_fat = view.findViewById(R.id.sp_fat);
        sp_carbo = view.findViewById(R.id.sp_carbo);
        sp_fiber = view.findViewById(R.id.sp_fiber);
        sp_sugar = view.findViewById(R.id.sp_sugar);
        edt_basicin = view.findViewById(R.id.edt_basicin);
        edt_ingredents = view.findViewById(R.id.edt_ingredents);
        llContainer.removeAllViews();
        llContainer.addView(view);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd-MM-yyyy"; //put your date format in which you need to display
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                manufacture_date.setText(sdf.format(myCalendar.getTime()));
            }
        };

        manufacture_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(CreateProduct.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ArrayList<String> maxlife = new ArrayList<>();
        maxlife.add("1 month");
        maxlife.add("2 months");
        maxlife.add("3 months");
        maxlife.add("4 months");
        maxlife.add("5 months");
        maxlife.add("6 months");
        maxlife.add("7 months");
        maxlife.add("8 months");
        maxlife.add("9 months");
        CustomSpinnerAdapter subada  =new CustomSpinnerAdapter(CreateProduct.this,maxlife,"White");
        sp_maxsh.setAdapter(subada);
        sp_maxsh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_maxshstr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> organic = new ArrayList<>();
        organic.add("True");
        organic.add("False");
        CustomSpinnerAdapter organicada  =new CustomSpinnerAdapter(CreateProduct.this,organic,"White");
        sp_byorganic.setAdapter(organicada);
        sp_byorganic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_byorganicstr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> rating = new ArrayList<>();
        rating.add("1");
        rating.add("2");
        rating.add("3");
        rating.add("4");
        rating.add("5");
        CustomSpinnerAdapter ratingad  =new CustomSpinnerAdapter(CreateProduct.this,rating,"White");
        sp_ratting.setAdapter(ratingad);
        sp_ratting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_rattingstr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> country = new ArrayList<>();
        country.add("India");
        CustomSpinnerAdapter countryada =new CustomSpinnerAdapter(CreateProduct.this,country,"White");
        sp_country.setAdapter(countryada);
        sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_countrystr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> sp_footype = new ArrayList<>();
        sp_footype.add("Veg");
        sp_footype.add("Non veg");
        CustomSpinnerAdapter spad  =new CustomSpinnerAdapter(CreateProduct.this,sp_footype,"White");
        sp_food_type.setAdapter(spad);
        sp_food_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_food_typestr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> foodpre = new ArrayList<>();
        foodpre.add("True");
        foodpre.add("False");
        CustomSpinnerAdapter foodpread =new CustomSpinnerAdapter(CreateProduct.this,foodpre,"White");
        sp_preference.setAdapter(countryada);
        sp_preference.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // qualification_mother = parent.getItemAtPosition(position).toString();
                sp_preferencestr = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.ll_2, R.id.txt_tab_index_2, R.id.txt_tab_label_2})
    public void ll2Click() {
        tvLabel0.setVisibility(View.GONE);
        tvLabel1.setVisibility(View.GONE);
        tvLabel2.setVisibility(View.VISIBLE);
        tvLabel3.setVisibility(View.GONE);
        tvLabel4.setVisibility(View.GONE);
        tv0.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv3.setBackground(getResources().getDrawable(R.drawable.circle_disabled));
        tv4.setBackground(getResources().getDrawable(R.drawable.circle_disabled));
        View view = View.inflate(CreateProduct.this, R.layout.add_product_three, null);
        TextView add_varients = view.findViewById(R.id.add_varients);
        productVariants = new ArrayList<ProductVariant>();
        recycler_varients = view.findViewById(R.id.recycler_varients);
        RecyclerView.LayoutManager lmmanager = new LinearLayoutManager(getApplicationContext());
        recycler_varients.setLayoutManager(lmmanager);
        varaintAdapter = new VaraintAdapter(getApplicationContext(), productVariants);
        recycler_varients.setAdapter(varaintAdapter);

        add_varients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(CreateProduct.this);
                dialog.setContentView(R.layout.product_variant_dialog);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.setCanceledOnTouchOutside(false);
                //productVariants = new ArrayList<>();

                varaintAdapter = new VaraintAdapter(CreateProduct.this,productVariants);
                et_stock = dialog.findViewById(R.id.et_stock);
                et_quality = dialog.findViewById(R.id.et_quality);
                 et_weight = dialog.findViewById(R.id.et_weight);
               // int weight= Integer.parseInt(et_weight.getText().toString());
                et_offer = dialog.findViewById(R.id.et_offer);
              //  int offer= Integer.parseInt(et_offer.getText().toString());
                mandate = dialog.findViewById(R.id.date);
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd-MM-yyyy"; //put your date format in which you need to display
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                        mandate.setText(sdf.format(myCalendar.getTime()));
                    }
                };
                mandate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new DatePickerDialog(CreateProduct.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                 et_hsn = dialog.findViewById(R.id.et_hsn);
                 et_tax = dialog.findViewById(R.id.et_tax);
                 et_upc = dialog.findViewById(R.id.et_upc);
                 et_preservation = dialog.findViewById(R.id.et_preservation);
                 et_price = dialog.findViewById(R.id.et_price);

                addproduct = dialog.findViewById(R.id.addproduct);
                productVariants.add(new ProductVariant(
                        "True",250,"50g",200,250,0,
                        "glass","true",et_preservation.getText().toString(),"22-10-2020",et_hsn.getText().toString(),et_tax.getText().toString(),et_upc.getText().toString()));
                dialog.show();
                addproduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        varaintAdapter.notifyDataSetChanged();
                                   dialog.dismiss();
                    }
                });
            }
        });

        llContainer.removeAllViews();
        llContainer.addView(view);
    }

    @OnClick({R.id.ll_3, R.id.txt_tab_index_3, R.id.txt_tab_label_3})
    public void ll3Click() {
        tvLabel0.setVisibility(View.GONE);
        tvLabel1.setVisibility(View.GONE);
        tvLabel2.setVisibility(View.GONE);
        tvLabel3.setVisibility(View.VISIBLE);
        tvLabel4.setVisibility(View.GONE);
        tv0.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv4.setBackground(getResources().getDrawable(R.drawable.circle_disabled));
        View view = View.inflate(CreateProduct.this, R.layout.add_product_four, null);
        add_varients_images = view.findViewById(R.id.add_varients_images);
        recyclerView = view.findViewById(R.id.recycler_images);
        adapter = new HorizontalRecyclerView(mArrayUri);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        add_varients_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent,"Select Image"), 1);
            }
        });
        llContainer.removeAllViews();
        llContainer.addView(view);
    }

    @OnClick({R.id.ll_4, R.id.txt_tab_index_4, R.id.txt_tab_label_4})
    public void ll4Click() {
        tvLabel0.setVisibility(View.GONE);
        tvLabel1.setVisibility(View.GONE);
        tvLabel2.setVisibility(View.GONE);
        tvLabel3.setVisibility(View.GONE);
        tvLabel4.setVisibility(View.VISIBLE);
        tv0.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv1.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv2.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv3.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        tv4.setBackground(getResources().getDrawable(R.drawable.circle_yellow24));
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.add_product_four, llContainer, false);
        add_varients_images = view.findViewById(R.id.add_varients_images);
        recyclerView = view.findViewById(R.id.recycler_images);
        adapter = new HorizontalRecyclerView(mArrayUri);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        add_varients_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Pictures: "), 1);
            }
        });
        llContainer.removeAllViews();
        llContainer.addView(view);

    }

    @Override
    public void commonClick(int code) {
        switch (code) {
            case RequestCodes.CAMERA:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, RequestCodes.CAMERA);
                break;
            case RequestCodes.STORAGE:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, RequestCodes.STORAGE);
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            commonDialog.ImageUploadDialog(CreateProduct.this, this);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(CreateProduct.this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(CreateProduct.this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(CreateProduct.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                commonDialog.showPermissionAlert(CreateProduct.this, "This Application need Camera and stoarge Permissions", this);
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                mArrayUri.clear();
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        Log.d("uriioewrwe", uri.toString() + "");
                        mArrayUri.add(uri);

                    }
                    Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    adapter.notifyDataSetChanged();
                }else {
                    mArrayUri.clear();
                    Log.v("LOG_TAG", "Selected Images");
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    ArrayList<String> imagesEncodedList = new ArrayList<String>();
                    if (data.getData() != null) {
                        Uri mImageUri = data.getData();
                        mArrayUri.add(mImageUri);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }
    public void removeItem(int position) {
        images.remove(position);
        imagesAdapater.notifyDataSetChanged();
        tvLabel4.setText(tvLabel4.getText().toString().split("  ")[0]+"  "+String.valueOf(images.size())+"/ 5");
        button.setVisibility(View.VISIBLE);
    }

    public void viewImage(int position){
        ivViewImage.setImageBitmap(images.get(position));
    }


    private void addNewProductToServer() {
        JSONObject object = new JSONObject();

        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = null ;

        try {
            object.put("type",sp_typestr);
            object.put("product_name",edt_productname.getText().toString());
            object.put("product_type","true");
            object.put("product_description",edt_desc.getText().toString());
            object.put("seller_id",Constant.MOM_momuuid);
            object.put("brand_id",sp_brandstr);
            object.put("video_url","http://amrutha.com");
            object.put("product_status","2");
            JSONArray catarray = new JSONArray();
            catarray.put(sp_catagorystr);
            JSONArray subcatarray = new JSONArray();
           subcatarray.put(sp_sub_catagorystr);
            JSONArray franchisearray = new JSONArray();
            franchisearray.put("123456789");
            object.put("product_category_id",catarray);
            object.put("product_subcategory_id",subcatarray);
            object.put("franchise_id",franchisearray);
            JSONArray searchrray = new JSONArray();
            searchrray.put("Pickle,Mango,Andhra");
            object.put("search_keyword",sub_cat_names.getText().toString());
            JSONObject deliverychargeobj = new JSONObject();
            JSONObject nutritional_factsobj = new JSONObject();
            JSONObject ingredentsobj = new JSONObject();
            deliverychargeobj.put("local_delivery_charge",edt_localc.getText().toString());
            deliverychargeobj.put("zonal_delivery_charge",edt_zonalcharges.getText().toString());
            deliverychargeobj.put("national_delivery_charge",edt_ncharges.getText().toString());
            object.put("delivery_charge",deliverychargeobj);
            nutritional_factsobj.put("Energy",sp_energy.getText().toString());
            nutritional_factsobj.put("Protein",sp_protin.getText().toString());
            nutritional_factsobj.put("Carbohydrates",sp_carbo.getText().toString());
            nutritional_factsobj.put("Fat",sp_fat.getText().toString());
            nutritional_factsobj.put("Fiber",sp_fiber.getText().toString());
            nutritional_factsobj.put("Sugar",sp_sugar.getText().toString());
            object.put("nutritional_facts",nutritional_factsobj);
            ingredentsobj.put("base_ingredents",edt_basicin.getText().toString());
            ingredentsobj.put("ingredents",edt_ingredents.getText().toString());
            object.put("ingredents",ingredentsobj);

            JSONArray orderArray = new JSONArray();
            for (int i=0; i<productVariants.size();i++){
                JSONObject orderObj = new JSONObject();
              orderObj.put( "product_certificate","True");
                orderObj.put("product_stock",productVariants.get(i).getProductStock());
                orderObj.put( "status","true");
                orderObj.put("product_hsn",productVariants.get(i).getProductHsn());
                orderObj.put("product_price_weight",productVariants.get(i).getProductPriceWeight());
                orderObj.put("product_price_piece",et_price.getText().toString());
                orderObj.put("discount_offer",productVariants.get(i).getDiscountOffer());
                orderObj.put("product_container","glass");
                orderObj.put("product_tax",productVariants.get(i).getProductTax());
                orderObj.put("product_ean_or_upc",productVariants.get(i).getProductEanOrUpc());
                orderObj.put("preservation_process",productVariants.get(i).getPreservationProcess());
                orderArray.put(orderObj);
                object.put("product_variants",orderArray);
            }
            JSONArray productfiltersrray = new JSONArray();
            JSONObject productfiltersrrayobj = new JSONObject();
            productfiltersrrayobj.put( "manufactured_by",manufacture_date.getText().toString());
            productfiltersrrayobj.put("regional_speciality",regional_speciality.getText().toString());
            productfiltersrrayobj.put( "product_taste",edit_taste.getText().toString());
            productfiltersrrayobj.put( "product_franchise",franchise.getText().toString());
            productfiltersrrayobj.put( "max_self_life",sp_maxshstr);
            productfiltersrrayobj.put( "made_in_country",sp_countrystr);
            productfiltersrrayobj.put("product_rating",sp_rattingstr);
            productfiltersrrayobj.put( "food_preference",sp_food_typestr);

            productfiltersrray.put(productfiltersrrayobj);
            object.put("product_filters",productfiltersrray);
            gsonObject = (JsonObject) jsonParser.parse(object.toString());

        }catch (JSONException je){
            je.printStackTrace();
        }

        Log.d("dsdsdsd", gsonObject.toString());
        if (Constant.mom_TOKEN != null && gsonObject != null) {
         apiService.addNewProduct("Bearer "+Constant.mom_TOKEN,gsonObject).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                    Log.d("sklheh",response.code()+" "+response.raw());

                        try {
                            JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                            Log.d("responseCode", "hgvghvgv"+object);
                            String status = object.getString("status");
                            String data = object.getString("data");
                            if (status.equalsIgnoreCase("success")) {
                                Constant.ProductID=data;
                                Toast.makeText(CreateProduct.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Toast.makeText(CreateProduct.this,t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void uploadimage() {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    MultipartBody.Builder builderAadhar = new MultipartBody.Builder();
    builderAadhar.setType(MultipartBody.FORM);

    builderAadhar.addFormDataPart("product_id",Constant.ProductID);


    for (int i = 0; i < mArrayUri.size(); i++) {

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(CreateProduct.this.getContentResolver(), mArrayUri.get(i));

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bitmap != null;
        String path = getRealPathFromURI(getImageUri(CreateProduct.this, bitmap));

        File f = new File(path);
        // jsonArray.put("image" + i);
        Log.d(" f.getName()", f.getName() + " " + f.getName());
        builderAadhar.addFormDataPart("image", f.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), f));

    }
    MultipartBody requestBody = builderAadhar.build();

    apiService.add_productimage("Bearer "+Constant.mom_TOKEN,requestBody).enqueue(new Callback<Object>() {
        @Override
        public void onResponse(Call<Object> call, Response<Object> response) {
            if (response.isSuccessful()) {
                try {
                    JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                    String status = object.getString("status");
                    String message = object.getString("message");
                    Log.d("onSucess", object.toString());

                    if (status.equalsIgnoreCase("success")) {
                        Log.d("Add_staff_success", response.code() + " " + message);
                        Toast.makeText(CreateProduct.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    assert response.errorBody() != null;
                    JSONObject object = new JSONObject(response.errorBody().string());
                    String message = object.getString("message");
                    Log.d("Add_staff_fail", message);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<Object> call, Throwable t) {
            Log.d("Add_staff_fail", t.getMessage());
        }
    });

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


}
