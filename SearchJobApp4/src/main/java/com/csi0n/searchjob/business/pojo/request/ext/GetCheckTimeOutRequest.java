package com.csi0n.searchjob.business.pojo.request.ext;

import com.csi0n.searchjob.business.pojo.request.BaseRequest;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class GetCheckTimeOutRequest extends BaseRequest {
    public String token;

    public GetCheckTimeOutRequest(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "GetCheckTimeOutRequest{" +
                "token='" + token + '\'' +
                '}';
    }
}
