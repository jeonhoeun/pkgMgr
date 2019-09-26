package com.jeonhoeun.setting;

public interface MainContract {
    interface View{

        void updateInputFields(SettingDao settingDao);

        void toast(String string);
    }
    interface Presenter{

        void onCreate();

        void onTestModeChanged(boolean b);

        void onTelephonySelected(String selectedTelephony);

        void onAccountChanged(String toString);

        void onOk();
    }
}
