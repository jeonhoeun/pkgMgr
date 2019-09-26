package com.jeonhoeun.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.Set;

public class MainPresenter implements MainContract.Presenter{
    private static final String TELEPHONY_TYPE = "TELEPHONY_TYPE";
    private static final String PREF_NAME="SETTING_PREF";
    private static final String TEST_MODE_ONOFF="TEST_MODE_ONOFF";
    private static final String ACCOUNT="ACCOUNT";
    MainContract.View view;
    Context context;

    private SettingDao settingDao;
    public MainPresenter(MainContract.View view){
        this.view = view;
        this.context = (Context)view;
    }

    @Override
    public void onCreate() {
        settingDao = getSetting(context);
        view.updateInputFields(settingDao);
    }

    @Override
    public void onTestModeChanged(boolean b) {
        settingDao.isTestMode = b;
    }

    @Override
    public void onTelephonySelected(String selectedTelephony) {
        settingDao.telephonyType = selectedTelephony;
    }

    @Override
    public void onAccountChanged(String toString) {
        settingDao.account = toString;
    }

    @Override
    public void onOk() {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(TEST_MODE_ONOFF,settingDao.isTestMode);
        editor.putString(TELEPHONY_TYPE,settingDao.telephonyType);
        editor.putString(ACCOUNT, settingDao.account);
        editor.commit();
        view.toast(context.getString(R.string.saved));

        context.getContentResolver().notifyChange(Uri.parse("content://"+context.getString(R.string.setting_auth)), null);

    }

    public static SettingDao getSetting(Context context){
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SettingDao rtnVal = new SettingDao();
        rtnVal.isTestMode = sp.getBoolean(TEST_MODE_ONOFF,false);
        String savedTelephony = sp.getString(TELEPHONY_TYPE,null);
        rtnVal.telephonyType = savedTelephony==null?"SKT":savedTelephony;
        String savedAccount = sp.getString(ACCOUNT,null);
        rtnVal.account = savedAccount==null?"":savedAccount;
        return rtnVal;
    }
}
