package com.debaj.overheadmanager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.debaj.overheadmanager.R;
import com.debaj.overheadmanager.logic.db.DatabaseManager;

public abstract class CommonActivity extends ActionBarActivity {
    private static final String AD_UNIT_ID = "a14f172cfc65622";
    
    public static SharedPreferences preferences;
    public static final String PREF = "overheadmangerprefs";
    
    protected ActionBar actionBar;
    protected RelativeLayout content;
    protected SQLiteDatabase db;
    private RelativeLayout adLayout;
    private AdView adView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        db = DatabaseManager.getInstance(getApplicationContext()).getWritableDatabase();
        if (preferences == null) {
            preferences = getSharedPreferences(PREF, MODE_PRIVATE);
        }
        actionBar = getSupportActionBar();
        content = (RelativeLayout) findViewById(R.id.content);
        setupAd();        
        inflateView();
    }
    
    private void setupAd() {
        adView = new AdView(this, AdSize.BANNER, AD_UNIT_ID);
        adLayout = (RelativeLayout) findViewById(R.id.ad);
        adLayout.addView(adView);

        AdRequest request = new AdRequest();
        request.addTestDevice("3B17AE550BB201FC490470098FD558B8");
        adView.loadAd(request);
    }
    
    @Override
    public abstract boolean onCreateOptionsMenu(Menu menu);
    
    @Override
    public abstract boolean onOptionsItemSelected(MenuItem item);
    
    public abstract void inflateView();
}
