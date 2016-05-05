package com.csi0n.searchjob.business.pojo.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class BaseResponse implements IResponse {
    @SerializedName(value = "status")
    public int status;
    @SerializedName(value = "info")
    public String info;
}
