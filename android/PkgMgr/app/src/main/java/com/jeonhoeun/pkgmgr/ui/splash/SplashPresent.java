package com.jeonhoeun.pkgmgr.ui.splash;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.jeonhoeun.pkgmgr.db.PkgDatabase;
import com.jeonhoeun.pkgmgr.db.entity.PackageInfo;
import com.jeonhoeun.pkgmgr.util.L;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SplashPresent implements SplashContract.Present{
    private SplashContract.View view;
    private Context context;
    public SplashPresent(SplashContract.View view){
        this.view = view;
        this.context = (Context)view;
    }

    @Override
    public void onCreate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<PackageInfo> infos = getPackageInfos();
                PkgDatabase.getInstance(context).packageInfoDao().insertAll(infos);
                List<PackageInfo> saved = PkgDatabase.getInstance(context).packageInfoDao().getAll();
                view.moveToStoreSelectActivity();
            }
        }).start();
    }

    private List<PackageInfo> getPackageInfos(){
        ArrayList<PackageInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> p= pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for( ApplicationInfo i : p){
            String ins = pm.getInstallerPackageName(i.packageName);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            if( ins!=null){
                android.content.pm.PackageInfo info;
                try {
                    info = pm.getPackageInfo(i.packageName, 0);
                    File f = new File(i.publicSourceDir);
                    PackageInfo item = new PackageInfo(
                            i.packageName,
                            pm.getInstallerPackageName(i.packageName),
                            info.versionName,
                            info.versionCode,
                            sdf.format(new Date(info.lastUpdateTime)),
                            sdf.format(new Date(info.firstInstallTime)),
                            i.publicSourceDir,
                            f.length());
                    list.add(item);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
