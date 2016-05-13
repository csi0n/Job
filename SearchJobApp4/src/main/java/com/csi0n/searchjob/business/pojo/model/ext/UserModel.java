package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by csi0n on 5/6/16.
 */
public class UserModel extends BaseModel {

    /**
     * uid : 15585450
     * login_status : 1
     * sex : 0
     * intro : 这个人很懒什么都没有留下！
     * uname : binggo
     * head_ic : upload/images/b27d3480e38068d6aaf9733a4ef6ea07/origin.png
     */
    @SerializedName(value = "uid")
    public long uid;
    @SerializedName(value = "login_status")
    public String login_status;
    @SerializedName(value = "sex")
    public String sex;
    @SerializedName(value = "intro")
    public String intro;
    @SerializedName(value = "uname")
    public String uname;
    @SerializedName(value = "head_ic")
    public String head_ic;

    @Override
    public String toString() {
        return "UserModel{" +
                "uid='" + uid + '\'' +
                ", login_status='" + login_status + '\'' +
                ", sex='" + sex + '\'' +
                ", intro='" + intro + '\'' +
                ", uname='" + uname + '\'' +
                ", head_ic='" + head_ic + '\'' +
                '}';
    }
}
