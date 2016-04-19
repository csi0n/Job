package com.csi0n.searchjob.enterpriseapp.controller;

import android.support.v7.internal.widget.ViewUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.csi0n.searchjob.enterpriseapp.Config;
import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.ui.activity.SendActivity;
import com.csi0n.searchjob.enterpriseapp.ui.widget.ChooseFuliDialog;
import com.csi0n.searchjob.enterpriseapp.utils.bean.EmptyBean;
import com.csi0n.searchjob.enterpriseapp.utils.bean.JobDetailBean;
import com.csi0n.searchjob.enterpriseapp.utils.bean.JobListBean;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.StringUtils;
import com.csi0n.searchjob.lib.utils.bean.FuliBean;
import com.csi0n.searchjob.lib.utils.bean.JobTypeBean;
import com.csi0n.searchjob.lib.widget.ChooseWheelDialog;
import com.csi0n.searchjob.lib.widget.ProgressLoading;

import org.json.JSONException;
import org.w3c.dom.Text;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csi0n on 3/27/16.
 */
public class SendController extends BaseController implements RadioGroup.OnCheckedChangeListener, CheckBox.OnCheckedChangeListener {
    private SendActivity mSendActivity;
    private DbManager db = x.getDb(Config.dbConfig);
    private String TEMP_JOB_TYPE;
    private String TEMP_FULI_ID;
    private String TEMP_FULI_STRING;
    private int TEMP_SHEBAO = 1;
    private int TEMP_SEX = 3;
    private ProgressLoading loading;
    private JobDetailBean TEMP_JOB_TYPE_DETAIL;

    public SendController(SendActivity sendActivity) {
        this.mSendActivity = sendActivity;
    }

