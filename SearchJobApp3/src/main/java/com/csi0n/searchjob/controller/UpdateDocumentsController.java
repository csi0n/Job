package com.csi0n.searchjob.controller;

import android.view.View;

import com.csi0n.searchjob.AppContext;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.SystemUtils;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.model.ShowModel;
import com.csi0n.searchjob.model.event.ChoosePicHeadEvent;
import com.csi0n.searchjob.ui.activity.UpdateDocumentsActivity;
import com.csi0n.searchjob.utils.SharePreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

/**
 * Created by csi0n on 4/25/16.
 */
public class UpdateDocumentsController extends BaseController {
    private UpdateDocumentsActivity mUpdateDocumentsActivity;
    private AlertView mAlertView, mAlertViewChooseSex;

    private ShowModel showModel;
    public UpdateDocumentsController(UpdateDocumentsActivity mUpdateDocumentsActivity) {
        this.mUpdateDocumentsActivity = mUpdateDocumentsActivity;
    }

    public void initUpdateDocuments() {
        do_get_show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_line_head:
                mAlertView = new AlertView("上传头像", null, "取消", null,
                        new String[]{"拍照", "从相册中选择"},
                        mUpdateDocumentsActivity, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                EventBus.getDefault().post(new ChoosePicHeadEvent(true));
                                break;
                            case 1:
                                EventBus.getDefault().post(new ChoosePicHeadEvent(false));
                                break;
                        }
                    }
                });
                mAlertView.show();
                break;
            case R.id.rl_line_uname:
                mUpdateDocumentsActivity.startChangeUname();
                break;
            case R.id.rl_line_sex:
                mAlertViewChooseSex = new AlertView("请选择性别", null, "取消", null,
                        new String[]{"男", "女"},
                        mUpdateDocumentsActivity, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                break;
                        }
                    }
                });
                mAlertViewChooseSex.show();
                break;
            case R.id.rl_line_name:
            case R.id.rl_line_code:
                if (showModel!=null)
                mUpdateDocumentsActivity.startChangeNameAndCode(showModel);
                break;
            case R.id.rl_line_sign:
                mUpdateDocumentsActivity.startChangeSign();
                break;
            case R.id.btn_logout:
                SharePreferenceManager.setKeyCachedToken("");
                SystemUtils.restartApplication(AppContext.getApplicationContext);
                break;
            default:
                break;
        }
    }

    private void do_get_show() {
        PostParams params = getDefaultPostParams(R.string.url_user_show);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<ShowModel>(ShowModel.class) {
            @Override
            public void SuccessResult(ShowModel result) throws JSONException {
                showModel=result;
                mUpdateDocumentsActivity.initWidget(result);
            }
        });
        post.post();
    }
}
