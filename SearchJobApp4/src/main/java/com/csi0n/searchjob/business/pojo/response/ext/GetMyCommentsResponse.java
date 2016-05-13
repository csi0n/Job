package com.csi0n.searchjob.business.pojo.response.ext;

import com.csi0n.searchjob.business.pojo.model.ext.MyCommentModel;
import com.csi0n.searchjob.business.pojo.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class GetMyCommentsResponse extends BaseResponse {
    @SerializedName(value = "data")
    public MyCommentModel[] datas;
}
