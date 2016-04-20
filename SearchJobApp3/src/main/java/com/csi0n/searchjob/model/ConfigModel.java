package com.csi0n.searchjob.model;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.util.List;

/**
 * Created by chqss on 2016/4/20 0020.
 */
public class ConfigModel extends BaseBean {
    /**
     * city : {"id":"1","city":"无锡","pinyin":"wuxi"}
     * area : [{"id":"1","area":"崇安区","city_id":"1","pinyin":"chonganqu"},{"id":"2","area":"南长区","city_id":"1","pinyin":"nanchangqu"},{"id":"3","area":"北塘区","city_id":"1","pinyin":"beitangqu"},{"id":"4","area":"新区","city_id":"1","pinyin":"xinqu"},{"id":"5","area":"滨湖区","city_id":"1","pinyin":"binhuqu"},{"id":"6","area":"惠山区","city_id":"1","pinyin":"huishangqu"},{"id":"7","area":"锡山区","city_id":"1","pinyin":"xishanqu"},{"id":"8","area":"江阴市","city_id":"1","pinyin":"jiangyinshi"},{"id":"9","area":"宜兴市","city_id":"1","pinyin":"yixingshi"}]
     */
    private List<CityAndAreaEntity> city;
    /**
     * id : 1
     * name : 普工
     */
    private List<JobTypeModel> job_type;
    /**
     * id : 4
     * name : 五险一金
     */
    private List<FuliModel> fu_li;
    public List<CityAndAreaEntity> getCity() {
        return city;
    }
    public void setCity(List<CityAndAreaEntity> city) {
        this.city = city;
    }
    public List<JobTypeModel> getJob_type() {
        return job_type;
    }
    public void setJob_type(List<JobTypeModel> job_type) {
        this.job_type = job_type;
    }
    public List<FuliModel> getFu_li() {
        return fu_li;
    }
    public void setFu_li(List<FuliModel> fu_li) {
        this.fu_li = fu_li;
    }
    public static class CityAndAreaEntity {
        /**
         * id : 1
         * city : 无锡
         * pinyin : wuxi
         */
        private CityModel city;
        /**
         * id : 1
         * area : 崇安区
         * city_id : 1
         * pinyin : chonganqu
         */
        private List<AreaModel> area;
        public CityModel getCity() {
            return city;
        }
        public void setCity(CityModel city) {
            this.city = city;
        }
        public List<AreaModel> getArea() {
            return area;
        }
        public void setArea(List<AreaModel> area) {
            this.area = area;
        }
    }
}
