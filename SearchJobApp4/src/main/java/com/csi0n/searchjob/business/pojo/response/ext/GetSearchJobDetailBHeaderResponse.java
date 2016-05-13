package com.csi0n.searchjob.business.pojo.response.ext;

import com.csi0n.searchjob.business.pojo.response.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/12 0012.
 */
public class GetSearchJobDetailBHeaderResponse extends BaseResponse {
    @SerializedName(value = "id")
    public String id;
    @SerializedName(value = "name")
    public String name;
    @SerializedName(value = "type")
    public String type;
    @SerializedName(value = "image")
    public String image;
    @SerializedName(value = "city")
    public String city;
    @SerializedName(value = "area")
    public String area;
    @SerializedName(value = "area_detail")
    public String area_detail;
    @SerializedName(value = "intro")
    public String intro;
}
