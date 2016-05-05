package com.csi0n.searchjob.business.pojo.response.ext;

import com.csi0n.searchjob.business.pojo.model.ext.CityAndAreaListModel;
import com.csi0n.searchjob.business.pojo.model.ext.FuliModel;
import com.csi0n.searchjob.business.pojo.model.ext.JobTypeModel;
import com.csi0n.searchjob.business.pojo.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/3 0003.
 */
public class GetConfigResponse extends BaseResponse {
    @SerializedName(value = "version")
    public String version;
    @SerializedName(value = "city")
    public CityAndAreaListModel[] cityAndAreaLists;
    @SerializedName(value = "job_type")
    public JobTypeModel[] jobTypes;
    @SerializedName(value = "fu_li")
    public FuliModel[] fuLis;
}
