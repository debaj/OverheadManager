package com.debaj.overheadmanager.logic.db.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.debaj.overheadmanager.common.CalendarHelper;
import com.debaj.overheadmanager.db.bean.Reading;
import com.debaj.overheadmanager.logic.db.Statements;
import com.debaj.overheadmanager.logic.db.Tables;

public class ReadingDAO {
    public static enum ReadingDB {
        ID("_id"), TIME("time"), VALUE("value"), KIND("kind"), PROFILE(
                "profile");

        private String columnName;

        private ReadingDB(String columnName) {
            this.columnName = columnName;
        }

        public synchronized String getColumnName() {
            return this.columnName;
        }
    }

    public synchronized long insertReading(SQLiteDatabase db, long time,
            float value, int kind, int profile) {
        ContentValues cv = new ContentValues();
        cv.put(ReadingDB.TIME.getColumnName(), time);
        cv.put(ReadingDB.VALUE.getColumnName(), value);
        cv.put(ReadingDB.KIND.getColumnName(), kind);
        cv.put(ReadingDB.PROFILE.getColumnName(), profile);
        return db.insert(Tables.READINGS.getTableName(), null, cv);
    }

    public synchronized void updateReading(SQLiteDatabase db, int id,
            float newValue) {
        SQLiteStatement stm = db
                .compileStatement(Statements.DB_READINGS_UPDATE_READING
                        .getStatement());
        stm.bindDouble(1, newValue);
        stm.bindLong(2, id);
        stm.execute();
    }

    public synchronized void deleteReading(SQLiteDatabase db, int id) {
        SQLiteStatement stm = db
                .compileStatement(Statements.DB_READINGS_DELETE_READING
                        .getStatement());
        stm.bindLong(1, id);
        stm.execute();
    }

    public synchronized List<Reading> getReadings(SQLiteDatabase db, long from,
            long to, int kind, int profile) {
        List<Reading> readings = new ArrayList<Reading>();
        String[] args = { Long.toString(from), Long.toString(to),
                Integer.toString(kind), Integer.toString(profile) };
        Cursor c = db.rawQuery(
                Statements.DB_READINGS_GET_READINGS.getStatement(), args);
        c.moveToFirst();
        while (c.moveToNext()) {
            readings.add(readingFromCursor(c));
        }
        c.close();
        return readings;
    }

    public synchronized List<Reading> getAllReadings(SQLiteDatabase db,
            int kind, int profile) {
        return getReadings(db, 0, System.currentTimeMillis(), kind, profile);
    }

    public synchronized List<Reading> getReadingsForThisWeek(SQLiteDatabase db,
            int kind, int profile) {
        Calendar cal = CalendarUtil.getFirstDayOfWeek();
        return getReadings(db, cal.getTimeInMillis(),
                System.currentTimeMillis(), kind, profile);
    }

    public synchronized List<Reading> getReadingsForThisMonth(
            SQLiteDatabase db, int kind, int profile) {
        Calendar cal = CalendarUtil.getFirstDayOfMonth();
        return getReadings(db, cal.getTimeInMillis(),
                System.currentTimeMillis(), kind, profile);
    }

    public synchronized List<Reading> getReadingsForThisYear(SQLiteDatabase db,
            int kind, int profile) {
        Calendar cal = CalendarUtil.getFirstDayOfYear();
        return getReadings(db, cal.getTimeInMillis(),
                System.currentTimeMillis(), kind, profile);
    }

    public synchronized List<Reading> getReadingsOfTheLastMonths(
            SQLiteDatabase db, int months, int kind, int profile) {
        Calendar cal = CalendarUtil.getFirstDayOfMonth();
        cal.add(Calendar.MONTH, months * -1);
        return getReadings(db, cal.getTimeInMillis(),
                System.currentTimeMillis(), kind, profile);
    }

    public synchronized float getReadingValue(SQLiteDatabase db, int id) {
        float reading = -1.0f;
        String[] args = { Integer.toString(id) };
        Cursor c = db.rawQuery(
                Statements.DB_READINGS_GET_READING_VALUE.getStatement(), args);
        if (c.moveToFirst()) {
            reading = c.getFloat(c.getColumnIndexOrThrow(ReadingDB.VALUE
                    .getColumnName()));
        }
        c.close();
        return reading;
    }

    public synchronized float getLastReading(SQLiteDatabase db, int kind,
            int profile) {
        float reading = -1.0f;
        String[] args = { Integer.toString(kind), Integer.toString(profile) };
        Cursor c = db.rawQuery(
                Statements.DB_READINGS_GET_LAST_READING.getStatement(), args);
        if (c.moveToFirst()) {
            reading = c.getFloat(c.getColumnIndexOrThrow(ReadingDB.VALUE
                    .getColumnName()));
        }
        c.close();
        return reading;
    }

    private synchronized Reading readingFromCursor(Cursor c) {
        Reading reading = new Reading();
        reading.setId(c.getInt(c.getColumnIndexOrThrow(ReadingDB.ID
                .getColumnName())));
        reading.setKind(c.getInt(c.getColumnIndexOrThrow(ReadingDB.KIND
                .getColumnName())));
        reading.setProfile(c.getInt(c.getColumnIndexOrThrow(ReadingDB.PROFILE
                .getColumnName())));
        reading.setTime(c.getLong(c.getColumnIndexOrThrow(ReadingDB.TIME
                .getColumnName())));
        reading.setValue(c.getFloat(c.getColumnIndexOrThrow(ReadingDB.VALUE
                .getColumnName())));
        return reading;
    }
}
