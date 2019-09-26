package com.jeonhoeun.pkgmgr.db;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.TelephonyManager;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jeonhoeun.pkgmgr.R;
import com.jeonhoeun.pkgmgr.db.dao.PackageInfoDao;
import com.jeonhoeun.pkgmgr.db.dao.SettingDao;
import com.jeonhoeun.pkgmgr.db.entity.PackageInfo;

@Database(entities = {PackageInfo.class}, version = 1)
public abstract class PkgDatabase extends RoomDatabase {
    public static Uri SETTING_AUTH = Uri.parse("content://com.jeonhoeun.setting");
    public static String SETTING_AUTH_READING_PERMISSION = "com.jeonhoeun.READ_SETTING";
    private static PkgDatabase inst;
    private static SettingDao settingDao;

    public static SettingDao getSettingDao() {
        return settingDao;
    }

    public static void updateSettingDao(Context context) {
        settingDao=null;
        Cursor c = context.getContentResolver().query(Uri.parse("content://com.jeonhoeun.setting"),null,null,null,null);
        if( c!=null){
            if( c.getCount()==1){
                settingDao = new SettingDao();
                c.moveToFirst();
                String isTest = c.getString(0);
                settingDao.isTestMode = isTest.equalsIgnoreCase("TRUE");
                settingDao.telephonyType = c.getString(1);
                settingDao.account = c.getString(2);
            }
            c.close();
        }
    }

    public abstract PackageInfoDao packageInfoDao();

    public static PkgDatabase getInstance(Context context){
        if( inst == null){
            inst = Room.databaseBuilder(context.getApplicationContext(),
                    PkgDatabase.class, "pkg_database.db")
                    .build();
        }
        return inst;
    }

    public static String getAccountEmail(Context context, SettingDao settingDao){
        boolean needAccountManager=false;
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

    public static String getNetworkOperator(Context context, SettingDao settingDao) {
        boolean needTelephonyManager = false;
        if( settingDao == null ){
            needTelephonyManager = true;
        }else if( settingDao.isTestMode==false){
            needTelephonyManager = true;
        }

        if( needTelephonyManager == false ){
            return settingDao.telephonyType;
        }
        String myNO = null;
        TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        myNO = mgr.getSimOperator();

        if (myNO.equals("45005") || myNO.equals("45012")) {
            myNO = context.getString(R.string.skt);
        }
        else if (myNO.equals("45002") || myNO.equals("45004") || myNO.equals("45007") || myNO.equals("45008")) {
            myNO = context.getString(R.string.kt);
        }
        else if (myNO.equals("45006")) {
            myNO = context.getString(R.string.lgu);
        }

        return myNO;
    }
}
