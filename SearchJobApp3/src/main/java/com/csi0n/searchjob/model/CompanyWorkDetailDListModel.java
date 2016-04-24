package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by chqss on 2016/3/7 0007.
 */
public class CompanyWorkDetailDListModel extends BaseBean {
    private List<CompanyWorkDetailDModel> data;

    public List<CompanyWorkDetailDModel> getData() {
        return data;
    }

    public void setData(List<CompanyWorkDetailDModel> data) {
        this.data = data;
    }

    public static class CompanyWorkDetailDModel extends BaseBean {

        /**
         * id : 2
         * uid : 15842165
         * company_id : 3
         * reply_uid : 15823766
         * content : 我感觉他家的普工是蛮好的。
         * add_time : 1457577562
         * user_info : {"uid":"15842165","login_status":"1","sex":"0","intro":"这个人很懒什么都没有留下!","uname":"小宝宝"}
         * reply_user_info : {"uid":"15823766","login_status":"1","sex":"0","intro":"这个人很懒什么都没有留下!","uname":"小宝宝"}
         */

        private String id;
        private String uid;
        private String company_id;
        private String reply_uid;
        private String content;
        private String add_time;
        /**
         * uid : 15842165
         * login_status : 1
         * sex : 0
         * intro : 这个人很懒什么都没有留下!
         * uname : 小宝宝
         */

        private UserModel user_info;
        /**
         * uid : 15823766
         * login_status : 1
         * sex : 0
         * intro : 这个人很懒什么都没有留下!
         * uname : 小宝宝
         */

        private UserModel reply_user_info;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getReply_uid() {
            return reply_uid;
        }

        public void setReply_uid(String reply_uid) {
            this.reply_uid = reply_uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public UserModel getUser_info() {
            return user_info;
        }

        public void setUser_info(UserModel user_info) {
            this.user_info = user_info;
        }

        public UserModel getReply_user_info() {
            return reply_user_info;
        }

        public void setReply_user_info(UserModel reply_user_info) {
            this.reply_user_info = reply_user_info;
        }
    }
}
