package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by chqss on 2016/3/7 0007.
 */
public class CompanyWorkDetailCListBean extends BaseBean {
    private List<CompanyWorkDetailCBean> data;

    public List<CompanyWorkDetailCBean> getData() {
        return data;
    }

    public void setData(List<CompanyWorkDetailCBean> data) {
        this.data = data;
    }

    public  static  class CompanyWorkDetailCBean extends BaseBean {


        /**
         * company_id : 3
         * uid : 15823766
         * username : test1
         * real_name : 胡兵
         * phone : 180124886671
         * is_follow : 1
         */

        private String company_id;
        private String uid;
        private String username;
        private String real_name;
        private String phone;
        private int is_follow;

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }
    }
}
