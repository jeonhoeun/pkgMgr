package com.jeonhoeun.pkgmgr.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.jeonhoeun.pkgmgr.R;
import com.jeonhoeun.pkgmgr.ui.fragment.HeaderFragment;
import com.jeonhoeun.pkgmgr.ui.fragment.SupplementaryServiceFragment;
import com.jeonhoeun.pkgmgr.ui.storeSelect.StoreSelectActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MainContract.View, HeaderFragment.HeaderFragmentCallback {

    public static final String STORE_NAME = "STORE_NAME";
    public static final String EMAIL = "EMAIL";

    private int channelId=100;

    private MainContract.Presenter presenter;
    HeaderFragment headerFragment;
    SupplementaryServiceFragment supplementaryServiceFragment;
    Timer joinTimer;
    String joiningServiceName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.ll_joinProcess).setVisibility(View.GONE);
            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( joinTimer!=null){
                    joinTimer.cancel();
                }
                findViewById(R.id.ll_joinProcess).setVisibility(View.GONE);
                joinTimer=null;
            }
        });

        presenter = new MainPresenter(this);

        String storeName = getIntent().getStringExtra(STORE_NAME);
        String email = getIntent().getStringExtra(EMAIL);

        presenter.onCreate(storeName,email);
    }

    @Override
    public void updateHeaderInfo(final String telephonyName, final String storeName, final String email) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if( headerFragment == null ){
                    headerFragment = new HeaderFragment(MainActivity.this, telephonyName, storeName, email);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.ll_header, headerFragment);
                    ft.commit();
                }else{
                    headerFragment.updateHeaderInfo(telephonyName,storeName,email);
                }
            }
        });
    }

    int lastTime = 15;
    NotificationCompat.Builder builder;

    SupplementaryServiceFragment.OnClickJoinListener onClickJoinListener = new SupplementaryServiceFragment.OnClickJoinListener() {
        @Override
        public void onClickJoin(String serviceName) {
            final View v = findViewById(R.id.ll_joinProcess);
            v.setVisibility(View.VISIBLE);
            if( joinTimer!=null){
                if( joiningServiceName.equals(serviceName)){
                    return;
                }else{
                    joinTimer.cancel();
                    joinTimer=null;
                }
            }

            joiningServiceName = serviceName;



            lastTime=15;
            ((TextView)v.findViewById(R.id.tv_title)).setText(String.format("%s 가입중입니다.",serviceName));
            ((TextView)v.findViewById(R.id.tv_lastTime)).setText(String.format("남은시간 : %d 초",lastTime));


            builder = new NotificationCompat.Builder(MainActivity.this,"channel000");
            builder.setSmallIcon(R.drawable.ic_settings_black_24dp);
            builder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
            builder.setOnlyAlertOnce(true);
            builder.setVibrate(new long[]{0L});
            builder.setContentTitle(String.format("%s 가입중입니다.",serviceName));
            builder.setContentText(String.format("남은시간 : %d 초",lastTime));

            NotificationManager notificationManager = (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("channel000", "innerName000", NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableLights(false);
                channel.enableVibration(false);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(channelId,builder.build());

            joinTimer = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    lastTime--;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView)v.findViewById(R.id.tv_lastTime)).setText(String.format("남은시간 : %d 초",lastTime));

                            builder.setContentText(String.format("남은시간 : %d 초",lastTime));

                            if( lastTime==0){
                                ((TextView)v.findViewById(R.id.tv_title)).setText(String.format("%s로 ",presenter.getEmail()));
                                ((TextView)v.findViewById(R.id.tv_lastTime)).setText("주문내역서가 발송되었습니다.");
                                joinTimer.cancel();
                                joinTimer=null;

                                builder.setContentTitle(String.format("%s로 ",presenter.getEmail()));
                                builder.setContentText("주문내역서가 발송되었습니다.");
                            }

                            NotificationManager notificationManager = (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(channelId,builder.build());
                        }
                    });
                }
            };
            joinTimer.schedule(tt,1000,1000);
        }
    };

    @Override
    public void updateContent(final String networkOperator) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if( supplementaryServiceFragment==null){
                    supplementaryServiceFragment = new SupplementaryServiceFragment(networkOperator, onClickJoinListener);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.ll_contents, supplementaryServiceFragment);
                    ft.commit();
                }
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        supplementaryServiceFragment.updateContentView(networkOperator);
                    }
                });


            }
        });
    }

    @Override
    public void onClickSetting() {
        startActivity(new Intent(this, StoreSelectActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestory();
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if( findViewById(R.id.ll_joinProcess).getVisibility() == View.VISIBLE ){
            findViewById(R.id.ll_joinProcess).setVisibility(View.GONE);
            return;
        }
        if( supplementaryServiceFragment.getCurrentStatus()==SupplementaryServiceFragment.STATUS_LISTING){
            super.onBackPressed();
            return;
        }else if( supplementaryServiceFragment.getCurrentStatus()==SupplementaryServiceFragment.STATUS_DETAIL){
            supplementaryServiceFragment.setCurrentStatus(SupplementaryServiceFragment.STATUS_LISTING);
            supplementaryServiceFragment.updateContentView(presenter.getTelephony());
        }
    }
}
