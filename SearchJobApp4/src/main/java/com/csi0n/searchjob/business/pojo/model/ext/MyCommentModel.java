package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class MyCommentModel extends BaseModel {
    @SerializedName(value = "id")
    public String id;
    @SerializedName(value = "name")
    public String name;
    @SerializedName(value = "image")
    public String image;
    @SerializedName(value = "intro")
    public String intro;
    @SerializedName(value = "content")
    public String content;
    @SerializedName(value = "add_time")
    public long add_time;
}
