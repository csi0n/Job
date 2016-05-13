package com.csi0n.searchjob.business.pojo.request.ext;

import com.csi0n.searchjob.business.pojo.request.BaseRequest;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class GetSearchJobListByKeyRequest extends BaseRequest {
    public int page;
    public String key;
    public String configVer;

    public GetSearchJobListByKeyRequest(int page, String key, String configVer) {
        this.page = page;
        this.key = key;
        this.configVer = configVer;
    }

    @Override
    public String toString() {
        return "GetSearchJobListByKeyRequest{" +
                "page=" + page +
                ", key='" + key + '\'' +
                ", configVer='" + configVer + '\'' +
                '}';
    }
}
