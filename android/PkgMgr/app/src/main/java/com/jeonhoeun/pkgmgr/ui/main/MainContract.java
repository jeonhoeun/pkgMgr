package com.jeonhoeun.pkgmgr.ui.main;

import androidx.fragment.app.Fragment;

public interface MainContract {
    interface View{

        void updateHeaderInfo(String telephonyName, String storeName, String email);

        void updateContent(String networkOperator);
    }
    interface Presenter{

        void onCreate(String storeName, String email);

        void onDestory();

        String getTelephony();

        String getEmail();
    }
}
