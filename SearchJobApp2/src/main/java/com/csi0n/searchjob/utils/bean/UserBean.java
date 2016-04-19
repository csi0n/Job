package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.io.Serializable;

/**
 * Created by csi0n on 12/26/15.
 */
public class UserBean extends BaseBean {
    private boolean isNormal=true;
    private String uid;
    private String username;
    private String status;
    private String login_status;
    private GroupData group_data;
    private String remark;

    public boolean isNormal() {
        return isNormal;
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public GroupData getGroup_data() {
        return group_data;
    }

    public void setGroup_data(GroupData group_data) {
        this.group_data = group_data;
    }

    public class GroupData extends BaseBean{
        private String fid;
        private String remark;
        private String ctime;

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }
}
