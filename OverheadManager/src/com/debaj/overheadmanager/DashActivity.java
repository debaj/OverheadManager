package com.debaj.overheadmanager;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.debaj.overheadmanager.viewhelper.DashboardItem;
import com.debaj.overheadmanager.viewhelper.IntentAdapter;

public class DashActivity extends CommonActivity {

    private GridView dashGrid;
    private static ArrayList<DashboardItem> intents;

    @Override
    public void inflateView() {
        showChangeLog();
        getLayoutInflater().inflate(R.layout.activity_dashboard, content, true);        
        initDashboard();
        dashGrid.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                    int position, long id) {
                startActivity(intents.get(position).getIntent());
            }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = false;
        Intent intent = null;
        switch(item.getItemId()) {
            case R.id.location:
                intent = new Intent(this, LocationsActivity.class);
                handled = true;
                break;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                handled = true;
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
        return handled;
    }

    private void initDashboard() {
        if (intents == null) {
            intents = new ArrayList<DashboardItem>();
            intents.clear();

            // Meter reading
            DashboardItem meterReading = new DashboardItem(R.drawable.ic_meter_reading, R.string.title_activity_meter_reading, new Intent(this, MeterReadingActivity.class));
            intents.add(meterReading);
            // Stats
            DashboardItem stat = new DashboardItem(R.drawable.ic_stats, R.string.title_activity_stats, new Intent(this, StatsActivity.class));
            intents.add(stat);
            // Bills
            DashboardItem bills = new DashboardItem(R.drawable.ic_bills, R.string.title_activity_bills, new Intent(this, BillsActivity.class));
            intents.add(bills);
            // Backup
            DashboardItem export = new DashboardItem(R.drawable.ic_backup, R.string.title_activity_backup, new Intent(this, BackupActivity.class));
            intents.add(export);
        }
        
        if (dashGrid == null) {
            dashGrid = (GridView) this.findViewById(R.id.dashboard);
        }
        IntentAdapter adapter = new IntentAdapter(this, intents);
        dashGrid.setAdapter(adapter);
    }

    private void showChangeLog() {

        PackageInfo pInfo;

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_META_DATA);
            if (preferences.getBoolean("enableChangelog", true)
                    && preferences.getLong("lastRunVersionCode", 0) < pInfo.versionCode) {
                Editor editor = preferences.edit();
                editor.putLong("lastRunVersionCode", pInfo.versionCode);
                editor.commit();
                startActivity(new Intent(this, ChangeLogActivity.class));
            }
        } catch (NameNotFoundException e) {
            Log.e("changelog", "Version not found!");
        }
    }
}
