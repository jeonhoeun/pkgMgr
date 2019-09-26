package com.jeonhoeun.pkgmgr.ui.storeSelect;

import java.util.ArrayList;

public interface StoreSelectContract {
    interface View{

        void updateStoreList(ArrayList<StoreSelectPresenter.MergedPackageInfo> all);

        void updateEmailInfo(String accountEmail);

        void startMainActivity(String s, String email);

        void toast(String string);
    }
    interface Presenter{

        void onCreate();

        void onOk();

        void emailChanged(String toString);

        void onCheckChanged(StoreSelectPresenter.MergedPackageInfo info);

        void onDestory();
    }
}
