package com.csi0n.searchjob.business.pojo.request.ext;

import com.csi0n.searchjob.business.pojo.request.BaseRequest;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class GetSearchJobDetailBHeaderRequest extends BaseRequest{
    public int company_id;

    public GetSearchJobDetailBHeaderRequest(int company_id) {
        this.company_id = company_id;
    }

    @Override
    public String toString() {
        return "GetSearchJobDetailBHeaderRequest{" +
                "company_id=" + company_id +
                '}';
    }
}
