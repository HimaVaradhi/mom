package in.amruthashala.momapp.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;
import in.amruthashala.momapp.common.Constant;
import in.amruthashala.momapp.common.SessionManagernew;
import in.amruthashala.momapp.cookies.PersistentHttpCookieStore;
import in.amruthashala.momapp.requestmodel.GetProductModel;
import in.amruthashala.momapp.requestmodel.Myloginresponse;
import in.amruthashala.momapp.retrofit.APIService;
import in.amruthashala.momapp.retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends BaseClass implements View.OnClickListener {

    AppCompatButton acPassword, acOtp, acSendOtp;
    Boolean onePressed = true, twoPressed = false;
    EditText etPassword, etEmail;
    ImageButton ibButton;
    LinearLayout llOtpLayout, llResendOtp, llPassword,llProceed;
    OtpTextView otpTextView;
    boolean otpClicked=false;
    String strTemp="";
    TextView acProceed,tvLogin,tvForgotPassword;
    APIService apiService;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    SessionManagernew session;
    PersistentHttpCookieStore cookieStore;
    String str = "https://varadhi-user.herokuapp.com";
    URI uri ;
    URL url ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManagernew(Login.this);
        apiService = ApiUtils.getAPIService();
        acProceed = findViewById(R.id.btn_proceed);
        tvLogin = findViewById(R.id.tvLogin);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        acProceed.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }
    @Override
    public int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_login;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_proceed:
                startActivity(new Intent(this, SignUp.class));
                break;
                case R.id.tvLogin:
                    onLoginButtonClick();
                break;

            case R.id.tvForgotPassword:
                Toast.makeText(this, "Forget Password", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void onLoginButtonClick() {
        String emailId = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if(emailId.equals(""))
            return;
        else if(password.equals(""))
            return;
        else {
            try {
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("email", etEmail.getText().toString());
                requestBody.put("password",etPassword.getText().toString());
                apiService.momlogin(requestBody).enqueue(new Callback<Myloginresponse>() {
                    @Override
                    public void onResponse(Call<Myloginresponse> call, retrofit2.Response<Myloginresponse> response) {
                        if (response.isSuccessful()) {
                            String token =response.body().getData().getAccess_token();
                            String refreshtoken =response.body().getData().getRefresh_token();
                            String momid =response.body().getData().getUser_data().getMom_id();
                          //  Toast.makeText(Login.this, momid+"Successfully Login", Toast.LENGTH_SHORT).show();
                            Log.e("tokentoken", "token: "+token );
                            Log.e("momid", "momid: "+momid );
                            Log.d("==Cookie==1===", "==="+response.raw().request().headers().get("Cookie"));
                            Log.d("==Cookie==2==", "==="+response.headers().get("Set-Cookie"));
                            Log.d("==Content-Type====", "==="+response.headers().get("Content-Type"));
                            //cookie code
                            try {
                                uri = new URI(str);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                            try {
                                url = uri.toURL();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            List<HttpCookie> cookie = HttpCookie.parse(response.headers().get("Set-Cookie"));
                            Log.d("Response code:", "===========" + cookie);
                            cookieStore = new PersistentHttpCookieStore(getApplicationContext());
                            for (int i=0; i<cookie.size(); i++)
                                cookieStore.add(uri, cookie.get(i));
                            CookieManager cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);
                            CookieHandler.setDefault(cookieManager);
                            Log.d("ny_cookies", cookie.size()+" "+cookieStore.getCookies().toString());
                            //cokie code end
                            Constant.refresh_TOKEN=refreshtoken;
                            Constant.MOM_momuuid=momid;
                            refrestoken();
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(Login.this, response.message(), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Myloginresponse> call, Throwable t) {
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
    private void refrestoken(){
        Call<Object> call = apiService.refreshtoken("Bearer "+Constant.refresh_TOKEN);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("sklheh",response.code()+" "+response.raw());

                try {
                    Log.e("TAG", "onResponse: "+response.message() );
                    JSONObject object=new JSONObject(new Gson().toJson(response.body()));
                    Log.e("TAG", "onResponse: "+object );

                    JSONObject jsonObject=object.getJSONObject("data");
                    String token = jsonObject.getString("access_token");
                    cookieStore = new PersistentHttpCookieStore(getApplicationContext());
                    CookieManager cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);
                    CookieHandler.setDefault(cookieManager);
                    session.createLoginSession(token);
                    session.saveUserId(token);
                    preferences = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString("token", token);
                    editor.apply();
                    startActivity(new Intent(Login.this, DashBoard.class));
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
