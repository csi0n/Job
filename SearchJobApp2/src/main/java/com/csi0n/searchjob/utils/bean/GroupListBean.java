package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by csi0n on 11/11/15.
 */
public class GroupListBean extends BaseBean {
    private List<Group> data;

    public List<Group> getData() {
        return data;
    }

    public void setData(List<Group> data) {
        this.data = data;
    }

    public static class Group extends BaseBean{
        private String id;
        private String name;

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
    }

}
