package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class JinLiRenModel extends BaseModel {
    @SerializedName(value = "uid")
    public String uid;
    @SerializedName(value = "real_name")
    public String real_name;
    @SerializedName(value = "phone")
    public String phone;
    @SerializedName(value = "username")
    public String username;
    @SerializedName(value = "is_follow")
    public  boolean is_follow;
}
