package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by chqss on 2016/3/6 0006.
 */
public class CompanyWorkDetailBListBean extends BaseBean {
    private List<CompanyWorkDetailBean> data;

    public List<CompanyWorkDetailBean> getData() {
        return data;
    }

    public void setData(List<CompanyWorkDetailBean> data) {
        this.data = data;
    }

    public static class CompanyWorkDetailBean extends BaseBean {
        /**
         * job_id : 1
         * company_id : 3
         * fuli : 1
         * age : 男:18~36
         * job_type : 普工
         */

        private String job_id;
        private String company_id;
        private String fuli;
        private int sex;
        private String age;
        private String job_type;

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public void setFuli(String fuli) {
            this.fuli = fuli;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public void setJob_type(String job_type) {
            this.job_type = job_type;
        }

        public String getJob_id() {
            return job_id;
        }

        public String getCompany_id() {
            return company_id;
        }

        public String getFuli() {
            return fuli;
        }

        public String getAge() {
            return age;
        }

        public String getJob_type() {
            return job_type;
        }
    }
}
