package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class MoneyBackModel extends BaseModel {
    @SerializedName(value = "id")
    public String id;
    @SerializedName(value = "uid")
    public String uid;
    @SerializedName(value = "company_id")
    public String company_id;
    @SerializedName(value = "money_back_famale")
    public String money_back_famale;
    @SerializedName(value = "money_back_male")
    public String money_back_male;
    @SerializedName(value = "add_time")
    public String add_time;
    @SerializedName(value = "jingliren")
    public JinLiRenModel jingliren;
}
