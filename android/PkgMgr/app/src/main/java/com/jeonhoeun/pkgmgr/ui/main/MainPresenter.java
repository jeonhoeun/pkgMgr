package com.jeonhoeun.pkgmgr.ui.main;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

import com.jeonhoeun.pkgmgr.db.PkgDatabase;

public class MainPresenter implements MainContract.Presenter{
    MainContract.View view;
    Context context;
    String storeName;
    String email="";
    String networkOperator;
    ContentObserver coSettingChanged;
    public MainPresenter(MainContract.View view){
        this.view = view;
        this.context = (Context)view;
    }

    @Override
    public void onCreate(String storeNameStart, String emailStart) {

        coSettingChanged = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                PkgDatabase.updateSettingDao(context);
                MainPresenter.this.email = PkgDatabase.getAccountEmail(context, PkgDatabase.getSettingDao());
                MainPresenter.this.networkOperator = PkgDatabase.getNetworkOperator(context, PkgDatabase.getSettingDao());
                view.updateHeaderInfo(networkOperator,MainPresenter.this.storeName,MainPresenter.this.email);
                view.updateContent(networkOperator);
            }
        };

        try{
            context.getContentResolver().registerContentObserver(PkgDatabase.SETTING_AUTH, false, coSettingChanged);
        }catch (Exception e){
            //e.printStackTrace();
        }

        this.storeName = storeNameStart;
        this.email = emailStart;
        this.networkOperator = PkgDatabase.getNetworkOperator(context, PkgDatabase.getSettingDao());

        view.updateHeaderInfo(networkOperator,storeNameStart,emailStart);
        view.updateContent(networkOperator);
    }

    @Override
    public void onDestory() {
        try{
            context.getContentResolver().unregisterContentObserver(coSettingChanged);
        }catch (Exception e){
            //e.printStackTrace();
        }
    }

    @Override
    public String getTelephony() {
        return networkOperator;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
