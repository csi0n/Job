package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by chqss on 2016/3/23 0023.
 */
@Table(name = "search_job_key_cache")
public class SearchJobKeyCacheModel extends BaseBean {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "key_name")
    private String key_name;
    @Column(name = "what")
    private String what;

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey_name() {
        return key_name;
    }

    public void setKey_name(String key_name) {
        this.key_name = key_name;
    }
}
