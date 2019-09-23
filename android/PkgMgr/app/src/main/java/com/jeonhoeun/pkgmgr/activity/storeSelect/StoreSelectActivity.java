package com.jeonhoeun.pkgmgr.activity.storeSelect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jeonhoeun.pkgmgr.R;
import com.jeonhoeun.pkgmgr.db.entity.PackageInfo;

import java.util.ArrayList;
import java.util.List;

public class StoreSelectActivity extends AppCompatActivity implements StoreSelectContract.View{
    private StoreSelectContract.Presenter presenter;
    RadioGroup mRgStores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_select);
        mRgStores = findViewById(R.id.rg_stores);
        this.presenter = new StoreSelectPresenter(this);
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
}
