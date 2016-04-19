package com.csi0n.searchjob.enterpriseapp.controller;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.enterpriseapp.Config;
import com.csi0n.searchjob.enterpriseapp.ui.activity.AppStart;
import com.csi0n.searchjob.enterpriseapp.utils.SharePreferenceManager;
import com.csi0n.searchjob.lib.controller.BaseController;

/**
 * Created by chqss on 2016/3/25 0025.
 */
public class AppStartController extends BaseController {
    private AppStart mAppStart;
    private TimeTask timeTask;

    public AppStartController(AppStart appStart) {
        this.mAppStart = appStart;
    }

    public void initAppStart() {
        timeTask = new TimeTask();
        timeTask.execute(null, null, null, null, null);
    }

    private class TimeTask extends AsyncTask<Void, Void, Void> {
        public TimeTask() {
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(Config.APP_START_TIME_DELAY);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String TOKEN = SharePreferenceManager.getKeyCachedToken();
            if (TextUtils.isEmpty(TOKEN)) {
                mAppStart.startLogin();
            } else {
                mAppStart.startMain();
            }
        }

    }
    @Override
    public void onClick(View view) {
    }
}
