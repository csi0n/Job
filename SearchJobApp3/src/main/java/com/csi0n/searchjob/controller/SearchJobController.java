package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.widget.ProgressLoading;
import com.csi0n.searchjob.model.ConfigModel;
import com.csi0n.searchjob.ui.fragment.SearchJobFragment;
import com.csi0n.searchjob.utils.DownLoadConfig;
import com.csi0n.searchjob.utils.SharePreferenceManager;

import org.json.JSONException;

/**
 * Created by chqss on 2016/4/20 0020.
 */
public class SearchJobController extends BaseController {
    private SearchJobFragment mSearchJobFragment;
    private ProgressLoading loading;

    public SearchJobController(SearchJobFragment mSearchJobFragment) {
        this.mSearchJobFragment = mSearchJobFragment;
    }

    public void initSearchJob() {
        if (SharePreferenceManager.getFlagIsFirstStartSearchJobFragment()) {
            loading = new ProgressLoading(mSearchJobFragment.aty, "正在下载配置文件...");
            loading.show();
            DownLoadConfig downLoadConfig = new DownLoadConfig(new ObjectHttpCallBack<ConfigModel>(ConfigModel.class) {
                @Override
                public void SuccessResult(ConfigModel result) throws JSONException {
                }

                @Override
                public void onFinished() {
                    loading.dismiss();
                    super.onFinished();
                }
            });
            downLoadConfig.download();
        } else {

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:

                break;
            default:
                break;
        }
    }
}
