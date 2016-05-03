package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/3/2 0002.
 */
public class JobTypeModel extends BaseModel {
    @SerializedName(value = "id")
    public int id;
    @SerializedName(value = "name")
    public String name;

    @Override
    public String toString() {
        return "JobTypeModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
