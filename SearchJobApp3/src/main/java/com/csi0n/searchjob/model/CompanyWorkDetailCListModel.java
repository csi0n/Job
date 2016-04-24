package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by chqss on 2016/3/7 0007.
 */
public class CompanyWorkDetailCListModel extends BaseBean {
    private List<CompanyWorkDetailCModel> data;

    public List<CompanyWorkDetailCModel> getData() {
        return data;
    }

    public void setData(List<CompanyWorkDetailCModel> data) {
        this.data = data;
    }

    public  static  class CompanyWorkDetailCModel extends BaseBean {

        /**
         * company_id : 3
         * uid : 15823766
         * username : test1
         * real_name : 胡兵
         * phone : 180124886671
         * head_ic : null
         * intro : 这个人很懒什么都没有留下!
         * sex : 0
         * is_follow : 0
         */

        private String company_id;
        private String uid;
        private String username;
        private String real_name;
        private String phone;
        private String head_ic;
        private String intro;
        private String sex;
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

        public String getHead_ic() {
            return head_ic;
        }

        public void setHead_ic(String head_ic) {
            this.head_ic = head_ic;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }
    }
}
