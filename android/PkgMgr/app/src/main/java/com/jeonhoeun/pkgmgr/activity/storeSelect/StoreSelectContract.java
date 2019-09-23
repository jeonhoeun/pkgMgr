package com.jeonhoeun.pkgmgr.activity.storeSelect;

import java.util.ArrayList;

public interface StoreSelectContract {
    interface View{

        void updateStoreList(ArrayList<StoreSelectPresenter.MergedPackageInfo> all);

        void updateEmailInfo(String accountEmail);

        void startMainActivity(String s, String email);
    }
    interface Presenter{

        void onCreate();

        void onOk();

        void emailChanged(String toString);
    }
}
