package com.debaj.overheadmanager.tasks;

import com.debaj.overheadmanager.logic.asynctask.AsyncCallback;
import com.debaj.overheadmanager.logic.model.bean.Bill;
import com.debaj.overheadmanager.logic.model.bean.BillDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class InsertBillTask extends AbstractDBTask {

    private Bill bill;

    public InsertBillTask(Context context, AsyncCallback callback, Bill bill) {
        super(context, callback, true);
        this.bill = bill;
    }

    @Override
    protected DbResult databaseOperation(SQLiteDatabase db) {
        DbResult result = DbResult.ERROR;
        if (bill != null) {
            result = (new BillDAO().insertBill(db, bill.getDueDate(),
                    bill.getSum(), bill.getConsumption(), bill.getType(),
                    bill.getProfile()) != -1) ? DbResult.SUCCESS
                    : DbResult.ERROR;
        }
        return result;
    }
}
