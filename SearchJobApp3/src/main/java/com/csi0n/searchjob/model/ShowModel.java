package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

/**
 * Created by csi0n on 4/25/16.
 */
public class ShowModel extends BaseBean {

    /**
     * id : 10
     * uid : 15585450
     * username : csi0n
     * status : 0
     * login_status : 1
     * real_name : 陈华清
     * real_code : 321321199306203412
     * phone :
     * i_password : 14e1b600b1fd579f47433b88e8d85291
     * sex : 0
     * intro : 这个人很懒什么都没有留下！
     * uname : binggo
     * head_ic : null
     */

    private String id;
    private String uid;
    private String username;
    private String status;
    private String login_status;
    private String real_name;
    private String real_code;
    private String phone;
    private String sex;
    private String intro;
    private String uname;
    private String head_ic;

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

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_code() {
        return real_code;
    }

    public void setReal_code(String real_code) {
        this.real_code = real_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getHead_ic() {
        return head_ic;
    }

    public void setHead_ic(String head_ic) {
        this.head_ic = head_ic;
    }
}
