package com.csi0n.searchjob.utils;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.model.AreaModel;
import com.csi0n.searchjob.model.CityModel;
import com.csi0n.searchjob.model.ConfigModel;
import com.csi0n.searchjob.model.FuliModel;
import com.csi0n.searchjob.model.JobTypeModel;

import org.json.JSONException;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by chqss on 2016/4/20 0020.
 */
public class DownLoadConfig {
    public interface I_DownLoadConfig{
        void onSuccess(ConfigModel result);
        void onError(int code,String str);
        void onFinish();
    }
    private I_DownLoadConfig callBack;

    public DownLoadConfig(I_DownLoadConfig callBack) {
        this.callBack = callBack;
    }

    private DbManager db=x.getDb(com.csi0n.searchjob.Config.dbConfig);
    public void download(){
        PostParams params=new PostParams(R.string.url_config);
        x.http().get(params.getParams(), new ObjectHttpCallBack<ConfigModel>(ConfigModel.class) {
            @Override
            public void SuccessResult(ConfigModel result) throws JSONException {
                try {
                    if (db.findAll(AreaModel.class) != null)
                        db.dropTable(AreaModel.class);
                    if (db.findAll(CityModel.class) != null)
                        db.dropTable(CityModel.class);
                    if (db.findAll(FuliModel.class) != null)
                        db.dropTable(FuliModel.class);
                    if (db.findAll(JobTypeModel.class) != null)
                        db.dropTable(JobTypeModel.class);
                    List<ConfigModel.CityAndAreaEntity> cityAndAreaEntities=result.getCity();
                    for (ConfigModel.CityAndAreaEntity cityAndArea:cityAndAreaEntities) {
                        db.save(cityAndArea.getCity());
                        for (AreaModel area : cityAndArea.getArea()) {
                            db.save(area);
                        }
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
                        callBack.onSuccess(result);
                } catch (DbException e) {
                    callBack.onError(1,"删除配置文件出错!");
                }finally {
                    callBack.onFinish();
                }
            }
        });
    }
}
