package com.debaj.overheadmanager.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.debaj.overheadmanager.db.bean.Profile;
import com.debaj.overheadmanager.db.statements.Statements;
import com.debaj.overheadmanager.db.statements.Tables;

public class ProfileDAO {
    public static enum ProfileDB {
        ID("_id"), NAME("name"), ADDRESS("address"), KIND("kind");

        private String columnName;

        private ProfileDB(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return this.columnName;
        }
    }

    public synchronized long insertProfile(SQLiteDatabase db, String name,
            String address, String kind) {
        ContentValues cv = new ContentValues();
        cv.put(ProfileDB.NAME.getColumnName(), name);
        cv.put(ProfileDB.ADDRESS.getColumnName(), address);
        cv.put(ProfileDB.KIND.getColumnName(), kind);
        return db.insert(Tables.PROFILES.getTableName(), null, cv);
    }
    
    public synchronized void updateProfile(SQLiteDatabase db, int id, String name,
            String address, String kind) {
        SQLiteStatement stm = db.compileStatement(Statements.DB_PROFILES_UPDATE_PROFILES.getStatement());
        stm.bindString(1, name);
        stm.bindString(2, address);
        stm.bindString(3, kind);
        stm.bindLong(4, id);
        stm.execute();
    }

    public synchronized void deleteProfile(SQLiteDatabase db, int id) {
        SQLiteStatement stm = db
                .compileStatement(Statements.DB_PROFILES_DELETE_PROFILE
                        .getStatement());
        stm.bindLong(1, id);
        stm.execute();
    }

    public synchronized Profile getProfile(SQLiteDatabase db, int id) {
        Profile profile = null;
        String[] args = { Integer.toString(id) };
        Cursor c = db.rawQuery(
                Statements.DB_PROFILES_GET_PROFILE.getStatement(), args);
        if (c.moveToFirst()) {
            profile = profileFromCursor(c);
        }
        c.close();
        return profile;
    }

    public synchronized Profile getFirstProfile(SQLiteDatabase db) {
        Profile profile = null;
        Cursor c = db.rawQuery(
                Statements.DB_PROFILES_GET_ALL_PROFILES.getStatement(),
                new String[0]);
        if (c.moveToFirst()) {
            profile = profileFromCursor(c);
        }
        c.close();
        return profile;
    }

    public synchronized List<Profile> getAllProfiles(SQLiteDatabase db) {
        List<Profile> profiles = new ArrayList<Profile>();
        Cursor c = db.rawQuery(
                Statements.DB_PROFILES_GET_ALL_PROFILES.getStatement(),
                new String[0]);
        c.moveToFirst();
        while (c.moveToNext()) {
            profiles.add(profileFromCursor(c));
        }
        c.close();
        return profiles;
    }

    private synchronized Profile profileFromCursor(Cursor c) {
        Profile profile = new Profile();
        profile.setId(c.getInt(c.getColumnIndexOrThrow(ProfileDB.ID
                .getColumnName())));
        profile.setName(c.getString(c.getColumnIndexOrThrow(ProfileDB.NAME
                .getColumnName())));
        profile.setAddress(c.getString(c
                .getColumnIndexOrThrow(ProfileDB.ADDRESS.getColumnName())));
        profile.setKind(c.getString(c.getColumnIndexOrThrow(ProfileDB.KIND
                .getColumnName())));
        return profile;
    }
}
