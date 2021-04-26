package in.amruthashala.momapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import butterknife.BindView;
import butterknife.OnClick;
import in.amruthashala.momapp.R;
import in.amruthashala.momapp.common.BaseClass;

public class SignUp1 extends BaseClass implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.checkbox_others)
    CheckBox cbOthers;
    @BindView(R.id.edit_comments)
    EditText etComments;
    @BindView(R.id.btn_proceed)
    AppCompatButton acProceed;
    @BindView(R.id.checkbox_1)
    CheckBox cb1;
    @BindView(R.id.checkbox_2)
    CheckBox cb2;
    @BindView(R.id.checkbox_3)
    CheckBox cb3;
    @BindView(R.id.checkbox_4)
    CheckBox cb4;
    @BindView(R.id.checkbox_5)
    CheckBox cb5;
    @BindView(R.id.checkbox_6)
    CheckBox cb6;
    @BindView(R.id.checkbox_7)
    CheckBox cb7;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cbOthers.setOnCheckedChangeListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.sign_up_1;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.checkbox_others:
                if (b) {
                    etComments.setText("");
                    etComments.setVisibility(View.VISIBLE);
                } else {
                    etComments.setText("");
                    etComments.setVisibility(View.GONE);
                }
                break;
        }
    }

    @OnClick(R.id.btn_proceed)
    public void onClick() {
        if (cb1.isChecked() || cb2.isChecked() || cb3.isChecked() || cb4.isChecked() || cb5.isChecked() || cb6.isChecked() || cb7.isChecked() || cbOthers.isChecked()) {
            startActivity(new Intent(this, SignUp2.class));
        } else {
            Toast.makeText(SignUp1.this, "Please select any one of the above", Toast.LENGTH_LONG).show();
        }
    }
}
