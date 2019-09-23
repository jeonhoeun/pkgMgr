package com.jeonhoeun.pkgmgr.activity.storeSelect;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.jeonhoeun.pkgmgr.db.PkgDatabase;
import com.jeonhoeun.pkgmgr.db.entity.PackageInfo;
import com.jeonhoeun.pkgmgr.util.L;

import java.util.ArrayList;
import java.util.List;

public class StoreSelectPresenter implements StoreSelectContract.Presenter{
    private StoreSelectContract.View view;
    private Context context;
    ArrayList<MergedPackageInfo> mergedInfos = new ArrayList<>();
    private String email="";
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
                        mergedInfos.add(newInfo);
                    }
                }
                view.updateStoreList(mergedInfos);
                email = getAccountEmail();
                view.updateEmailInfo(email);

            }
        }).start();

    }

    @Override
    public void onOk() {
        L.i("email:"+email);
        view.startMainActivity("",email);
    }

    @Override
    public void emailChanged(String changed) {
        email = changed;
    }

    private String getAccountEmail(){
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
            return account.name;
        } else {
            return "unknown";
        }


//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(context);
//        if( acct!=null){
//            L.i("personName :"+acct.getDisplayName());
//            L.i("personGivenName:"+acct.getGivenName());
//            L.i("personFamilyName:"+acct.getFamilyName());
//            L.i("personEmail:"+acct.getEmail());
//            L.i("persionId:"+acct.getId());
//            return acct.getEmail();
//        }else{
//            return "unknown";
//        }
    }

    class MergedPackageInfo{
        String installerPackageName;
        int count;
        long totalSize;

        public MergedPackageInfo(String installerPackageName, long totalSize) {
            this.installerPackageName = installerPackageName;
            this.totalSize = totalSize;
            this.count=1;
        }
    }
}
