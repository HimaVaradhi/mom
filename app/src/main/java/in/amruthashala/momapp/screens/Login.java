package in.amruthashala.momapp.screens;

import androidx.appcompat.widget.AppCompatButton;

import in.aabhasjindal.otptextview.OtpTextView;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;
import in.amruthashala.momapp.common.PinEntryView;
import in.amruthashala.momapp.requestmodel.Myloginresponse;
import in.amruthashala.momapp.retrofit.APIService;
import in.amruthashala.momapp.retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    String mobileNumber="";
    boolean otpVerify = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        if(shp.getBoolean("logged",false)){
            Intent in = new Intent(Login.this, DashBoard.class);
            startActivity(in);
            finish();
        }
        apiService = ApiUtils.getAPIService(Login.this);
        acProceed = findViewById(R.id.btn_proceed);
        tvLogin = findViewById(R.id.tvLogin);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        acProceed.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }
    public void CheckLogin() {
        if (shp == null)
            shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String Token = shp.getString("token", "");
        if (Token != null && !Token.equals("")) {
            Intent i = new Intent(Login.this, DashBoard.class);
            startActivity(i);
            finish();
        }
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
                BottomSheetDialog bottomSheerDialog = new BottomSheetDialog(Login.this);
                View parentView = getLayoutInflater().inflate(R.layout.dialog_forgotpassword, null);
                bottomSheerDialog.setContentView(parentView);

                LinearLayout coordinatorLayout = parentView.findViewById(R.id.ll_child);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(coordinatorLayout);

                TextView tvTimer = parentView.findViewById(R.id.tvTimer);
                TextView tvVerifyOtp = parentView.findViewById(R.id.tvVerifyOtp);
                TextView tvSendOtp = parentView.findViewById(R.id.tvSendOtp);
                TextView tvResetPassword = parentView.findViewById(R.id.tvResetPassword);
                TextView tvMobileNumber = parentView.findViewById(R.id.tvMobileNumber);
                EditText etMobile = parentView.findViewById(R.id.etMobile);
                EditText etPassword = parentView.findViewById(R.id.etPassword);
                EditText etVerifyPassword = parentView.findViewById(R.id.etVerifyPassword);
                PinEntryView pinEntryView = parentView.findViewById(R.id.pin_entry_view);
                LinearLayout llPassword = parentView.findViewById(R.id.llPassword);
                LinearLayout llVerify = parentView.findViewById(R.id.llVerify);
                LinearLayout llMobile = parentView.findViewById(R.id.llMobile);
                ProgressBar progreessBar = parentView.findViewById(R.id.progreessBar);


                tvSendOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mobileNumber = etMobile.getText().toString().trim();


                        if(mobileNumber.length()!=10){
                            Toast.makeText(Login.this, "Enter your registered number", Toast.LENGTH_LONG).show();
                        }
                        else {

                            progreessBar.setVisibility(View.VISIBLE);
                            startTimer(tvTimer);
                            tvMobileNumber.setText("Please enter the OTP sent to \n +91-"+mobileNumber);
                            callOtpSendApi(mobileNumber,llMobile,llVerify,progreessBar);

                        }
                    }
                });

                tvVerifyOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(otpVerify){
                            startActivity(new Intent(Login.this, DashBoard.class));
                            finish();
                        }
                        else {

                            Toast.makeText(Login.this, "OTP Error", Toast.LENGTH_SHORT).show();

                        }



                    }
                });

                pinEntryView.setOnPinEnteredListener(new PinEntryView.OnPinEnteredListener() {
                    @Override
                    public void onPinEntered(String pin) {


                        callLoginWithOtp(pin);


                    }
                });


                tvResetPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(etPassword.getText().toString().trim().isEmpty()){
                            etPassword.setError("Add Password");
                        }
                        else if(etPassword.getText().toString().trim().length()<8){
                            etPassword.setError("Min 8 character password");
                        }

                        else if(!etPassword.getText().toString().trim().equals(etVerifyPassword.getText().toString().trim())){
                            etVerifyPassword.setError("Password Not matching");
                        }
                        else {

                            Toast.makeText(Login.this, "Call Reset Api", Toast.LENGTH_SHORT).show();
                            bottomSheerDialog.cancel();
                        }
                    }
                });

                bottomSheerDialog.show();


                tvTimer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startTimer(tvTimer);
                        callResendOtp();

                    }
                });



                break;
        }
    }
    private void callLoginWithOtp(String pin) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("mobile_number", mobileNumber);
        requestBody.put("otp", pin);

        apiService.mom_login_with_otp(requestBody).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = object.getString("status");
                        JSONObject jsonData = object.getJSONObject("data");
                        String access_token = jsonData.getString("access_token");
                        String refresh_token = jsonData.getString("refresh_token");

                        JSONObject user_data = jsonData.getJSONObject("user_data");
                        String mobile_number = user_data.getString("mobile_number");
                        String mom_id = user_data.getString("mom_id");

                        SharedPreferences.Editor editor = shp.edit();

                        editor.putBoolean("logged", true);
                        editor.putString("access_token", access_token);
                        editor.putString("refresh_token", refresh_token);
                        editor.putString("mobile_number", mobile_number);
                        editor.putString("mom_id", mom_id);
                        editor.apply();

                        otpVerify = true;

                        Toast.makeText(Login.this, "OTP Verified Successfully", Toast.LENGTH_LONG).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Login.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }



            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });



        //mApiService.user_registration()
    }

    private void callResendOtp() {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("mobile_number", mobileNumber);

        apiService.forgot_password(requestBody).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful()) {
                    try {
                        JSONObject object=new JSONObject(new Gson().toJson(response.body()));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Login.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }



            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

                Toast.makeText(Login.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void callOtpSendApi(String mobileNumber, LinearLayout llMobile, LinearLayout llVerify, ProgressBar progressBar) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("mobile_number", mobileNumber);

        apiService.forgot_password(requestBody).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject object=new JSONObject(new Gson().toJson(response.body()));
                        llMobile.setVisibility(View.GONE);
                        llVerify.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        progressBar.setVisibility(View.GONE);
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Login.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }



            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void startTimer(TextView tvTimer) {

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Remaining time: " + millisUntilFinished / 1000);
                tvTimer.setEnabled(false);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvTimer.setText("Resend OTP");
                tvTimer.setEnabled(true);

            }

        }.start();
    }
    private void onLoginButtonClick() {
        String emailId = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (emailId.equals(""))
            return;
        else if (password.equals(""))
            return;
        else {
            try {
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("email", etEmail.getText().toString());
                requestBody.put("password", etPassword.getText().toString());
                apiService.momlogin(requestBody).enqueue(new Callback<Myloginresponse>() {
                    @Override
                    public void onResponse(Call<Myloginresponse> call, retrofit2.Response<Myloginresponse> response) {
                        if (response.isSuccessful()) {
                            String tokenid = response.body().getData().getAccess_token();
                            String momid = response.body().getData().getUser_data().getMom_id();
                            String email = response.body().getData().getUser_data().getEmail();
                            String mobile_number = response.body().getData().getUser_data().getMobile_number();
                            Log.e("tokentoken", "token: " + tokenid);
                            Log.e("momid", "momid: " + momid);
                            if (shp == null)
                                shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
                            shpEditor = shp.edit();
                            shpEditor.putBoolean("logged", true);
                            shpEditor.putString("token", tokenid);
                            shpEditor.putString("momidedt", momid);
                            shpEditor.putString("mobile_number", mobile_number);
                            shpEditor.putString("email", email);
                            shpEditor.commit();
                            Intent i = new Intent(Login.this, DashBoard.class);
                            startActivity(i);
                            finish();
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
}
