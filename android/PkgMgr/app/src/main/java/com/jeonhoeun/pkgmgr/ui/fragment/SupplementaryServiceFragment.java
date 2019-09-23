package com.jeonhoeun.pkgmgr.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
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
    public SupplementaryServiceFragment(String telephonyName){
        this.telephonyName = telephonyName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout root = (LinearLayout)inflater.inflate(R.layout.fragment_supplementary_service, null);
        LinearLayout ll = new LinearLayout(getContext());
        ll.setBackgroundColor(Color.WHITE);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(getContext());
        tv.setTextColor(Color.BLACK);
        tv.setText("부가서비스1");
        ll.addView(tv);
        root.addView(ll);
        return root;
    }
}
