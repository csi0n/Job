package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.model.CompanyWorkDetailAModel;
import com.csi0n.searchjob.ui.activity.ShowJobTypeDetailActivity;

import org.json.JSONException;
import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by chqss on 2016/4/22 0022.
 */
public class ShowJobTypeDetailController extends BaseController {
    private ShowJobTypeDetailActivity mShowJobTypeDetailActivity;
    private DbManager db = x.getDb(Config.dbConfig);

    public ShowJobTypeDetailController(ShowJobTypeDetailActivity mShowJobTypeDetailActivity) {
        this.mShowJobTypeDetailActivity = mShowJobTypeDetailActivity;
    }

    public void initShowJobType() {
        do_get_job(mShowJobTypeDetailActivity.getJobId());
    }

    private void do_get_job(String job_id) {
        PostParams params = getDefaultPostParams(R.string.url_searchJobDetailA);
        params.put("job_id", job_id);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<CompanyWorkDetailAModel>(CompanyWorkDetailAModel.class) {
            @Override
            public void SuccessResult(CompanyWorkDetailAModel result) throws JSONException {
                mShowJobTypeDetailActivity.setCompanyInforTop(db, result);
            }
        });
        post.post();
    }

    @Override
    public void onClick(View view) {

    }
}
