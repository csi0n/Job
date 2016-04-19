package com.csi0n.searchjob.controller;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.ui.activity.AppStart;
import com.csi0n.searchjob.utils.SharePreferenceManager;

/**
 * Created by chqss on 2016/4/19 0019.
 */
public class AppStartController extends BaseController {
    private AppStart mAppStart;
    private TimeTask mTimeTask;

    public AppStartController(AppStart appStart) {
        this.mAppStart = appStart;
    }

    public void initAppStart() {
        mTimeTask = new TimeTask();
    }

    @Override
    public void onClick(View view) {

    }

    private class TimeTask extends AsyncTask<Void, Void, Void> {
        public TimeTask() {
            this.execute(null, null, null, null, null);
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
            if (TextUtils.isEmpty(TOKEN))
                mAppStart.startMain(false);
            else
                mAppStart.startMain(true);
        }
    }
}
