package com.jeonhoeun.pkgmgr.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;

import com.jeonhoeun.pkgmgr.R;
import com.jeonhoeun.pkgmgr.activity.storeSelect.StoreSelectActivity;
import com.jeonhoeun.pkgmgr.util.L;

public class SplashActivity extends AppCompatActivity implements SplashContract.View{
    private SplashContract.Present present;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i("onCreate");
        setContentView(R.layout.activity_splash);
        present = new SplashPresent(this);

        AccountManager accountManager = AccountManager.get(this);
        Account account = getAccount(accountManager);

        if (account != null) {
            L.i("account:"+account.name);
        }else{
            L.i("account null");
        }
        present.onCreate();
    }

    private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    @Override
    public void moveToStoreSelectActivity() {
        startActivity(new Intent(this, StoreSelectActivity.class));
        finish();
    }
}
