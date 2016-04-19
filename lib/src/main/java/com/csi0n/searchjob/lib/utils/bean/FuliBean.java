package com.csi0n.searchjob.lib.utils.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by chqss on 2016/3/2 0002.
 */
@Table(name = "table_fuli")
public class FuliBean extends BaseBean {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "name")
    private String name;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
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
