package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by csi0n on 5/6/16.
 */
public class MyModel extends BaseModel {

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
    @SerializedName(value = "username")
    public String username;
    @SerializedName(value = "head_ic")
    public String head_ic;
    @SerializedName(value = "real_code")
    public String real_code;
    @SerializedName(value = "real_name")
    public String real_name;

    @Override
    public String toString() {
        return "MyModel{" +
                "uid='" + uid + '\'' +
                ", login_status='" + login_status + '\'' +
                ", sex='" + sex + '\'' +
                ", intro='" + intro + '\'' +
                ", uname='" + uname + '\'' +
                ", head_ic='" + head_ic + '\'' +
                ", real_code='" + real_code + '\'' +
                ", real_name='" + real_name + '\'' +
                '}';
    }
}
