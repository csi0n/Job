package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

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
    @Column(name = "p_id",isId = true)
    private int p_id;
    @Column(name = "id")
    private int id;
    @Column(name = "city")
    private String city;
    @Column(name = "pinyin")
    private String pinyin;

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
    public List<AreaModel> getAreaList(DbManager db)throws DbException{
        return db.selector(AreaModel.class).where("city_id", "=", this.id).findAll();
    }
}
