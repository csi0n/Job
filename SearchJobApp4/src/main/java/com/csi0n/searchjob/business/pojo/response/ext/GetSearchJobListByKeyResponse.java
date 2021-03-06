package com.csi0n.searchjob.business.pojo.response.ext;

import com.csi0n.searchjob.business.pojo.model.ext.CompanyJobModel;
import com.csi0n.searchjob.business.pojo.request.BaseRequest;
import com.csi0n.searchjob.business.pojo.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class GetSearchJobListByKeyResponse extends BaseResponse {
    @SerializedName(value = "data")
    public CompanyJobModel[] companyJobs;
}
