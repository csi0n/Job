package com.csi0n.searchjob.lib.utils.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by chqss on 2016/2/29 0029.
 */
@Table(name = "city_bean")
public class City extends BaseBean {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "city")
    private String city;
    @Column(name = "pinyin")
    private String pinyin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}