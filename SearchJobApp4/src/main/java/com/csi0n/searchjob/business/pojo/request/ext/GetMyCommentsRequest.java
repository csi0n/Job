package com.csi0n.searchjob.business.pojo.request.ext;

import com.csi0n.searchjob.business.pojo.request.BaseRequest;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class GetMyCommentsRequest extends BaseRequest {
    public int page;

    public GetMyCommentsRequest(int page) {
        this.page = page;
    }
}
