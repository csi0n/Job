package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.io.Serializable;

/**
 * Created by csi0n on 11/9/15.
 */
public class DynamicBean extends BaseBean {
    private String id;
    private DynamicData data;
    private String ctime;
    private String status;
    private UserBean user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DynamicData getData() {
        return data;
    }

    public void setData(DynamicData data) {
        this.data = data;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public class DynamicData extends BaseBean {
        private String data;
        private String images;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }
    }
}
