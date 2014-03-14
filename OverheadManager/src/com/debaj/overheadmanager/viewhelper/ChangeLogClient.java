package com.debaj.overheadmanager.viewhelper;

import android.app.Activity;
import android.content.Intent;
import android.net.MailTo;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ChangeLogClient extends WebViewClient {
    Activity mContext;

    public ChangeLogClient(Activity context) {
        this.mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("mailto:")) {
            MailTo mt = MailTo.parse(url);
            Intent i = new Intent(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_EMAIL, new String[] { mt.getTo() });
            i.putExtra(Intent.EXTRA_SUBJECT, mt.getSubject());
            i.setType("text/plain");
            mContext.startActivity(i);
            view.reload();
            return true;
        }
        view.loadUrl(url);
        return true;
    }
}