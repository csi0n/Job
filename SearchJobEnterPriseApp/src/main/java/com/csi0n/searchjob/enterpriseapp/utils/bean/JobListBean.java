package com.csi0n.searchjob.enterpriseapp.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by csi0n on 3/29/16.
 */
public class JobListBean extends BaseBean {
    private List<JobBean> data;

    public List<JobBean> getData() {
        return data;
    }

    public void setData(List<JobBean> data) {
        this.data = data;
    }

    public static class JobBean extends BaseBean{

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
        private String age;
        private int sex;
        private String job_type;

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getFuli() {
            return fuli;
        }

        public void setFuli(String fuli) {
            this.fuli = fuli;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getJob_type() {
            return job_type;
        }

        public void setJob_type(String job_type) {
            this.job_type = job_type;
        }
    }
}
