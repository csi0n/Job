package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by csi0n on 2015/12/28 0028.
 */
public class UserDynamicBean extends BaseBean {
    private String head;// 头像资源ID
    private String name;// 姓名
    private String date;// 日期
    private String phonemodel;// 手机型号
    private int type;// 消息类型
    private boolean agree;//是否点过赞
    private String address;//位置信息
    private List<String> agreeShow;//获得“赞”的姓名列表
    private List<String> comments;//用户评论列表

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhonemodel() {
        return phonemodel;
    }

    public void setPhonemodel(String phonemodel) {
        this.phonemodel = phonemodel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getAgreeShow() {
        return agreeShow;
    }

    public void setAgreeShow(List<String> agreeShow) {
        this.agreeShow = agreeShow;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
