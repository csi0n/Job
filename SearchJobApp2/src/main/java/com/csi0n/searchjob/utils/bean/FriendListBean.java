package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by csi0n on 12/26/15.
 */
public class FriendListBean extends BaseBean {
    private List<FriendBean> data;

    public List<FriendBean> getData() {
        return data;
    }

    public void setData(List<FriendBean> data) {
        this.data = data;
    }

    public static class FriendBean extends BaseBean{
    private String id;
    private String name;
    private boolean ShowBottom=false;
    private boolean normal=false;

    private List<UserBean> friends_data;

    public boolean isNormal() {
        return normal;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }

    public boolean isShowBottom() {
        return ShowBottom;
    }

    public void setShowBottom(boolean showBottom) {
        ShowBottom = showBottom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserBean> getFriends_data() {
        return friends_data;
    }

    public void setFriends_data(List<UserBean> friends_data) {
        this.friends_data = friends_data;
    }
}

}
