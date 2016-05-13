package com.csi0n.searchjob.business.pojo.response.ext;

import com.csi0n.searchjob.business.pojo.model.ext.MoneyBackModel;
import com.csi0n.searchjob.business.pojo.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class GetSearchJobDetailAResponse extends BaseResponse {
    @SerializedName(value = "company_id")
    public int company_id;
    @SerializedName(value = "company_name")
    public String company_name;
    @SerializedName(value = "job_id")
    public int job_id;
    @SerializedName(value = "job_type")
    public String job_type;
    @SerializedName(value = "use_type")
    public String use_type;
    @SerializedName(value = "gongzi")
    public String gongzi;
    @SerializedName(value = "gongzi_detail")
    public String gongzi_detail;
    @SerializedName(value = "work_time")
    public String work_time;
    @SerializedName(value = "shebao")
    public boolean shebao;
    @SerializedName(value = "work_location")
    public String work_location;
    @SerializedName(value = "fuli")
    public String fuli;
    @SerializedName(value = "age")
    public String age;
    @SerializedName(value = "degree_wanted")
    public String degree_wanted;
    @SerializedName(value = "work_life")
    public String work_life;
    @SerializedName(value = "more_infor")
    public String more_infor;
    @SerializedName(value = "work_infor")
    public String work_infor;
    @SerializedName(value = "day")
    public String day;
    @SerializedName(value = "today_money_back")
    public MoneyBackModel[] today_money_backs;
}
