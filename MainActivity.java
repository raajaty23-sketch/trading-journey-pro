package com.raajat.tradingjourney;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class MainActivity extends Activity {
    private JournalDb db;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        db = new JournalDb(this);
        WebView w = new WebView(this);
        w.setWebViewClient(new WebViewClient());
        w.setWebChromeClient(new WebChromeClient());
        WebSettings s = w.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(true);
        w.addJavascriptInterface(new AndroidStore(db), "AndroidStore");
        w.loadUrl("file:///android_asset/index.html");
        setContentView(w);
    }

    @Override public void onBackPressed() {
        WebView w = (WebView)findViewById(android.R.id.content);
        if (w != null && w.canGoBack()) w.goBack(); else super.onBackPressed();
    }

    public static class AndroidStore {
        private final JournalDb db;
        AndroidStore(JournalDb db){this.db=db;}

        @JavascriptInterface public String get(String key){ return db.get(key); }

        @JavascriptInterface public void set(String key,String value){ db.set(key,value); }

        @JavascriptInterface public void remove(String key){ db.remove(key); }
    }

    static class JournalDb {
        private final SQLiteDatabase db;
        JournalDb(Context c){
            db = c.openOrCreateDatabase("trading_journey_v3.db", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS kv (k TEXT PRIMARY KEY, v TEXT NOT NULL)");
        }
        synchronized String get(String k){
            android.database.Cursor c=db.rawQuery("SELECT v FROM kv WHERE k=?",new String[]{k});
            try { return c.moveToFirst()?c.getString(0):""; } finally { c.close(); }
        }
        synchronized void set(String k,String v){
            ContentValues cv=new ContentValues(); cv.put("k",k); cv.put("v",v);
            db.insertWithOnConflict("kv",null,cv,SQLiteDatabase.CONFLICT_REPLACE);
        }
        synchronized void remove(String k){ db.delete("kv","k=?",new String[]{k}); }
    }
}
