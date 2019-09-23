package com.jeonhoeun.pkgmgr.activity.storeSelect;

import android.content.Context;

import com.jeonhoeun.pkgmgr.db.PkgDatabase;
import com.jeonhoeun.pkgmgr.db.entity.PackageInfo;

import java.util.ArrayList;
import java.util.List;

public class StoreSelectPresenter implements StoreSelectContract.Presenter{
    private StoreSelectContract.View view;
    private Context context;
    ArrayList<MergedPackageInfo> mergedInfos = new ArrayList<>();
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
            }
        }).start();

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
