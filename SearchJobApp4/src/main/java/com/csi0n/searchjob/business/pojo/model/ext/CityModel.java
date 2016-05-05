package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/4/20 0020.
 */
public class CityModel extends BaseModel {
    @SerializedName(value = "id")
    public long id;
    @SerializedName(value = "city")
    public String city;
    @SerializedName(value = "pinyin")
    public String pinyin;

    @Override
    public String toString() {
        return "CityModel{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
