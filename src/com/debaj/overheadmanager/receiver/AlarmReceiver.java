package com.debaj.overheadmanager.receiver;

import com.debaj.overheadmanager.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int DUE_BILLS_ID = 2342;
    // protected DatabaseOperator db;
    private Context context;
    private SharedPreferences preferences;

    private int dueBills;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        setupReceiver();
    }

    private void setupReceiver() {
        // db = new DatabaseOperator(context);
        // dueBills = getDueBills();
        // if (dueBills > 0) {
        // buildNotification();
        // }
    }

    private void buildNotification() {
        StringBuilder alarmText = new StringBuilder();
        alarmText.append(dueBills).append(" ");
        if (dueBills == 1) {
            alarmText.append(context.getString(R.string.notification_due_bill));
        } else {
            alarmText
                    .append(context.getString(R.string.notification_due_bills));
        }

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(ns);

        Notification notification = new Notification(R.drawable.widget_bills,
                context.getString(R.string.notification_title),
                System.currentTimeMillis());
        // notification.flags |= Notification.FLAG_AUTO_CANCEL
        // | Notification.FLAG_SHOW_LIGHTS;
        //
        // CharSequence contentTitle = context
        // .getString(R.string.notification_title);
        // Intent notificationIntent = new Intent(context, BillsActivity.class);
        // PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
        // notificationIntent, 0);
        //
        // notification.setLatestEventInfo(context, contentTitle,
        // alarmText.toString(), contentIntent);

        mNotificationManager.notify(DUE_BILLS_ID, notification);

    }

    // private int getDueBills() {
    // boolean notificationEnabled = preferences.getBoolean(
    // "enableNotification", false);
    // int dueInDays = Integer.parseInt(preferences.getString(
    // "notificationPeriod", "0"));
    //
    // int bills = 0;
    //
    // if (notificationEnabled && dueInDays > 0) {
    //
    // db.open();
    //
    // Cursor due = db.getDueBills(dueInDays, -1);
    // bills = due.getCount();
    // due.close();
    //
    // db.close();
    //
    // }
    // return bills;
    // }
}
