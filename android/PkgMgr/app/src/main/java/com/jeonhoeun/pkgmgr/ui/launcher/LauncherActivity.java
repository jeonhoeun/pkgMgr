package com.jeonhoeun.pkgmgr.ui.launcher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jeonhoeun.pkgmgr.R;
import com.jeonhoeun.pkgmgr.db.PkgDatabase;
import com.jeonhoeun.pkgmgr.ui.splash.SplashActivity;

import java.util.ArrayList;

public class LauncherActivity extends AppCompatActivity {
    private int REQUEST_PERMISSIONS=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> reqs = new ArrayList<>();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)!= PackageManager.PERMISSION_GRANTED){
            reqs.add(Manifest.permission.GET_ACCOUNTS);
        }

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            reqs.add(Manifest.permission.READ_CONTACTS);
        }

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            reqs.add(Manifest.permission.READ_PHONE_STATE);
        }

//        if(ContextCompat.checkSelfPermission(this, PkgDatabase.SETTING_AUTH_READING_PERMISSION)!= PackageManager.PERMISSION_GRANTED){
//            reqs.add(PkgDatabase.SETTING_AUTH_READING_PERMISSION);
//        }

        if( reqs.size()>0){
            ActivityCompat.requestPermissions(this,
                    reqs.toArray(new String[]{""}),
                    REQUEST_PERMISSIONS);
        }else{
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( requestCode == REQUEST_PERMISSIONS){
            for(int result : grantResults){
                if( result != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, R.string.need_all_permission, Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
    }
}