    public void initSend() {
        if (mSendActivity.isFromNormal())
            loadMoneyBack();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choose_job_type:
                try {
                    final List<JobTypeBean> jobTypeBeanList = db.selector(JobTypeBean.class).findAll();
                    ArrayList<String> strList = new ArrayList<>();
                    for (JobTypeBean jobType : jobTypeBeanList) {
                        strList.add(jobType.getName());
                    }
                    new ChooseWheelDialog(mSendActivity, "请选择工作类型", strList, new ChooseWheelDialog.OnClickSubmit() {
                        @Override
                        public void text(int position, String txt) {
                            mSendActivity.setJobType(jobTypeBeanList.get(position).getName());
                            TEMP_JOB_TYPE = String.valueOf(jobTypeBeanList.get(position).getId());
                        }
                    }).show();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_choose_fuli:
                new ChooseFuliDialog(mSendActivity, new ChooseFuliDialog.onSubmit() {
                    @Override
                    public void onSub(List<FuliBean> fuliBeenList) {
                        int i = 0;
                        for (FuliBean fuliBean : fuliBeenList) {
                            if (fuliBean.isCheck()) {
                                if (i == 0) {
                                    TEMP_FULI_ID = String.valueOf(fuliBean.getId());
                                    TEMP_FULI_STRING = fuliBean.getName();
                                } else {
                                    TEMP_FULI_ID += "," + fuliBean.getId();
                                    TEMP_FULI_STRING += "," + fuliBean.getName();
                                }
                                i++;
                            }

                        }
                        mSendActivity.setFuli(TEMP_FULI_STRING);
                    }
                }).show();
                break;
            case R.id.tv_more:
                if (TextUtils.isEmpty(TEMP_JOB_TYPE))
                    mSendActivity.setEditJobTypeError(getString(R.string.str_please_choose_job_type));
                else if (TextUtils.isEmpty(mSendActivity.getEditGongzi1()))
                    mSendActivity.setEditGongzi1Error("请输入最低工资!");
                else if (TextUtils.isEmpty(mSendActivity.getEditGongzi2()))
                    mSendActivity.setEditGongzi2Error("请输入最高工资!");
                else if (TextUtils.isEmpty(mSendActivity.getEditGongziDetail()))
                    mSendActivity.setEditGongziDetailError("请输入工资详情");
                else if (TextUtils.isEmpty(mSendActivity.getmEditWorkTime()))
                    mSendActivity.setEditWorkTimeError("请输入工作时长!");
                else if (TextUtils.isEmpty(mSendActivity.getEditWorkLife()))
                    mSendActivity.setEditWorkLifeError("请设置工作经验/年限");
                else if (TextUtils.isEmpty(mSendActivity.getDegreeWanted()))
                    mSendActivity.setDegreeWantedError("请设置教育程度");
                else if (TextUtils.isEmpty(mSendActivity.getEditMoreinfo()))
                    mSendActivity.setEditMoreinfoError("请填写更多信息");
                else if (TextUtils.isEmpty(mSendActivity.getEditWorkinfo()))
                    mSendActivity.setmWorkinfoError("请填写工作内容");
                else if (TextUtils.isEmpty(mSendActivity.getEditWorklocation()))
                    mSendActivity.setEditWorklocationError("请填写工作地址!");
                else if (TextUtils.isEmpty(mSendActivity.getEditUsertype()))
                    mSendActivity.setEditUsertypeError("请填写用工类型!");
                else {
                    if (mSendActivity.isFromNormal()) {
                        if (TEMP_JOB_TYPE_DETAIL != null)
                            do_save(TEMP_JOB_TYPE, mSendActivity.getEditGongzi1(), mSendActivity.getEditGongzi2(), mSendActivity.getEditGongziDetail(), mSendActivity.getmEditWorkTime()
                                    , TEMP_FULI_ID, String.valueOf(TEMP_SEX), mSendActivity.getEditAge1(), mSendActivity.getEditAge2(), mSendActivity.getEditWorkLife(), mSendActivity.getDegreeWanted()
                                    , mSendActivity.getEditMoreinfo(), mSendActivity.getEditWorkinfo(), mSendActivity.getEditWorklocation(), String.valueOf(TEMP_SHEBAO), mSendActivity.getEditUsertype());
                        else
                            CLog.show("请等待获取信息线程完成工作!");
                    } else {
                        do_insert(TEMP_JOB_TYPE, mSendActivity.getEditGongzi1(), mSendActivity.getEditGongzi2(), mSendActivity.getEditGongziDetail(), mSendActivity.getmEditWorkTime()
                                , TEMP_FULI_ID, String.valueOf(TEMP_SEX), mSendActivity.getEditAge1(), mSendActivity.getEditAge2(), mSendActivity.getEditWorkLife(), mSendActivity.getDegreeWanted()
                                , mSendActivity.getEditMoreinfo(), mSendActivity.getEditWorkinfo(), mSendActivity.getEditWorklocation(), String.valueOf(TEMP_SHEBAO), mSendActivity.getEditUsertype());
                    }
                }
                break;
            default:
                break;
        }
    }

