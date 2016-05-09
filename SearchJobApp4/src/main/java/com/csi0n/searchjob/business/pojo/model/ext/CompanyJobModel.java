package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by csi0n on 5/8/16.
 */
public class CompanyJobModel extends BaseModel {
    @SerializedName(value = "id")
    public int id;
    @SerializedName(value = "company_name")
    public String company_name;
    @SerializedName(value = "job_id")
    public String job_id;
    @SerializedName(value = "money_back_famale")
    public int money_back_famale;
    @SerializedName(value = "money_back_male")
    public int money_back_male;
    @SerializedName(value = "age")
    public String age;
    @SerializedName(value = "sex")
    public int sex;
    @SerializedName(value = "fuli")
    public String fuli;
    @SerializedName(value = "area")
    public String area;
    @SerializedName(value = "city")
    public String city;
}
