package in.amruthashala.momapp.screens;

import android.app.Application;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;

import java.util.List;
import java.util.Map;

import in.amruthashala.momapp.cookies.PersistentHttpCookieStore;
import okhttp3.Cookie;

public class MyApplication extends Application {
    PersistentHttpCookieStore cookieStore;

    public void onCreate() {
        super.onCreate();
        // enable cookies
        java.net.CookieManager cookieManager = new java.net.CookieManager();
        cookieStore = new PersistentHttpCookieStore(getApplicationContext());
         cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
//testing

    }

        }

