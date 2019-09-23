package com.jeonhoeun.pkgmgr.ui.storeSelect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jeonhoeun.pkgmgr.R;
import com.jeonhoeun.pkgmgr.ui.main.MainActivity;

import java.util.ArrayList;

public class StoreSelectActivity extends AppCompatActivity implements StoreSelectContract.View{
    private StoreSelectContract.Presenter presenter;
    RadioGroup mRgStores;
    EditText mEtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_select);
        mRgStores = findViewById(R.id.rg_stores);
        mRgStores.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                StoreSelectPresenter.MergedPackageInfo info = (StoreSelectPresenter.MergedPackageInfo)rb.getTag();
                presenter.onCheckChanged(info);
            }
        });
        mEtEmail = findViewById(R.id.et_email);
        mEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.emailChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onOk();
            }
        });
        presenter = new StoreSelectPresenter(this);
        presenter.onCreate();
    }

    @Override
    public void updateStoreList(final ArrayList<StoreSelectPresenter.MergedPackageInfo> all){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(15, 15, 15, 15);
                for( int i=0;i<all.size();i++){
                    StoreSelectPresenter.MergedPackageInfo info = all.get(i);
                    RadioButton rb = new RadioButton(StoreSelectActivity.this);
                    rb.setId(i);
                    rb.setTag(info);
                    rb.setLayoutParams(params);
                    rb.setTextColor(Color.BLACK);
                    rb.setText(
                            String.format(
                                    "%s, %d개, %dKB",
                                    info.storeName,
                                    info.count,
                                    info.totalSize/1024)
                    );
                    mRgStores.addView(rb);
                    if( info.storeName.equals(getString(R.string.one_store))){
                        rb.setChecked(true);
                    }
                }
            }
        });
    }

    @Override
    public void updateEmailInfo(final String accountEmail) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEtEmail.setText(accountEmail);
            }
        });
    }

    @Override
    public void startMainActivity(String storeName, String email) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(MainActivity.STORE_NAME, storeName);
        intent.putExtra(MainActivity.EMAIL, email);
        startActivity(intent);
        finish();
    }

    @Override
    public void toast(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(StoreSelectActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
