package com.jeonhoeun.setting;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainContract.View{
    MainContract.Presenter presenter;
    Switch swTestMode;
    RadioGroup rgTelephony;
    EditText etAccount;
    Button btnOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swTestMode = findViewById(R.id.switch_onOff);
        swTestMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                presenter.onTestModeChanged(b);
            }
        });

        rgTelephony = findViewById(R.id.rg_telephony);
        rgTelephony.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedRbId = radioGroup.getCheckedRadioButtonId();
                String tagData = (String)findViewById(checkedRbId).getTag();
                presenter.onTelephonySelected(tagData);
            }
        });

        etAccount = findViewById(R.id.et_accountInput);
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onAccountChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnOk=findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onOk();
            }
        });

        presenter = new MainPresenter(this);
        presenter.onCreate();
    }

    @Override
    public void updateInputFields(final SettingDao settingDao) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swTestMode.setChecked(settingDao.isTestMode);

                if( settingDao.telephonyType!=null){
                    if( settingDao.telephonyType.equalsIgnoreCase("SKT")){
                        ((RadioButton)findViewById(R.id.rb_skt)).setChecked(true);
                    }else if( settingDao.telephonyType.equalsIgnoreCase("KT")){
                        ((RadioButton)findViewById(R.id.rb_kt)).setChecked(true);
                    }else if( settingDao.telephonyType.equalsIgnoreCase("LGU")){
                        ((RadioButton)findViewById(R.id.rb_lgu)).setChecked(true);
                    }else if( settingDao.telephonyType.equalsIgnoreCase("N/A")){
                        ((RadioButton)findViewById(R.id.rb_na)).setChecked(true);
                    }
                }

                if( settingDao.account!=null){
                    etAccount.setText(settingDao.account);
                }

            }
        });
    }

    @Override
    public void toast(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
