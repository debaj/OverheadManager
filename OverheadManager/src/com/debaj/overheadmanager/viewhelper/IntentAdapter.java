package com.debaj.overheadmanager.viewhelper;

import java.util.List;

import com.debaj.overheadmanager.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IntentAdapter extends BaseAdapter {

    private List<DashboardItem> intents;
    private Activity activity;

    public IntentAdapter(Activity activity, List<DashboardItem> intents) {
        this.intents = intents;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return intents.size();
    }

    @Override
    public Object getItem(int arg0) {
        return intents.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        LayoutInflater li = activity.getLayoutInflater();
        v = li.inflate(R.layout.dashboard_item, null);
        TextView tv = (TextView) v.findViewById(R.id.item_text);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, activity.getResources()
                .getDrawable(intents.get(position).getIcon()), null, null);
        tv.setText(activity.getString(intents.get(position).getLabel()));
        return v;
    }

}
