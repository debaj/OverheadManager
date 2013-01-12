package com.debaj.overheadmanager.tasks;

import com.debaj.overheadmanager.AsyncCallback;
import com.debaj.overheadmanager.db.bean.Reading;
import com.debaj.overheadmanager.db.dao.ReadingDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
            result = (new ReadingDAO()
                    .insertReading(db, reading.getTime(), reading.getValue(),
                            reading.getKind(), reading.getProfile()) != -1) ? DbResult.SUCCESS
                    : DbResult.ERROR;
        }
        return result;
    }
}
