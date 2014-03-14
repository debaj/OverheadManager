package com.debaj.overheadmanager.widget;

import com.debaj.overheadmanager.BillsActivity;
import com.debaj.overheadmanager.DashActivity;
import com.debaj.overheadmanager.MeterReadingActivity;
import com.debaj.overheadmanager.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class OverheadWidget extends AppWidgetProvider {

    private static final int REQUEST_CODE = 2013;

    @Override
    public void onUpdate(Context context, AppWidgetManager mgr,
            int[] appWidgetIds) {
        ComponentName me = new ComponentName(context, OverheadWidget.class);
        mgr.updateAppWidget(me, buildUpdate(context, appWidgetIds));

    }

    private RemoteViews buildUpdate(Context context, int[] appWidgetIds) {

        RemoteViews updateViews = new RemoteViews(context.getPackageName(),
                R.layout.overhead_widget);
        Intent dashIntent = new Intent(context, DashActivity.class);
        Intent readingIntent = new Intent(context, MeterReadingActivity.class);
        Intent billsIntent = new Intent(context, BillsActivity.class);
        PendingIntent dashPI = PendingIntent.getActivity(context, REQUEST_CODE,
                dashIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent readingPI = PendingIntent.getActivity(context,
                REQUEST_CODE, readingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent billsPI = PendingIntent.getActivity(context,
                REQUEST_CODE, billsIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        updateViews.setOnClickPendingIntent(R.id.dashButton, dashPI);
        updateViews.setOnClickPendingIntent(R.id.readingsButton, readingPI);
        updateViews.setOnClickPendingIntent(R.id.billsButton, billsPI);

        return (updateViews);
    }

}
