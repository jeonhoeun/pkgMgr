package com.jeonhoeun.pkgmgr.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jeonhoeun.pkgmgr.R;

public class HeaderFragment extends Fragment {
    HeaderFragmentCallback callback;
    TextView tvTelephonyName;
    TextView tvStoreAndEmail;
    String telephonyName;
    String storeName;
    String email;

    public interface HeaderFragmentCallback{
        void onClickSetting();

    }

    public HeaderFragment(HeaderFragmentCallback callback,String telephonyName,String storeName, String email){
        this.callback = callback;
        this.telephonyName = telephonyName;
        this.storeName=storeName;
        this.email= email;
    }

    public void updateHeaderInfo(String telephonyName, String storeName, String email) {
        this.telephonyName = telephonyName;
        this.storeName=storeName;
        this.email= email;
        tvTelephonyName.setText(telephonyName);
        tvStoreAndEmail.setText(String.format("%s, %s", storeName, email));
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_header, null);
        tvTelephonyName = root.findViewById(R.id.tv_telephony_name);
        tvStoreAndEmail = root.findViewById(R.id.tv_store_and_email);
        updateHeaderInfo(telephonyName,storeName,email);
        root.findViewById(R.id.iv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickSetting();
            }
        });
        return root;
    }
}
