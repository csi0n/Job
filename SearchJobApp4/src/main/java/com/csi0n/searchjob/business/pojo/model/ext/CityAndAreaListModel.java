package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/5 0005.
 */
public class CityAndAreaListModel extends BaseModel{
    @SerializedName(value = "city")
    public CityModel city;
    @SerializedName(value = "area")
    public AreaModel[] area;
}
