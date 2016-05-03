package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/2/29 0029.
 */

public class AreaModel extends BaseModel {
    @SerializedName(value = "id")
    public int id;
    @SerializedName(value = "area")
    public String area;
    @SerializedName(value = "city_id")
    public int city_id;
    @SerializedName(value = "pinyin")
    public String pinyin;

    @Override
    public String toString() {
        return "AreaModel{" +
                "id=" + id +
                ", area='" + area + '\'' +
                ", city_id=" + city_id +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}