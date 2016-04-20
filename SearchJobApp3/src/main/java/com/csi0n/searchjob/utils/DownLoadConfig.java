package com.csi0n.searchjob.utils;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.Config;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.utils.bean.Area;
import com.csi0n.searchjob.lib.utils.bean.CityBean;
import com.csi0n.searchjob.lib.utils.bean.JobTypeBean;
import com.csi0n.searchjob.model.AreaModel;
import com.csi0n.searchjob.model.CityModel;
import com.csi0n.searchjob.model.ConfigModel;
import com.csi0n.searchjob.model.FuliModel;
import com.csi0n.searchjob.model.JobTypeModel;

import org.json.JSONException;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by chqss on 2016/4/20 0020.
 */
public class DownLoadConfig {
    private ObjectHttpCallBack<ConfigModel> callBack;

    public DownLoadConfig(ObjectHttpCallBack<ConfigModel> callBack) {
        this.callBack = callBack;
    }

    private DbManager db=x.getDb(com.csi0n.searchjob.Config.dbConfig);
    public void download(){
        PostParams params=new PostParams(R.string.url_config);
        x.http().get(params.getParams(), new ObjectHttpCallBack<ConfigModel>(ConfigModel.class) {
            @Override
            public void SuccessResult(ConfigModel result) throws JSONException {
                try {
                    db.delete(AreaModel.class);
                    db.delete(CityModel.class);
                    db.delete(FuliModel.class);
                    db.delete(JobTypeModel.class);
                    List<ConfigModel.CityAndAreaEntity> cityAndAreaEntities=result.getCity();
                    for (ConfigModel.CityAndAreaEntity cityAndArea:cityAndAreaEntities) {
                        db.save(cityAndArea);
                    }
                    List<JobTypeModel> jobTypeBeanLists=result.getJob_type();
                    for (JobTypeModel jobtype:jobTypeBeanLists) {
                        db.save(jobtype);
                    }
                    List<FuliModel> fuliLists=result.getFu_li();
                    for (FuliModel fuli:fuliLists) {
                        db.save(fuli);
                    }
                    if (callBack!=null)
                        callBack.SuccessResult(result);
                } catch (DbException e) {
                    CLog.show("删除配置文件出错!");
                    callBack.ErrorResult(1,"删除配置文件出错!");
                }
            }
        });
    }
}
