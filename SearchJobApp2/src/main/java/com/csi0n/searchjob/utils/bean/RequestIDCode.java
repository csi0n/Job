package com.csi0n.searchjob.utils.bean;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

/**
 * Created by csi0n on 4/10/16.
 */
public class RequestIDCode extends BaseBean {
    private String real_name;
    private String real_code;

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_code() {
        return real_code;
    }

    public void setReal_code(String real_code) {
        this.real_code = real_code;
    }
}
