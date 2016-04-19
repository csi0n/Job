package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by chqss on 2016/2/1 0001.
 */
public class AddFriendListBean extends BaseBean {
    private List<AddFriend> data;

    public List<AddFriend> getData() {
        return data;
    }

    public void setData(List<AddFriend> data) {
        this.data = data;
    }

    public static class AddFriend extends BaseBean{
        private String id;
        private String uid;
        private String fid;
        private int handle=0;
        private String reason;
        private int atime;
        private int dtime;
        private UserBean user_data;

        public int getHandle() {
            return handle;
        }

        public void setHandle(int handle) {
            this.handle = handle;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public int getAtime() {
            return atime;
        }

        public void setAtime(int atime) {
            this.atime = atime;
        }

        public int getDtime() {
            return dtime;
        }

        public void setDtime(int dtime) {
            this.dtime = dtime;
        }

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

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public UserBean getUser_data() {
            return user_data;
        }

        public void setUser_data(UserBean user_data) {
            this.user_data = user_data;
        }

    }
}
