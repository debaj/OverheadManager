package com.debaj.overheadmanager.tasks;

import com.debaj.overheadmanager.AsyncCallback;
import com.debaj.overheadmanager.db.bean.ReadingKind;
import com.debaj.overheadmanager.db.dao.ReadingKindDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class InsertReadingKindTask extends AbstractDBTask {

    private ReadingKind readingKind;

    public InsertReadingKindTask(Context context, AsyncCallback callback,
            ReadingKind readingKind) {
        super(context, callback, true);
        this.readingKind = readingKind;
    }

    @Override
    protected DbResult databaseOperation(SQLiteDatabase db) {
        DbResult result = DbResult.ERROR;
        if (readingKind != null) {
            result = (new ReadingKindDAO().insertReadingKind(db,
                    readingKind.getName(), readingKind.getUnit(),
                    readingKind.getPrice(), readingKind.getDiscountPrice()) != -1) ? DbResult.SUCCESS
                    : DbResult.ERROR;
        }
        return result;
    }
}