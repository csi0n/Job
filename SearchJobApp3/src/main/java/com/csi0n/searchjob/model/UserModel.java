package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

/**
 * Created by csi0n on 4/23/16.
 */
public class UserModel extends BaseBean {

    /**
     * uid : 15842165
     * login_status : 1
     * sex : 0
     * intro : 这个人很懒什么都没有留下!
     * uname : 小宝宝
     */

    private String uid;
    private String login_status;
    private String sex;
    private String intro;
    private String uname;
    private String head_ic;

    public String getHead_ic() {
        return head_ic;
    }

    public void setHead_ic(String head_ic) {
        this.head_ic = head_ic;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
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
}
