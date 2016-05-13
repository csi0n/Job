package com.csi0n.searchjob.business.pojo.request.ext;

import com.csi0n.searchjob.business.pojo.request.BaseRequest;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class GetSearchJobDetailARequest extends BaseRequest {
    public int company_id;

    public GetSearchJobDetailARequest(int company_id) {
        this.company_id = company_id;
    }

    @Override
    public String toString() {
        return "GetSearchJobDetailARequest{" +
                "company_id=" + company_id +
                '}';
    }
}
