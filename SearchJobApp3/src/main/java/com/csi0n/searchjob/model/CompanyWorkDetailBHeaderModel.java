package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

/**
 * Created by chqss on 2016/3/6 0006.
 */
public class CompanyWorkDetailBHeaderModel extends BaseBean {

    /**
     * id : 1
     * name : 大东电子
     * type : 名营企业
     * image : upload/default/default_company_image.png
     * city : 无锡
     * area : 滨湖区
     * area_detail : 新锡路
     * intro : 这个企业很懒，什么介绍都没留下
     */

    private String id;
    private String name;
    private String type;
    private String image;
    private String city;
    private String area;
    private String area_detail;
    private String intro;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setArea_detail(String area_detail) {
        this.area_detail = area_detail;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public String getArea_detail() {
        return area_detail;
    }

    public String getIntro() {
        return intro;
    }
}
