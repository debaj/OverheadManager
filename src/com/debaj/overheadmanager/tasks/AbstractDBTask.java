package com.debaj.overheadmanager.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.debaj.overheadmanager.AsyncCallback;
import com.debaj.overheadmanager.R;
import com.debaj.overheadmanager.db.DatabaseManager;

public abstract class AbstractDBTask extends AsyncTask<Void, Void, Object> {

    protected Context context;
    protected AsyncCallback callback;
    protected boolean writable;
    protected int messageResource;
    
    private ProgressDialog progress;
    
    public AbstractDBTask(Context context, AsyncCallback callback, boolean writable) {
        this.context = context;
        this.callback = callback;
        this.writable = writable;
        this.messageResource = R.string.message_general_please_wait;
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(context);
        progress.setMessage(context.getString(messageResource));
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    protected Object doInBackground(Void... arg0) {
        SQLiteDatabase db;
        if (writable) {
            db = DatabaseManager.getInstance(context).getWritableDatabase();
        } else {
            db = DatabaseManager.getInstance(context).getReadableDatabase();
        }
        Object dbResult = databaseOperation(db);
        db.close();
        return dbResult;
    }
    
    @Override
    protected void onPostExecute(Object result) {
        progress.dismiss();
        callback.onAsyncTaskFinished(result);
        super.onPostExecute(result);
    }
    protected abstract Object databaseOperation(SQLiteDatabase db);
}