    private void loadMoneyBack() {
        loading = new ProgressLoading(mSendActivity, "获取信息中...");
        loading.show();
        PostParams params = getDefaultPostParams(R.string.url_loadMoneyBack);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<JobDetailBean>(JobDetailBean.class) {
            @Override
            public void SuccessResult(JobDetailBean result) throws JSONException {
                try {
                    JobTypeBean job_type = db.selector(JobTypeBean.class).where("id", "=", result.getJob_type()).findFirst();
                    TEMP_JOB_TYPE = String.valueOf(job_type.getId());
                    mSendActivity.setJobType(job_type.getName());
                    String[] gongzi = result.getGongzi().split("~");
                    if (gongzi.length == 2) {
                        mSendActivity.setEditGongzi1(gongzi[0]);
                        mSendActivity.setEditGongzi2(gongzi[1]);
                    }
                    mSendActivity.setEditGongziDetail(result.getGongzi_detail());
                    mSendActivity.setEditWorkTime(result.getWork_time());
                    switch (result.getSex()) {
                        case "3":
                            mSendActivity.setRb1Check(true);
                            break;
                        case "1":
                            mSendActivity.setRb2Check(true);
                            break;
                        case "2":
                            mSendActivity.setRb3Check(true);
                            break;
                    }
                    String[] age = result.getAge().split("~");
                    if (age.length == 2) {
                        mSendActivity.setEditAge1(age[0]);
                        mSendActivity.setEditAge2(age[1]);
                    }
                    mSendActivity.setEditWorkLife(result.getWork_life());
                    mSendActivity.setDegreeWanted(result.getDegree_wanted());
                    mSendActivity.setEditMoreInfo(result.getMore_infor());
                    mSendActivity.setEditWorkInfo(result.getWork_infor());
                    mSendActivity.setEditWorklocation(result.getWork_location());
                    mSendActivity.setChkSheBao(true);
                    mSendActivity.setEditUserType(result.getUse_type());
                    String[] fuli = result.getFuli().split(",");
                    int i = 0;
                    for (String f : fuli) {
                        FuliBean fu = db.selector(FuliBean.class).where("id", "=", f).findFirst();
                        if (i == 0) {
                            TEMP_FULI_ID = String.valueOf(fu.getId());
                            TEMP_FULI_STRING = fu.getName();
                        } else {
                            TEMP_FULI_ID += "," + fu.getId();
                            TEMP_FULI_STRING += "," + fu.getName();
                        }
                        i++;
                    }
                    mSendActivity.setFuli(TEMP_FULI_STRING);
                    TEMP_JOB_TYPE_DETAIL = result;
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinished() {
                super.onFinished();
                if (loading.isShowing())
                    loading.dismiss();
                if (TEMP_JOB_TYPE_DETAIL == null)
                    TEMP_JOB_TYPE_DETAIL = new JobDetailBean();
            }
        });
        post.post();
    }

    private void do_save(String job_type, String gongzi1, String gongzi2, String gongzi_detail, String work_time, String fuli, String sex, String age1,
                         String age2, String work_life, String degreewanted, String more_info, String work_info, String work_location, String shebao, String use_type) {
        loading = new ProgressLoading(mSendActivity, "发布中...");
        loading.show();
        PostParams params = getDefaultPostParams(R.string.url_saveMoneyBack);
        params.put("job_type", job_type);
        params.put("gongzi", gongzi1 + "~" + gongzi2);
        params.put("gongzi_detail", gongzi_detail);
        params.put("work_time", work_time);
        params.put("fuli", fuli);
        params.put("sex", sex);
        if (!TextUtils.isEmpty(age1) && !TextUtils.isEmpty(age2))
            params.put("age", age1 + "~" + age2);
        params.put("work_life", work_life);
        params.put("degree_wanted", degreewanted);
        params.put("more_infor", more_info);
        params.put("work_infor", work_info);
        params.put("work_location", work_location);
        params.put("shebao", shebao);
        params.put("use_type", use_type);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                CLog.show("发布成功!");
            }

            @Override
            public void onFinished() {
                super.onFinished();
                if (loading.isShowing())
                    loading.dismiss();
            }
        });
        post.post();
    }

    private void do_insert(String job_type, String gongzi1, String gongzi2, String gongzi_detail, String work_time, String fuli, String sex,
                           String age1, String age2, String work_life, String degreewanted, String more_info, String work_info, String work_location, String shebao, String use_type) {
        loading = new ProgressLoading(mSendActivity, "发布中...");
        loading.show();
        PostParams params = getDefaultPostParams(R.string.url_insertJob);
        params.put("job_type", job_type);
        params.put("gongzi", gongzi1 + "~" + gongzi2);
        params.put("gongzi_detail", gongzi_detail);
        params.put("work_time", work_time);
        params.put("fuli", fuli);
        params.put("sex", sex);
        if (!TextUtils.isEmpty(age1) && !TextUtils.isEmpty(age2))
            params.put("age", age1 + "~" + age2);
        params.put("work_life", work_life);
        params.put("degree_wanted", degreewanted);
        params.put("more_infor", more_info);
        params.put("work_infor", work_info);
        params.put("work_location", work_location);
        params.put("shebao", shebao);
        params.put("use_type", use_type);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                CLog.show("发布成功!");
            }
            @Override
            public void onFinished() {
                super.onFinished();
                if (loading.isShowing())
                    loading.dismiss();
            }
        });
        post.post();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_1:
                TEMP_SEX = 3;
                break;
            case R.id.rb_2:
                TEMP_SEX = 1;
                break;
            case R.id.rb_3:
                TEMP_SEX = 2;
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b)
            TEMP_SHEBAO = 1;
        else
            TEMP_SHEBAO = 0;
    }
}
