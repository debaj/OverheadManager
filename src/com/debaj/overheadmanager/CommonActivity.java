package com.debaj.overheadmanager;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.debaj.overheadmanager.R;
import com.debaj.overheadmanager.db.DatabaseManager;

public abstract class CommonActivity extends SherlockActivity {
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
        correctActionBarBackground();
        setupAd();        
        inflateView();
    }

    private void correctActionBarBackground() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped);
            bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            actionBar.setBackgroundDrawable(bg);

            BitmapDrawable bgSplit = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped_split_img);
            bgSplit.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
            actionBar.setSplitBackgroundDrawable(bgSplit);
        }
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
