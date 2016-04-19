package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

/**
 * Created by csi0n on 2/21/16.
 */
public class PhoneNumberInfo extends BaseBean {
    private String name;
    private String number;
    public PhoneNumberInfo(String name,String number){
        this.name=name;
        this.number=number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
