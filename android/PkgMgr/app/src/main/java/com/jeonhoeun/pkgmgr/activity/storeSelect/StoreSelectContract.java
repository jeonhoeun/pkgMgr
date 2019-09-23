package com.jeonhoeun.pkgmgr.activity.storeSelect;

import java.util.ArrayList;

public interface StoreSelectContract {
    interface View{

        void updateStoreList(ArrayList<StoreSelectPresenter.MergedPackageInfo> all);
    }
    interface Presenter{

        void onCreate();
    }
}
