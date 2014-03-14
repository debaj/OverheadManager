package com.debaj.overheadmanager.logic.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

import com.debaj.overheadmanager.R;

public class StringUtil {
    
    private StringUtil() {}
    
    public static boolean isSet(String string) {
        return (string != null && !"".equals(string));
    }
    
    public static String formatDate(Date date, Context context) {
        SimpleDateFormat df = new SimpleDateFormat(context.getString(R.string.format_date));
        return df.format(date);
    }
    
    public static String getDateForKey(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(date);
    }
    
    public static String formatDayDate(Date date, Context context) {
        SimpleDateFormat df = new SimpleDateFormat(context.getString(R.string.format_date_day_only));
        return df.format(date);
    }
    
    public static String formatCurrency (float amount, Context context) {
        return Float.toString(amount) + " " + context.getString(R.string.format_currency);
    }
}
