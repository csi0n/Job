package com.csi0n.searchjob.enterpriseapp.controller;

import android.os.AsyncTask;
import android.view.View;

import com.csi0n.searchjob.enterpriseapp.Config;
import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.ui.activity.Main;
import com.csi0n.searchjob.enterpriseapp.utils.SharePreferenceManager;
import com.csi0n.searchjob.enterpriseapp.utils.bean.EmptyBean;
import com.csi0n.searchjob.enterpriseapp.utils.bean.UserCompanyBean;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.CheckUpdate;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.SystemUtils;
import com.csi0n.searchjob.lib.utils.bean.Area;
import com.csi0n.searchjob.lib.utils.bean.City;
import com.csi0n.searchjob.lib.utils.bean.CityBean;
import com.csi0n.searchjob.lib.utils.bean.FuliBean;
import com.csi0n.searchjob.lib.utils.bean.JobTypeBean;
import com.csi0n.searchjob.lib.utils.bean.SearchJobConfigBean;
import com.csi0n.searchjob.lib.widget.ProgressLoading;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;

import org.json.JSONException;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by chqss on 2016/3/25 0025.
 */
public class MainController extends BaseController {
    private Main mMain;
    private AlertView alertView;
    private ProgressLoading loading;
    private DbManager db = x.getDb(Config.dbConfig);

    public MainController(Main main) {
        this.mMain = main;
    }

    public void initMain() {
        if (mMain.isNeed_check_timeout())
            do_check_time_out();
        else
            do_get_company_user_info();
        CheckUpdate checkUpdate = new CheckUpdate(mMain, Config.saveFolder, Config.APP_NAME, R.string.url_checkUpdateEnterprise);
        checkUpdate.start();
        if (!SharePreferenceManager.getFlagIsConfigIsSave()) {
            getConfigFromNet();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ri_head:
                alertView = new AlertView("上传头像", null, "取消", null, new String[]{"拍照", "从相册中选择"}, mMain, AlertView.Style.ActionSheet, new OnItemClickListener() {
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                SystemUtils.openCamera(mMain, Config.PIC_FROM_CAMERA, Config.saveFolder);
                                break;
                            case 1:
                                SystemUtils.openPic(mMain, Config.PIC_FROM_DISK);
                                break;
                        }
                    }
                });
                alertView.show();
                break;
            case R.id.ll_user_info:
                mMain.startUserInfo();
                break;
            case R.id.ll_normal_user:
                mMain.startNormal();
                break;
            case R.id.ll_comments:
                mMain.startComments();
                break;
            default:
                break;
        }
    }

    private void do_check_time_out() {
        com.csi0n.searchjob.lib.utils.Config.DEFAULT_TOKEN = SharePreferenceManager.getKeyCachedToken();
        PostParams params = getDefaultPostParams(R.string.url_checkTimeOut);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                do_get_company_user_info();
            }

            @Override
            public void ErrorResult(int code, String str) {
                super.ErrorResult(code, str);
                mMain.startLogin();
            }
        });
        post.post();
    }

    private void do_get_company_user_info() {
        PostParams params = getDefaultPostParams(R.string.url_getUserCompanyInfo);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<UserCompanyBean>(UserCompanyBean.class) {
            @Override
            public void SuccessResult(UserCompanyBean result) throws JSONException {
                mMain.setHead(result.getCompany_image());
                mMain.setName(result.getCompany_name());
                Config.LOGIN_COMPANY_USER = result;
            }
        });
        post.post();
    }

    public void do_changeHead(final File file) {
        PostParams params = getDefaultPostParams(R.string.url_changeInfo);
        params.put("image", file);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                mMain.setHead(file);
            }
        });
        post.post();
    }

    private void getConfigFromNet() {
        loading = new ProgressLoading(mMain, "初始化...");
        loading.show();
        PostParams params = getDefaultPostParams(R.string.url_getDefaultSearchJobFragmentConfig);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<SearchJobConfigBean>(SearchJobConfigBean.class) {
            @Override
            public void SuccessResult(final SearchJobConfigBean result) throws JSONException {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            List<CityBean> cityBeenList = result.getCity();
                            for (int i = 0; i < cityBeenList.size(); i++) {
                                CityBean cityBean = cityBeenList.get(i);
                                City city = db.selector(City.class).where("id", "=", cityBean.getCity().getId()).findFirst();
                                if (city != null) {
                                    List<Area> areas = db.selector(Area.class).where("city_id", "=", city.getId()).findAll();
                                    if (areas != null && areas.size() > 0) {
                                        CLog.getInstance().iMessage("area data is exist");
                                    } else {
                                        for (int j = 0; j < cityBean.getArea().size(); j++) {
                                            db.save(cityBean.getArea().get(j));
                                        }
                                        CLog.getInstance().iMessage("area data save complete");
                                    }
                                } else {
                                    city = cityBean.getCity();
                                    db.save(city);
                                    CLog.getInstance().iMessage("city data save complete");
                                    List<Area> areas = db.selector(Area.class).where("city_id", "=", city.getId()).findAll();
                                    if (areas != null && areas.size() > 0) {
                                        CLog.getInstance().iMessage("area data is exist");
                                    } else {
                                        for (int j = 0; j < cityBean.getArea().size(); j++) {
                                            db.save(cityBean.getArea().get(j));
                                        }
                                        CLog.getInstance().iMessage("area data save complete");
                                    }
                                }
                            }
                            List<FuliBean> fuliBeanList = result.getFu_li();
                            for (int i = 0; i < fuliBeanList.size(); i++) {
                                FuliBean fuliBean = fuliBeanList.get(i);
                                FuliBean fuliBean_temp = db.selector(FuliBean.class).where("id", "=", fuliBean.getId()).findFirst();
                                if (fuliBean_temp == null) {
                                    db.save(fuliBean);
                                }
                            }
                            List<JobTypeBean> jobTypeBeanList = result.getJob_type();
                            for (int i = 0; i < jobTypeBeanList.size(); i++) {
                                JobTypeBean jobTypeBean = jobTypeBeanList.get(i);
                                JobTypeBean jobTypeBean_temp = db.selector(JobTypeBean.class).where("id", "=", jobTypeBean.getId()).findFirst();
                                if (jobTypeBean_temp == null) {
                                    db.save(jobTypeBean);
                                }
                            }
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        SharePreferenceManager.setFlagIsConfigIsSave(true);
                        if (loading.isShowing())
                            loading.dismiss();
                    }
                }.execute(null, null, null, null, null);
            }
        });
        post.post();
    }
}
