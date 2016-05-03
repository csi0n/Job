package com.csi0n.searchjob.business.pojo.model.ext;

import com.csi0n.searchjob.business.pojo.model.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chqss on 2016/5/2 0002.
 */
public class FuliModel extends BaseModel {
    @SerializedName(value = "id")
    public int id;
    @SerializedName(value = "name")
    public String name;

    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "FuliModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
