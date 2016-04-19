package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by chqss on 2016/2/1 0001.
 */
@Table(name = "message_list_remark")
public class MessageListRemarkBean extends BaseBean {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "uid")
    private String uid;
    @Column(name = "fid")
    private String fid;
    @Column(name = "remark")
    private String remark;
    @Column(name = "ctime")
    private String ctime;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
