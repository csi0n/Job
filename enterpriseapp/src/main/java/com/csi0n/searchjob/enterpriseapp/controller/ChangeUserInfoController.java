package com.csi0n.searchjob.enterpriseapp.controller;

import android.text.TextUtils;
import android.view.View;

import com.csi0n.searchjob.enterpriseapp.Config;
import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.ui.activity.ChangeUserInfoActivity;
import com.csi0n.searchjob.enterpriseapp.ui.activity.Main;
import com.csi0n.searchjob.enterpriseapp.utils.bean.EmptyBean;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.StringUtils;
import com.csi0n.searchjob.lib.utils.bean.Area;
import com.csi0n.searchjob.lib.utils.bean.City;
import com.csi0n.searchjob.lib.widget.ChooseWheelDialog;

import org.json.JSONException;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csi0n on 3/29/16.
 */
public class ChangeUserInfoController extends BaseController {
    private ChangeUserInfoActivity mChangeUserInfoActivity;
    private DbManager db = x.getDb(Config.dbConfig);
    private City TEMP_CITY;
    private List<Area> TEMP_AREA_LIST;
    private String TEMP_AREA_ID;

    public ChangeUserInfoController(ChangeUserInfoActivity changeUserInfoActivity) {
        this.mChangeUserInfoActivity = changeUserInfoActivity;
    }

    public void initChangeUserInfo() {
        try {
            Area area = db.selector(Area.class).where("id", "=", Config.LOGIN_COMPANY_USER.getArea_id()).findFirst();
            TEMP_AREA_LIST = db.selector(Area.class).where("city_id", "=", area.getCity_id()).findAll();
            TEMP_CITY = db.selector(City.class).where("id", "=", area.getCity_id()).findFirst();
            mChangeUserInfoActivity.setArea(area.getArea());
            mChangeUserInfoActivity.setCity(TEMP_CITY.getCity());
        } catch (DbException e) {
            e.printStackTrace();
        }
        mChangeUserInfoActivity.setIntro(Config.LOGIN_COMPANY_USER.getIntro());
        mChangeUserInfoActivity.setAddressDetail(Config.LOGIN_COMPANY_USER.getArea_detail());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choose_city:
                try {
                    final List<City> cityList = db.findAll(City.class);
                    if (cityList != null && cityList.size() > 0) {
                        ArrayList<String> temp = new ArrayList<>();
                        for (City city : cityList) {
                            temp.add(city.getCity());
                        }
                        new ChooseWheelDialog(mChangeUserInfoActivity, "请选择城市", temp, new ChooseWheelDialog.OnClickSubmit() {
                            @Override
                            public void text(int position, String txt) {
                                mChangeUserInfoActivity.setCity(cityList.get(position).getCity());
                                TEMP_CITY = cityList.get(position);
                                try {
                                    TEMP_AREA_LIST = db.selector(Area.class).where("city_id", "=", TEMP_CITY.getId()).findAll();
                                    TEMP_AREA_ID = String.valueOf(TEMP_AREA_LIST.get(0).getId());
                                    mChangeUserInfoActivity.setArea(TEMP_AREA_LIST.get(0).getArea());
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();
                    } else
                        CLog.show("数据库中没有找到城市信息");

                } catch (DbException e) {

                }
                break;
            case R.id.btn_choose_area:
                if (TEMP_AREA_LIST != null && TEMP_AREA_LIST.size() > 0) {
                    ArrayList<String> temp = new ArrayList<>();
                    for (Area area : TEMP_AREA_LIST) {
                        temp.add(area.getArea());
                    }
                    new ChooseWheelDialog(mChangeUserInfoActivity, "请选择区域", temp, new ChooseWheelDialog.OnClickSubmit() {
                        @Override
                        public void text(int position, String txt) {
                            mChangeUserInfoActivity.setArea(TEMP_AREA_LIST.get(position).getArea());
                            TEMP_AREA_ID = String.valueOf(TEMP_AREA_LIST.get(position).getId());
                        }
                    }).show();
                } else
                    CLog.show("没有找到区域信息!");
                break;
            case R.id.tv_more:
                if (TextUtils.isEmpty(mChangeUserInfoActivity.getAddressDetail()))
                    CLog.show("请填写详细地址!");
                else if (TextUtils.isEmpty(mChangeUserInfoActivity.getIntro()))
                    CLog.show("请输入企业介绍");
                else
                    do_change_info(TEMP_AREA_ID, mChangeUserInfoActivity.getAddressDetail(), mChangeUserInfoActivity.getIntro());
                break;
            default:
                break;
        }
    }

    private void do_change_info(String area_id, String area_detail, String intro) {
        PostParams params = getDefaultPostParams(R.string.url_changeInfo);
        if (!TextUtils.isEmpty(area_id))
            params.put("area_id", area_id);
        if (!TextUtils.isEmpty(area_detail))
            params.put("area_detail", area_detail);
        if (!TextUtils.isEmpty(intro))
            params.put("intro", intro);
        HttpPost post = new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                CLog.show("修改成功!");
            }
        });
        post.post();
    }
}
