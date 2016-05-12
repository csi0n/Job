package com.csi0n.searchjob.business.pojo.response.ext;

import com.csi0n.searchjob.business.pojo.model.ext.MyModel;
import com.csi0n.searchjob.business.pojo.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/5 0005.
 */
public class GetLoginResponse extends BaseResponse {
    @SerializedName(value = "user")
    public MyModel user;
    @SerializedName(value = "token")
    public String token;
    @Override
    public String toString() {
        return "GetLoginResponse{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}
