package com.csi0n.searchjob.business.pojo.request.ext;

import com.csi0n.searchjob.business.pojo.request.BaseRequest;

/**
 * Created by csi0n on 5/8/16.
 */
public class GetCompanyJobMainRequest extends BaseRequest {
    public int page;
    public String area_id;
    public String city_id;
    public String money_back;
    public String work_type;
    public String fuli;

    public GetCompanyJobMainRequest(int page, String area_id, String city_id, String money_back, String work_type, String fuli) {
        this.page = page;
        this.area_id = area_id;
        this.city_id = city_id;
        this.money_back = money_back;
        this.work_type = work_type;
        this.fuli = fuli;
    }
}
