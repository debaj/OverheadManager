package com.debaj.overheadmanager.receiver;

import java.util.List;

import com.debaj.overheadmanager.BillsActivity;
import com.debaj.overheadmanager.R;
import com.debaj.overheadmanager.logic.db.DatabaseManager;
import com.debaj.overheadmanager.logic.model.bean.Bill;
import com.debaj.overheadmanager.logic.model.bean.BillDAO;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int DUE_BILLS_ID = 2342;
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
        dueBills = getDueBills();
        if (dueBills > 0) {
            buildNotification();
        }
    }

    private void buildNotification() {
        StringBuilder alarmText = new StringBuilder();
        alarmText.append(dueBills).append(" ");
        if (dueBills == 1) {
            alarmText.append(context
                    .getString(R.string.notification_due_bill_message));
        } else {
            alarmText.append(context
                    .getString(R.string.notification_due_bill_message_plural));
        }

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, BillsActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        builder.setContentTitle(context
                .getString(R.string.notification_due_bill_title));
        builder.setContentText(alarmText.toString());
        builder.setSmallIcon(R.drawable.widget_bills);
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);

        ((NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE)).notify(
                DUE_BILLS_ID, builder.getNotification());
    }

    private int getDueBills() {
        boolean notificationEnabled = preferences.getBoolean(
                "enableNotification", false);
        int dueInDays = Integer.parseInt(preferences.getString(
                "notificationPeriod", "0"));
        int bills = 0;
        if (notificationEnabled && dueInDays > 0) {

            BillDAO dao = new BillDAO();
            SQLiteDatabase db = DatabaseManager.getInstance(context)
                    .getReadableDatabase();
            List<Bill> due = dao.getDueBills(db, dueInDays, -1);
            db.close();
            bills = due.size();
        }
        return bills;
    }
}
