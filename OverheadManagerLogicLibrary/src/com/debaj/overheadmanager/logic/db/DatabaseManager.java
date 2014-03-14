package com.debaj.overheadmanager.logic.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import com.debaj.overheadmanager.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "overhead.db";
    private static final String STATEMENT_FILE_STUB = "db_v%d";
    private static final int DATABASE_VERSION = 8;

    public static DatabaseManager INSTANCE;
    private static Context context;

    private DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DatabaseManager.context = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        buildDatabase(db, 1, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        buildDatabase(db, oldVersion + 1, newVersion);
    }

    public static DatabaseManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseManager(context);
        }
        return INSTANCE;
    }

    private synchronized void buildDatabase(SQLiteDatabase db, int from, int to) {
        for (int i = from; i <= to; i++) {
            int resId = getResId(i);
            if (resId != -1) {
                try {
                    runStatements(db, getResId(i));
                } catch (IOException e) {
                }
            }
        }
    }

    private synchronized void runStatements(SQLiteDatabase db, int resId)
            throws IOException {
        InputStream statements = context.getResources().openRawResource(resId);
        BufferedReader statementReader = new BufferedReader(
                new InputStreamReader(statements));

        while (statementReader.ready()) {
            String insertStmt = statementReader.readLine();
            db.execSQL(insertStmt);
        }

        statementReader.close();
    }

    private int getResId(int version) {
        try {
            Field idField = R.raw.class.getDeclaredField(String.format(
                    STATEMENT_FILE_STUB, version));
            return idField.getInt(idField);
        } catch (Exception e) {
            return -1;
        }
    }
}
