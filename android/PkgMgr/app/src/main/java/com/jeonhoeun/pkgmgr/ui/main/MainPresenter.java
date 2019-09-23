package com.jeonhoeun.pkgmgr.ui.main;

import android.content.Context;
import android.telephony.TelephonyManager;

import androidx.fragment.app.Fragment;

import com.jeonhoeun.pkgmgr.ui.fragment.SupplementaryServiceFragment;

public class MainPresenter implements MainContract.Presenter{
    MainContract.View view;
    String storeName;
    String email;
    String networkOperator;
    public MainPresenter(MainContract.View view){
        this.view = view;
    }

    @Override
    public void onCreate(String storeName, String email) {
        this.storeName = storeName;
        this.email = email;
        this.networkOperator = getNetworkOperator(view.getContext());
        view.updateHeaderInfo(networkOperator,storeName,email);
        view.updateContent(getSupplementaryServiceLIstFragment(networkOperator));
    }

    private Fragment getSupplementaryServiceLIstFragment(String networkOperator){
        SupplementaryServiceFragment fragment = new SupplementaryServiceFragment(networkOperator);
        return fragment;
    }

    private String getNetworkOperator(Context context) {
        String myNO = null;
        TelephonyManager mgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        myNO = mgr.getSimOperator();

        if (myNO.equals("45005") || myNO.equals("45012")) {
            myNO = "SK";
        }
        else if (myNO.equals("45002") || myNO.equals("45004") || myNO.equals("45007") || myNO.equals("45008")) {
            myNO = "KT";
        }
        else if (myNO.equals("45006")) {
            myNO = "LG";
        }

        return myNO;
    }
}
