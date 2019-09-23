package com.jeonhoeun.pkgmgr;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.jeonhoeun.pkgmgr.util.L;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PkgInfoTest {
    @Test
    public void getPkgInfo() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        PackageManager pm = appContext.getPackageManager();
        List<ApplicationInfo> p= pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for( ApplicationInfo i : p){
            String ins = pm.getInstallerPackageName(i.packageName);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            if( ins!=null){
                PackageInfo info;
                try {
                    info = pm.getPackageInfo(i.packageName, 0);
                    L.i("packageName:"+i.packageName+"========================================");
                    L.i("installed_store:" +pm.getInstallerPackageName(i.packageName));
                    L.i("versionName:"+info.versionName);
                    L.i("versionCode:"+info.versionCode);
                    L.i("lastUPdateTime:"+sdf.format(new Date(info.lastUpdateTime)));
                    L.i("firstInstallTime:"+sdf.format(new Date(info.firstInstallTime)));
                    L.i("apk location:"+i.publicSourceDir);
                    File f = new File(i.publicSourceDir);
                    L.i("apk size :"+f.length());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
