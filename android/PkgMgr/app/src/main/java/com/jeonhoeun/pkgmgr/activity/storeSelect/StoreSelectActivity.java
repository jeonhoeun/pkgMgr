package com.jeonhoeun.pkgmgr.activity.storeSelect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jeonhoeun.pkgmgr.R;
import com.jeonhoeun.pkgmgr.activity.MainActivity;
import com.jeonhoeun.pkgmgr.db.entity.PackageInfo;

import java.util.ArrayList;
import java.util.List;

public class StoreSelectActivity extends AppCompatActivity implements StoreSelectContract.View{
    private StoreSelectContract.Presenter presenter;
    RadioGroup mRgStores;
    EditText mEtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_select);
        mRgStores = findViewById(R.id.rg_stores);
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
                for(StoreSelectPresenter.MergedPackageInfo info : all ){
                    RadioButton rb = new RadioButton(StoreSelectActivity.this);
                    rb.setText(info.installerPackageName+" count:"+info.count+" total Size:"+info.totalSize);
                    mRgStores.addView(rb);
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
    public void startMainActivity(String s, String email) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
