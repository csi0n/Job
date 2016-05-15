package com.csi0n.searchjob.business.pojo.response.ext;

import com.csi0n.searchjob.business.pojo.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/14 0014.
 */
public class GetCheckUserAppVerResponse extends BaseResponse {
    @SerializedName(value = "desc")
    public String desc;
    @SerializedName(value = "url")
    public String url;
}
