package com.jeonhoeun.pkgmgr.ui.fragment;

import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jeonhoeun.pkgmgr.R;

public class SupplementaryServiceFragment extends Fragment {
    String telephonyName;
    LinearLayout root;
    View skt;
    View kt;
    View lgu;
    View na;

    public static final int STATUS_LISTING=1;
    public static final int STATUS_DETAIL=2;
    public static final int STATUS_JOINING=3;
    private int currentStatus = STATUS_LISTING;
    private String currentTelephonyName = "";
    private String selectedServiceName="";

    public interface OnClickJoinListener{
        void onClickJoin(String serviceName);
    }

    private OnClickJoinListener onClickJoinListener;

    public SupplementaryServiceFragment(String telephonyName, OnClickJoinListener onClickJoinListener){
        this.telephonyName = telephonyName;
        this.onClickJoinListener = onClickJoinListener;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = (LinearLayout)inflater.inflate(R.layout.fragment_supplementary_service, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        root.setLayoutParams(params);
        return root;
    }

    public void updateContentView(String newTelephonyName){
        if( !currentTelephonyName.equals(newTelephonyName)){
            switchContentView(newTelephonyName);
            currentTelephonyName = newTelephonyName;
        }


        if( currentStatus == STATUS_LISTING){
            showListingView();
        }else if( currentStatus == STATUS_DETAIL){
            showDetailview();
        }
    }

    private void showListingView(){
        if( currentTelephonyName.equals(getString(R.string.n_a))){
            ((TextView)root.findViewById(R.id.tv_msg)).setText(R.string.no_services);
            return;
        }else{
            root.findViewById(R.id.ll_list).setVisibility(View.VISIBLE);
            root.findViewById(R.id.ll_detail).setVisibility(View.GONE);
        }
    }

    private void showDetailview(){
        if( currentTelephonyName.equals(getString(R.string.n_a))){
            ((TextView)root.findViewById(R.id.tv_msg)).setText(R.string.no_telephony);
            return;
        }else{
            root.findViewById(R.id.ll_list).setVisibility(View.GONE);
            root.findViewById(R.id.ll_detail).setVisibility(View.VISIBLE);
            ((TextView)root.findViewById(R.id.tv_serviceName)).setText(selectedServiceName);
        }
    }

    private void switchContentView(final String telephonyName){
        if( root!=null && root.getChildCount()> 0){
            root.removeAllViews();
        }
        View subView=null;
        if( telephonyName.equals(getString(R.string.skt))){
            skt = LayoutInflater.from(getContext()).inflate(R.layout.view_skt,root);
            subView = skt;
        }else if( telephonyName.equals(getString(R.string.kt))){
            kt = LayoutInflater.from(getContext()).inflate(R.layout.view_kt,root);
            subView = kt;
        }else if( telephonyName.equals(getString(R.string.lgu))){
            lgu = LayoutInflater.from(getContext()).inflate(R.layout.view_lgu,root);
            subView = lgu;
        }else if( telephonyName.equals(getString(R.string.n_a))){
            na = LayoutInflater.from(getContext()).inflate(R.layout.view_na,root);
            ((TextView)na.findViewById(R.id.tv_msg)).setText(R.string.no_services);
            return;
        }

        subView.findViewById(R.id.tv_supp1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStatus = STATUS_DETAIL;
                selectedServiceName = ((TextView)v).getText().toString();
                updateContentView(telephonyName);
            }
        });

        subView.findViewById(R.id.tv_supp2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStatus = STATUS_DETAIL;
                selectedServiceName = ((TextView)v).getText().toString();
                updateContentView(telephonyName);
            }
        });

        subView.findViewById(R.id.tv_supp3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStatus = STATUS_DETAIL;
                selectedServiceName = ((TextView)v).getText().toString();
                updateContentView(telephonyName);
            }
        });

        subView.findViewById(R.id.tv_supp4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStatus = STATUS_DETAIL;
                selectedServiceName = ((TextView)v).getText().toString();
                updateContentView(telephonyName);
            }
        });

        subView.findViewById(R.id.tv_supp5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStatus = STATUS_DETAIL;
                selectedServiceName = ((TextView)v).getText().toString();
                updateContentView(telephonyName);
            }
        });

        subView.findViewById(R.id.btn_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickJoinListener.onClickJoin(selectedServiceName);
            }
        });
    }

    private void updateDetailView(String telephonyName){
        if( telephonyName.equals(getString(R.string.n_a))){
            ((TextView)root.findViewById(R.id.tv_msg)).setText(R.string.no_telephony);
            return;
        }

        root.findViewById(R.id.ll_list).setVisibility(View.GONE);
        root.findViewById(R.id.ll_detail).setVisibility(View.VISIBLE);

        ((TextView)root.findViewById(R.id.tv_serviceName)).setText(selectedServiceName);


    }



}
