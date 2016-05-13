package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailCModel extends BaseModel {

    /**
     * company_id : 3
     * uid : 15823766
     * username : test1
     * real_name : 胡兵
     * phone : 180124886671
     * head_ic : null
     * intro : 这个人很懒什么都没有留下!
     * sex : 0
     * is_follow : 0
     */

    @SerializedName("company_id")
    public String companyId;
    @SerializedName("uid")
    public String uid;
    @SerializedName("username")
    public String username;
    @SerializedName("real_name")
    public String realName;
    @SerializedName("phone")
    public String phone;
    @SerializedName("head_ic")
    public String headIc;
    @SerializedName("intro")
    public String intro;
    @SerializedName("sex")
    public String sex;
    @SerializedName("is_follow")
    public boolean isFollow;
}
