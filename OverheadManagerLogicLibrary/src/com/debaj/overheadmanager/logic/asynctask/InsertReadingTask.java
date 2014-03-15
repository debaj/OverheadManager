package com.debaj.overheadmanager.logic.asynctask;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.debaj.overheadmanager.logic.db.dao.ReadingDAO;
import com.debaj.overheadmanager.logic.model.bean.Reading;

public class InsertReadingTask extends AbstractDBTask {

    private Reading reading;

    public InsertReadingTask(Context context, AsyncCallback callback,
            Reading reading) {
        super(context, callback, true);
        this.reading = reading;
    }

    @Override
    protected DbResult databaseOperation(SQLiteDatabase db) {
        DbResult result = DbResult.ERROR;
        if (reading != null) {
            result = (new ReadingDAO().insertReading(db, reading.getTime(),
                    reading.getValue(), reading.getKind(),
                    reading.getProfile()) != -1) ? DbResult.SUCCESS
                    : DbResult.ERROR;
        }
        return result;
    }
}