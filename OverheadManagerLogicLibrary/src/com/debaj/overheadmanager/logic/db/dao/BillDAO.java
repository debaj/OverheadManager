package com.debaj.overheadmanager.logic.db.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.debaj.overheadmanager.logic.db.Statements;
import com.debaj.overheadmanager.logic.db.Tables;
import com.debaj.overheadmanager.logic.model.bean.Bill;
import com.debaj.overheadmanager.logic.util.CalendarUtil;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BillDAO {
    public static enum BillDB {
        ID("_id"), DUE_DATE("due_date"), SUM("sum"), TYPE("type"), PAID("paid"), PROFILE(
                "profile"), CONSUMPTION("consumption");

        private String columnName;

        private BillDB(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return this.columnName;
        }
    }

    public synchronized long insertBill(SQLiteDatabase db, long dueDate,
            float sum, float consumption, String type, int profile) {
        ContentValues cv = new ContentValues();
        cv.put(BillDB.DUE_DATE.getColumnName(), dueDate);
        cv.put(BillDB.SUM.getColumnName(), sum);
        cv.put(BillDB.CONSUMPTION.getColumnName(), consumption);
        cv.put(BillDB.TYPE.getColumnName(), type);
        cv.put(BillDB.PAID.getColumnName(), 0);
        cv.put(BillDB.PROFILE.getColumnName(), profile);
        return db.insert(Tables.BILLS.getTableName(), null, cv);
    }

    public synchronized long settleBill(SQLiteDatabase db, int id) {
        ContentValues cv = new ContentValues();
        cv.put(BillDB.PAID.getColumnName(), 1);
        return db.update(Tables.BILLS.getTableName(), cv,
                BillDB.ID.getColumnName() + " = ?",
                new String[] { Integer.toString(id) });
    }

    public synchronized List<Bill> getAllBills(SQLiteDatabase db) {
        List<Bill> bills = new ArrayList<Bill>();
        Cursor c = db
                .rawQuery(Statements.DB_BILLS_GET_ALL_BILLS.getStatement(),
                        new String[0]);
        c.moveToFirst();
        while (c.moveToNext()) {
            bills.add(billFromCursor(c));
        }
        c.close();
        return bills;
    }

    public synchronized List<Bill> getSettledBills(SQLiteDatabase db,
            int dueInDays, int profile) {
        List<Bill> bills = new ArrayList<Bill>();
        String sql = Statements.DB_BILLS_GET_SETTLED_BILLS.getStatement();
        String[] args;
        if (profile > -1) {
            sql = sql + Statements.DB_BILLS_PROFILE_FRAGMENT.getStatement();
            args = new String[] { Integer.toString(profile) };
        } else {
            args = new String[0];
        }
        Cursor c = db.rawQuery(sql, args);
        c.moveToFirst();
        while (c.moveToNext()) {
            bills.add(billFromCursor(c));
        }
        c.close();
        return bills;
    }

    public synchronized List<Bill> getDueBills(SQLiteDatabase db,
            int dueInDays, int profile) {
        List<Bill> bills = new ArrayList<Bill>();
        Calendar cal = CalendarUtil.getTodayMidnight();
        cal.add(Calendar.DAY_OF_YEAR, dueInDays);
        String sql = Statements.DB_BILLS_GET_DUE_BILLS.getStatement();
        String[] args;
        if (profile > -1) {
            sql = sql + Statements.DB_BILLS_PROFILE_FRAGMENT.getStatement();
            args = new String[] { Long.toString(cal.getTimeInMillis()),
                    Integer.toString(profile) };
        } else {
            args = new String[] { Long.toString(cal.getTimeInMillis()) };
        }
        Cursor c = db.rawQuery(sql, args);
        c.moveToFirst();
        while (c.moveToNext()) {
            bills.add(billFromCursor(c));
        }
        c.close();
        return bills;
    }

    public synchronized List<Bill> getUnsettledBills(SQLiteDatabase db, int profile) {
        return getDueBills(db, Integer.MAX_VALUE, profile);
    }

    public synchronized List<Bill> getLateBills(SQLiteDatabase db, int profile) {
        return getDueBills(db, 0, profile);
    }

    private synchronized Bill billFromCursor(Cursor c) {
        Bill bill = new Bill();
        bill.setId(c.getInt(c.getColumnIndexOrThrow(BillDB.ID.getColumnName())));
        bill.setConsumption(c.getFloat(c
                .getColumnIndexOrThrow(BillDB.CONSUMPTION.getColumnName())));
        bill.setDueDate(c.getLong(c.getColumnIndexOrThrow(BillDB.DUE_DATE
                .getColumnName())));
        bill.setProfile(c.getInt(c.getColumnIndexOrThrow(BillDB.PROFILE
                .getColumnName())));
        bill.setSettled(c.getInt(c.getColumnIndexOrThrow(BillDB.PAID
                .getColumnName())) == 1);
        bill.setSum(c.getFloat(c.getColumnIndexOrThrow(BillDB.SUM
                .getColumnName())));
        bill.setType(c.getString(c.getColumnIndexOrThrow(BillDB.TYPE
                .getColumnName())));
        return bill;
    }
}
