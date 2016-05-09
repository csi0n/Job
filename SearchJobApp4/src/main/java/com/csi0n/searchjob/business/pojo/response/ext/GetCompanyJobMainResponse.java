package com.csi0n.searchjob.business.pojo.response.ext;

import com.csi0n.searchjob.business.pojo.model.ext.CompanyJobModel;
import com.csi0n.searchjob.business.pojo.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by csi0n on 5/8/16.
 */
public class GetCompanyJobMainResponse extends BaseResponse {
    @SerializedName(value = "data")
    public CompanyJobModel[] companyJobs;
}
