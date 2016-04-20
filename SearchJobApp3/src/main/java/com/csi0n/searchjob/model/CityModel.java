package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by chqss on 2016/4/20 0020.
 */
@Table(name = "city_bean")
public class CityModel extends BaseBean {
    /**
     * id : 1
     * city : 无锡
     * pinyin : wuxi
     */
    @Column(name = "id", isId = true)
    private String id;
    @Column(name = "city")
    private String city;
    @Column(name = "pinyin")
    private String pinyin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
