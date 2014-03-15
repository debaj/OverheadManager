package com.debaj.overheadmanager;

import java.util.GregorianCalendar;

import com.debaj.overheadmanager.logic.asynctask.AsyncCallback;
import com.debaj.overheadmanager.logic.asynctask.DbResult;
import com.debaj.overheadmanager.logic.asynctask.InsertReadingTask;
import com.debaj.overheadmanager.logic.model.bean.Reading;
import com.debaj.overheadmanager.logic.util.CalendarUtil;
import com.debaj.overheadmanager.logic.util.StringUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MeterReadingActivity extends CommonActivity implements
        AsyncCallback {

    private AsyncTask<Void, Void, Object> insertReadingTask;
    private EditText value;
    private Spinner readingKindSpinner;
    private CheckBox pastDateCheck;
    private RelativeLayout pastDateLayout;
    private DatePicker pastDatePicker;
    private TextView pastDateText;
    private TextView pastDateError;

    @Override
    public void onAsyncTaskFinished(Object result) {
        if (result instanceof DbResult) {
            int message = R.string.message_general_operation_error;
            DbResult r = (DbResult) result;
            switch(r) {
                case SUCCESS:
                    message = R.string.message_general_operation_finished;
                    break;
                default:
                    break;
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void inflateView() {
        getLayoutInflater().inflate(R.layout.activity_meter_reading, content,
                true);
        setupView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_meter_reading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = false;
        switch (item.getItemId()) {
            case R.id.save:
                saveReading();
                handled = true;
                break;
            case R.id.clear:
                clear();
                handled = true;
                break;
        }
        return handled;
    }
    
    private void setupView() {
        value = (EditText) findViewById(R.id.reading_value);
        readingKindSpinner = (Spinner) findViewById(R.id.reading_kind);
        pastDateCheck = (CheckBox) findViewById(R.id.past_date_checkbox);
        pastDateLayout = (RelativeLayout) findViewById(R.id.previous_date_layout);
        pastDatePicker = (DatePicker) findViewById(R.id.previous_date);
        pastDateText = (TextView) findViewById(R.id.lbl_previous_date);
        pastDateError = (TextView) findViewById(R.id.error_future_date);
        
        pastDateCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pastDateLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);                
            }
        });
    }

    private void saveReading() {
        Reading reading = parseReading();
        if (reading != null) {
            insertReadingTask = new InsertReadingTask(this, this, reading)
                    .execute();
        }
    }

    private Reading parseReading() {
        Reading reading = null;
        if (checkFields()) {
            reading = new Reading();
            reading.setValue(Float.parseFloat(value.getText().toString()));
            // TODO reading.setKind(0);
            // TODO reading.setProfile(-1);
            if (pastDateCheck.isChecked()) {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.set(pastDatePicker.getYear(), pastDatePicker.getMonth(), pastDatePicker.getDayOfMonth());
                reading.setTime(calendar.getTimeInMillis());
            } else {
                reading.setTime(System.currentTimeMillis());
            }
        }
        return reading;
    }

    private boolean checkFields() {
        boolean isAllOk = true;
        if (!StringUtil.isSet(value.getText().toString())) {
            isAllOk = false;
            // TODO error icon
            value.setError(getString(R.string.message_error_field_is_mandatory));
        }
        
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(pastDatePicker.getYear(), pastDatePicker.getMonth(), pastDatePicker.getDayOfMonth());
        if (CalendarUtil.isFutureDate(calendar)) {
            isAllOk = false;
            pastDateText.setVisibility(View.INVISIBLE);
            pastDateError.setVisibility(View.VISIBLE);
        } else {
            pastDateText.setVisibility(View.VISIBLE);
            pastDateError.setVisibility(View.INVISIBLE);
        }
        return isAllOk;
    }

    private void clear() {
        
    }

}
