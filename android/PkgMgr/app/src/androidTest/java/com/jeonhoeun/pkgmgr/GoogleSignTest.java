package com.jeonhoeun.pkgmgr;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.jeonhoeun.pkgmgr.util.L;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GoogleSignTest {
    @Test
    public void getAccount(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(appContext);
        if( acct!=null){
            L.i("personName :"+acct.getDisplayName());
            L.i("personGivenName:"+acct.getGivenName());
            L.i("personFamilyName:"+acct.getFamilyName());
            L.i("personEmail:"+acct.getEmail());
            L.i("persionId:"+acct.getId());
        }
    }
}
