package in.amruthashala.momapp.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.Constant;
import in.amruthashala.momapp.inputresponsemodel.AddressList;

public class PaymentFragment extends Fragment{
    @BindView(R.id.et_holder_name)
    EditText etAccountHolderName;
    @BindView(R.id.et_account_number)
    EditText etAccountNumber;
    @BindView(R.id.et_ifsc_code)
    EditText etIFSCCode;
    @BindView(R.id.et_branch_name)
    EditText etBranchName;
    @BindView(R.id.et_bank_name)
    EditText etBankName;
    @BindView(R.id.et_mobile_number)
    EditText etMobileNumber;
    @BindView(R.id.btn_send_otp_email)
    AppCompatButton acSendOtp;
    @BindView(R.id.ll_otp_layout)
    LinearLayout otpLayout;
    String strTempPhone="",stretAccountHolderName,stretAccountNumber,stretIFSCCode,stretBranchName,stretBankName,stretMobileNumber;

    ArrayList<AddressList> paymentlists;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_sign_up, container, false);
        ButterKnife.bind(this, view);
        etMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 10) {
                    if(strTempPhone.equals(charSequence.toString())){
                        acSendOtp.setVisibility(View.GONE);
                        otpLayout.setVisibility(View.VISIBLE);
                    }else {
                        acSendOtp.setVisibility(View.VISIBLE);
                        otpLayout.setVisibility(View.GONE);
                    }
                } else {
                    acSendOtp.setVisibility(View.GONE);
                    otpLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    @OnClick(R.id.btn_send_otp_email)
    public void click(){
        strTempPhone=etMobileNumber.getText().toString();
        acSendOtp.setVisibility(View.GONE);
        otpLayout.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.ll_save_layout, R.id.txt_save, R.id.img_save})
    public void onSave() {
        if (etAccountHolderName.getText().toString().isEmpty()) {
            etAccountHolderName.setError("Please enter Account holder name");
        } else if (etAccountNumber.getText().toString().isEmpty()) {
            etAccountNumber.setError("Please enter Account number");
        } else if (etIFSCCode.getText().toString().isEmpty()) {
            etIFSCCode.setError("Please enter IFSC code");
        } else if (etBranchName.getText().toString().isEmpty()) {
            etBranchName.setError("Please enter Branch name");
        } else if (etBankName.getText().toString().isEmpty()) {
            etBankName.setError("Please enter Bank name");
        } else if (etMobileNumber.getText().toString().isEmpty()) {
            etMobileNumber.setError("Please enter Mobile number");
        }else{
            stretAccountHolderName=etAccountHolderName.getText().toString();
            stretAccountNumber=etAccountNumber.getText().toString();
            stretIFSCCode=etIFSCCode.getText().toString();
            stretBranchName=etBranchName.getText().toString();
            stretBankName=etBankName.getText().toString();
            stretMobileNumber=etMobileNumber.getText().toString();

            Constant.AccountHolderName=stretAccountHolderName;
            Constant.AccountNumber=stretAccountNumber;
            Constant.IFSCCode=stretIFSCCode;
            Constant.BranchName=stretBranchName;
            Constant.BankName=stretBankName;
            Constant.PMobileNumber=stretMobileNumber;

           // JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject = new JSONObject();
            JSONObject bank = new JSONObject();
            JSONObject other = new JSONObject();
                try {
                    bank.put("account_holder_name", Constant.AccountHolderName);
                    bank.put("account_no",Constant.AccountNumber);
                    bank.put("ifsc_code",Constant.IFSCCode);
                    bank.put("bank_name",Constant.BankName);
                    bank.put("branch_name",Constant.BranchName);
                    other.put("account_type","Savings");
                    other.put("mobile_number",Constant.PMobileNumber);


                    jsonObject.put("bank_account_details",bank);
                    jsonObject.put("other_account",other);

                   // jsonObject1.put("payments",jsonObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            Constant.PaymentObject=jsonObject.toString();
            Toast.makeText(getActivity(), "Payment details saved", Toast.LENGTH_SHORT).show();
            Log.e("payapplidetails", "payapplidetails: "+Constant.PaymentObject);



        }


    }
}
