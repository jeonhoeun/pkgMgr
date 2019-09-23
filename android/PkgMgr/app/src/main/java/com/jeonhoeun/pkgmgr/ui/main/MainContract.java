package com.jeonhoeun.pkgmgr.ui.main;

import android.content.Context;

import androidx.fragment.app.Fragment;

public interface MainContract {
    interface View{

        Context getContext();

        void updateHeaderInfo(String telephonyName, String storeName, String email);

        void updateContent(Fragment fragment);
    }
    interface Presenter{

        void onCreate(String storeName, String email);
    }
}
