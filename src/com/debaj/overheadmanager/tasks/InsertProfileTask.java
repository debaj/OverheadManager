package com.debaj.overheadmanager.tasks;

import com.debaj.overheadmanager.AsyncCallback;
import com.debaj.overheadmanager.db.bean.Profile;
import com.debaj.overheadmanager.db.dao.ProfileDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class InsertProfileTask extends AbstractDBTask {

    private Profile profile;

    public InsertProfileTask(Context context, AsyncCallback callback,
            Profile profile) {
        super(context, callback, true);
        this.profile = profile;
    }

    @Override
    protected DbResult databaseOperation(SQLiteDatabase db) {
        DbResult result = DbResult.ERROR;
        if (profile != null) {
            result = (new ProfileDAO().insertProfile(db, profile.getName(),
                    profile.getAddress(), profile.getKind()) != -1) ? DbResult.SUCCESS
                    : DbResult.ERROR;
        }
        return result;
    }
}