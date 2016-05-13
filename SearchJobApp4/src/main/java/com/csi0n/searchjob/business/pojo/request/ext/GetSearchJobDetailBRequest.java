package com.csi0n.searchjob.business.pojo.request.ext;

import com.csi0n.searchjob.business.pojo.request.BaseRequest;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class GetSearchJobDetailBRequest extends BaseRequest{
    public int company_id;

    public int page;

    public GetSearchJobDetailBRequest(int page, int company_id) {
        this.company_id = company_id;
        this.page = page;
    }

    @Override
    public String toString() {
        return "GetSearchJobDetailBRequest{" +
                "company_id=" + company_id +
                '}';
    }
}
