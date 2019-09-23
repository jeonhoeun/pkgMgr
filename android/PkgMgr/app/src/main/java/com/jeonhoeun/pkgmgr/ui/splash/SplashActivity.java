package com.jeonhoeun.pkgmgr.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.jeonhoeun.pkgmgr.R;
import com.jeonhoeun.pkgmgr.ui.storeSelect.StoreSelectActivity;

public class SplashActivity extends AppCompatActivity implements SplashContract.View{
    private SplashContract.Present present;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        present = new SplashPresent(this);
        present.onCreate();
    }

    @Override
    public void moveToStoreSelectActivity() {
        startActivity(new Intent(this, StoreSelectActivity.class));
        finish();
    }
}
