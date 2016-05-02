package com.csi0n.searchjob.business.pojo.request;

import com.csi0n.searchjob.core.system.SystemUtils;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class BaseRequest  implements IRequest{
    public String version;

    public BaseRequest() {
        this.version = SystemUtils.getVersion();
    }
}
