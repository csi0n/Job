package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class CompanyWorkDetailDModel extends BaseModel {

    /**
     * id : 7
     * uid : 15585450
     * company_id : 3
     * reply_uid : null
     * content : 我是一条评论
     * add_time : 1461503508
     */

    @SerializedName("id")
    public int id;
    @SerializedName("uid")
    public int uid;
    @SerializedName("company_id")
    public String companyId;
    @SerializedName("reply_uid")
    public String  replyUid;
    @SerializedName("content")
    public String content;
    @SerializedName("add_time")
    public long addTime;
    @SerializedName(value = "user_info")
    public UserModel userInfo;
    @SerializedName(value = "reply_user_info")
    public UserModel replyUserInfo;
}
