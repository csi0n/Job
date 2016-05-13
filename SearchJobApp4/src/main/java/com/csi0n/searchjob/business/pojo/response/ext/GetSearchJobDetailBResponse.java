package com.csi0n.searchjob.business.pojo.response.ext;

import com.csi0n.searchjob.business.pojo.model.ext.CompanyWorkDetailBModel;
import com.csi0n.searchjob.business.pojo.model.ext.MoneyBackModel;
import com.csi0n.searchjob.business.pojo.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class GetSearchJobDetailBResponse extends BaseResponse {
    @SerializedName(value = "data")
    public CompanyWorkDetailBModel[] data;
}
