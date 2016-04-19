package com.csi0n.searchjob.lib.utils.bean;

import java.util.List;

/**
 * Created by chqss on 2016/3/2 0002.
 */
public class SearchJobConfigBean extends BaseBean {
    private List<CityBean> city;
    private List<JobTypeBean> job_type;
    private List<FuliBean> fu_li;

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public List<JobTypeBean> getJob_type() {
        return job_type;
    }

    public void setJob_type(List<JobTypeBean> job_type) {
        this.job_type = job_type;
    }

    public List<FuliBean> getFu_li() {
        return fu_li;
    }

    public void setFu_li(List<FuliBean> fu_li) {
        this.fu_li = fu_li;
    }
}
