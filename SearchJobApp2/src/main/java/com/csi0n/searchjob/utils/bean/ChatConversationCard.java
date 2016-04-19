package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by chqss on 2016/2/1 0001.
 */
@Table(name = "chat_conversation")
public class ChatConversationCard extends BaseBean {
    @Column(name = "key_id",isId = true)
    private float key_id;
    /*1.普通消息,2.群组消息*/
    @Column(name = "type")
    private int type;
    @Column(name = "remark")
    private String remark;
    @Column(name = "username")
    private String username;

    public float getKey_id() {
        return key_id;
    }

    public void setKey_id(float key_id) {
        this.key_id = key_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
