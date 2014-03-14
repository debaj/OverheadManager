package com.debaj.overheadmanager;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class SettingsActivity extends PreferenceActivity {

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragment caller,
            Preference pref) {
        // TODO Auto-generated method stub
        return super.onPreferenceStartFragment(caller, pref);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

}
