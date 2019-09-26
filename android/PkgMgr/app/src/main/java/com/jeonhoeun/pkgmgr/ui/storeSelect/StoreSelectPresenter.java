package com.jeonhoeun.pkgmgr.ui.storeSelect;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.jeonhoeun.pkgmgr.R;
import com.jeonhoeun.pkgmgr.db.PkgDatabase;
import com.jeonhoeun.pkgmgr.db.dao.SettingDao;
import com.jeonhoeun.pkgmgr.db.entity.PackageInfo;
import com.jeonhoeun.pkgmgr.util.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StoreSelectPresenter implements StoreSelectContract.Presenter{
    private StoreSelectContract.View view;
    private Context context;
    ArrayList<MergedPackageInfo> mergedInfos = new ArrayList<>();
    private String email="";
    MergedPackageInfo selectedInfo;
    public StoreSelectPresenter(StoreSelectContract.View view){
        this.view = view;
        this.context = (Context)view;
    }

    @Override
    public void onCreate() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<PackageInfo> infos = PkgDatabase.getInstance(context).packageInfoDao().getAll();
                for(PackageInfo info : infos ){
                    boolean merged=false;
                    for( MergedPackageInfo refInfo : mergedInfos){
                        if( refInfo.installerPackageName.equals(info.getInstallerPackageName())){
                            refInfo.count++;
                            refInfo.totalSize+=info.getPublicSourceSize();
                            merged=true;
                            break;
                        }
                    }
                    if( merged==false){
                        MergedPackageInfo newInfo = new MergedPackageInfo(info.getInstallerPackageName(), info.getPublicSourceSize());
                        if( newInfo.storeName.equals(context.getString(R.string.one_store))){
                            mergedInfos.add(0,newInfo);
                        }else {
                            mergedInfos.add(newInfo);
                        }
                    }
                }


                Cursor c = context.getContentResolver().query(Uri.parse("content://com.jeonhoeun.setting"),null,null,null,null);
                SettingDao settingDao=null;
                if( c!=null){
                    if( c.getCount()==1){
                        settingDao = new SettingDao();
                        c.moveToFirst();
                        String isTest = c.getString(0);
                        settingDao.isTestMode = isTest.equalsIgnoreCase("TRUE");
                        settingDao.telephonyType = c.getString(1);
                        settingDao.account = c.getString(2);
                    }
                }

                email = getAccountEmail(settingDao);
                view.updateStoreList(mergedInfos);
                view.updateEmailInfo(email);

            }
        }).start();

    }

    @Override
    public void onOk() {
        if(TextUtils.isEmpty(email)){
            view.toast(context.getString(R.string.please_input_email));
            return;
        }

        if(selectedInfo==null){
            view.toast(context.getString(R.string.please_select_prefered_store));
            return;
        }

        view.startMainActivity(selectedInfo.storeName,email);
    }

    @Override
    public void emailChanged(String changed) {
        email = changed;
    }

    @Override
    public void onCheckChanged(MergedPackageInfo info) {
        selectedInfo = info;
    }

    private String getAccountEmail(SettingDao settingDao){
        boolean needAccountManager=true;
        if( settingDao==null){
            needAccountManager=true;
        }else if(settingDao.isTestMode==false){
            needAccountManager=true;
        }

        if( needAccountManager==false){
            return settingDao.account;
        }

        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
            return account.name;
        } else {
            return "unknown";
        }
    }

    class MergedPackageInfo{
        String installerPackageName;
        String storeName;
        int count;
        long totalSize;

        public MergedPackageInfo(String installerPackageName, long totalSize) {
            this.installerPackageName = installerPackageName;
            this.storeName = getStoreName(installerPackageName);
            this.totalSize = totalSize;
            this.count=1;
        }

        private String getStoreName(String installerPackageName){
            if( installerPackageName.equals("com.skt.skaf.A000Z00040") || installerPackageName.equals("com.kt.olleh.storefront")){
                return context.getString(R.string.one_store);
            }else if( installerPackageName.equals("com.android.vending")){
                return context.getString(R.string.google_play_store);
            }else if( installerPackageName.equals("com.sec.android.app.samsungapps")){
                return context.getString(R.string.galaxy_apps);
            }else if( installerPackageName.equals("com.sec.android.easyMover.Agent")){
                return context.getString(R.string.samsung_smart_switch);
            }else if( installerPackageName.equals("com.google.android.packageinstaller")){
                return context.getString(R.string.android_package_installer);
            }else if( installerPackageName.equals("com.samsung.android.mateagent")){
                return context.getString(R.string.samsung_mate_agent);
            }else{
                return installerPackageName;
            }
        }
    }
}
