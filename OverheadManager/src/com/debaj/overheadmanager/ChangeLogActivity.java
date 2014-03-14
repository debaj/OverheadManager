package com.debaj.overheadmanager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;

import com.debaj.overheadmanager.R;
import com.debaj.overheadmanager.viewhelper.ChangeLogClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ChangeLogActivity extends Activity {

    WebView changeLogView;
    WebViewClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changelog);
        new ChangeLogTask(this).execute();
    }

    public void close(View v) {
        finish();
    }

    private void showChangeLog(String data) {
        changeLogView = (WebView) findViewById(R.id.changelog);
        client = new ChangeLogClient(this);
        changeLogView.setWebViewClient(client);
        changeLogView.loadData(data, "text/html; charset=UTF-8", "UTF8");
    }

    private class ChangeLogTask extends AsyncTask<Void, Void, Boolean> {

        private ProgressDialog dialog;
        private String changelog;

        public ChangeLogTask(Activity activity) {
            dialog = new ProgressDialog(activity);
            changelog = "";
        }

        @Override
        protected void onPreExecute() {
            dialog.setCancelable(false);
            dialog.setMessage(getString(R.string.act_changelog_loading));
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            try {
                InputStream is = getResources().openRawResource(R.raw.cl);
                InputStreamReader isr = new InputStreamReader(is, "UTF8");
                BufferedReader reader = new BufferedReader(isr);
                Writer writer = new StringWriter();

                int n = 0;
                char[] buffer = new char[1024];

                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
                
                changelog = writer.toString();
                
                return true;
            } catch (Exception e) {
                Log.w("Changelog", e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            dialog.dismiss();
            if (!result || "".equals(changelog)) {
                finish();
            } else {
                showChangeLog(changelog);
            }
        }
    }
}
