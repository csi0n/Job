package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailBModel extends BaseModel {
    @SerializedName(value = "job_id")
    public String job_id;
    @SerializedName(value = "company_id")
    public String company_id;
    @SerializedName(value = "fuli")
    public String fuli;
    @SerializedName(value = "sex")
    public int sex;
    @SerializedName(value = "age")
    public String age;
    @SerializedName(value = "job_type")
    public String job_type;
}
