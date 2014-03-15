package com.debaj.overheadmanager.logic.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.debaj.overheadmanager.logic.db.Statements;
import com.debaj.overheadmanager.logic.db.Tables;
import com.debaj.overheadmanager.logic.model.bean.ReadingKind;

public class ReadingKindDAO {
    public static enum ReadingKindDB {
        ID("_id"), NAME("name"), UNIT("unit"), PRICE("price"), DISCOUNT_PRICE(
                "discount_price"), ENABLED("enabled");

        private String columnName;

        private ReadingKindDB(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return this.columnName;
        }
    }

    public synchronized long insertReadingKind(SQLiteDatabase db, String name,
            String unit, float price, float discountPrice) {
        ContentValues cv = new ContentValues();
        cv.put(ReadingKindDB.NAME.getColumnName(), name);
        cv.put(ReadingKindDB.UNIT.getColumnName(), unit);
        cv.put(ReadingKindDB.PRICE.getColumnName(), price);
        cv.put(ReadingKindDB.DISCOUNT_PRICE.getColumnName(), discountPrice);
        cv.put(ReadingKindDB.ENABLED.getColumnName(), true);
        return db.insert(Tables.READING_KINDS.getTableName(), null, cv);
    }
    
    public synchronized void updateReadingKind(SQLiteDatabase db, int id, String name,
            String unit, float price, float discountPrice, boolean enabled) {
        SQLiteStatement stm = db.compileStatement(Statements.DB_READING_KINDS_UPDATE_READING_KIND.getStatement());
        stm.bindString(1, name);
        stm.bindString(2, unit);
        stm.bindDouble(3, price);
        stm.bindDouble(4, discountPrice);
        stm.bindLong(5, enabled ? 1 : 0);
        stm.bindLong(6, id);
        stm.execute();
    }
    
    public synchronized void deleteReadingKind(SQLiteDatabase db, int id) {
        SQLiteStatement stm = db.compileStatement(Statements.DB_READING_KINDS_DELETE_READING_KIND.getStatement());
        stm.bindLong(1, id);
        stm.execute();
    }
    
    public synchronized ReadingKind getReadingKind(SQLiteDatabase db, int id) {
        String[] args = { Integer.toString(id) };
        ReadingKind readingKind = null;
        Cursor c = db.rawQuery(Statements.DB_READING_KINDS_GET_READING_KIND.getStatement(), args);
        if (c.moveToFirst()) {
            readingKind = readingKindFromCursor(c);
        }
        c.close();
        return readingKind;
    }

    private synchronized ReadingKind readingKindFromCursor(Cursor c) {
        ReadingKind readingKind;
        readingKind = new ReadingKind();
        readingKind.setId(c.getInt(c.getColumnIndexOrThrow(ReadingKindDB.ID
                .getColumnName())));
        readingKind.setName(c.getString(c
                .getColumnIndexOrThrow(ReadingKindDB.NAME.getColumnName())));
        readingKind.setUnit(c.getString(c
                .getColumnIndexOrThrow(ReadingKindDB.UNIT.getColumnName())));
        readingKind.setPrice(c.getFloat(c
                .getColumnIndexOrThrow(ReadingKindDB.PRICE.getColumnName())));
        readingKind.setDiscountPrice(c.getFloat(c
                .getColumnIndexOrThrow(ReadingKindDB.DISCOUNT_PRICE
                        .getColumnName())));
        readingKind.setEnabled(c.getInt(c
                .getColumnIndexOrThrow(ReadingKindDB.ENABLED
                        .getColumnName())) == 1);
        return readingKind;
    }
}
