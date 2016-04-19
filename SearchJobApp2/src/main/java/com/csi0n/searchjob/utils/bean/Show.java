package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.io.Serializable;

/**
 * Created by chqss on 2016/1/31 0031.
 */
public class Show extends BaseBean {
    private DynamicBean last_dynamic;
    private UserBean user;
    private FriendInfo friend_info;

    public DynamicBean getLast_dynamic() {
        return last_dynamic;
    }

    public void setLast_dynamic(DynamicBean last_dynamic) {
        this.last_dynamic = last_dynamic;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public FriendInfo getFriend_info() {
        return friend_info;
    }

    public void setFriend_info(FriendInfo friend_info) {
        this.friend_info = friend_info;
    }

    public class FriendInfo extends BaseBean{
        private String following;
        private String follower;
        private GroupInfo group_info;
        private FriendData data;

        public String getFollowing() {
            return following;
        }

        public void setFollowing(String following) {
            this.following = following;
        }

        public String getFollower() {
            return follower;
        }

        public void setFollower(String follower) {
            this.follower = follower;
        }

        public GroupInfo getGroup_info() {
            return group_info;
        }

        public void setGroup_info(GroupInfo group_info) {
            this.group_info = group_info;
        }

        public FriendData getData() {
            return data;
        }

        public void setData(FriendData data) {
            this.data = data;
        }

        public class GroupInfo extends BaseBean{
            private String id;
            private String uid;
            private String name;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public class FriendData extends BaseBean {
            private String id;
            private String uid;
            private String fid;
            private String group_id;
            private String remark;

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

            public String getGroup_id() {
                return group_id;
            }

            public void setGroup_id(String group_id) {
                this.group_id = group_id;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
