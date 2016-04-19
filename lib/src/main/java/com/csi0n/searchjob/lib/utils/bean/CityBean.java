package com.csi0n.searchjob.lib.utils.bean;

import java.util.List;

/**
 * Created by chqss on 2016/2/29 0029.
 */
public class CityBean extends BaseBean {
    private City city;
    private List<Area> area;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Area> getArea() {
        return area;
    }

    public void setArea(List<Area> area) {
        this.area = area;
    }


}
