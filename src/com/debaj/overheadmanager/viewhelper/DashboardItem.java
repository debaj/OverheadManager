package com.debaj.overheadmanager.viewhelper;

import android.content.Intent;

public class DashboardItem {
    private int icon;
    private int label;
    private Intent intent;
    
    public DashboardItem (int icon, int label, Intent intent) {
        this.icon = icon;
        this.label = label;
        this.intent = intent;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int iconResId) {
        this.icon = iconResId;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
