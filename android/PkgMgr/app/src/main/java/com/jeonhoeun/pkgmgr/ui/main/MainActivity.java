package com.jeonhoeun.pkgmgr.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.jeonhoeun.pkgmgr.R;
import com.jeonhoeun.pkgmgr.ui.fragment.HeaderFragment;

public class MainActivity extends AppCompatActivity implements MainContract.View, HeaderFragment.HeaderFragmentCallback {

    public static final String STORE_NAME = "STORE_NAME";
    public static final String EMAIL = "EMAIL";

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);

        String storeName = getIntent().getStringExtra(STORE_NAME);
        String email = getIntent().getStringExtra(EMAIL);

        presenter.onCreate(storeName,email);

        ContentObserver co = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Log.i("TEST","changed");
            }
        };

        getContentResolver().registerContentObserver(Uri.parse("content://"+getString(R.string.setting_auth)), false, co);

    }

    @Override
    public void updateHeaderInfo(final String telephonyName, final String storeName, final String email) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.ll_header, new HeaderFragment(MainActivity.this, telephonyName, storeName, email));
                ft.commit();
            }
        });
    }

    @Override
    public void updateContent(final Fragment fragment) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.ll_contents, fragment);
                ft.commit();
            }
        });
    }

    @Override
    public void onClickSetting() {

    }

    @Override
    public Context getContext() {
        return this;
    }
}
