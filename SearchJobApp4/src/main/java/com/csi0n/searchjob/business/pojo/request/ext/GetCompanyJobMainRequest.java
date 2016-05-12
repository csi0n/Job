package com.csi0n.searchjob.business.pojo.request.ext;

import com.csi0n.searchjob.business.pojo.request.BaseRequest;

/**
 * Created by csi0n on 5/8/16.
 */
public class GetCompanyJobMainRequest extends BaseRequest {
    public int page;
    public long area_id;
    public long city_id;
    public int money_back;
    public int work_type;
    public String fuli;
    public String configVer;

    public GetCompanyJobMainRequest(int page, long area_id, long city_id, int money_back, int work_type, String fuli, String configVer) {
        this.page = page;
        this.area_id = area_id;
        this.city_id = city_id;
        this.money_back = money_back;
        this.work_type = work_type;
        this.fuli = fuli;
        this.configVer = configVer;
    }
}
