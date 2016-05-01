package com.csi0n.searchjobapp.business.pojo.response;

import com.csi0n.searchjobapp.business.pojo.request.IRequest;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class BaseResponse<T> implements IResponse {
    @SerializedName(value = "status")
    public int status;
    @SerializedName(value = "info")
    public String info;
  @SerializedName(value = "data")
    public T data;
}
