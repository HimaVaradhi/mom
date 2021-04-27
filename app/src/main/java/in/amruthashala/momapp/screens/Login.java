package in.amruthashala.momapp.screens;

import androidx.appcompat.widget.AppCompatButton;

import in.aabhasjindal.otptextview.OtpTextView;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;
import in.amruthashala.momapp.requestmodel.Myloginresponse;
import in.amruthashala.momapp.retrofit.APIService;
import in.amruthashala.momapp.retrofit.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        CheckLogin();
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
                Toast.makeText(this, "Forget Password", Toast.LENGTH_SHORT).show();
                break;
        }
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
                            Log.e("tokentoken", "token: " + tokenid);
                            Log.e("momid", "momid: " + momid);
                            if (shp == null)
                                shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
                            shpEditor = shp.edit();
                            shpEditor.putString("token", tokenid);
                            shpEditor.putString("momidedt", momid);
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
