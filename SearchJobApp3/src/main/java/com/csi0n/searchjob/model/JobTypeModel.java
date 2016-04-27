package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by chqss on 2016/3/2 0002.
 */
@Table(name = "table_job_type")
public class JobTypeModel extends BaseBean {
    @Column(name = "p_id",isId = true)
    private int p_id;
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
