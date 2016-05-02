package com.csi0n.searchjob.business.pojo.event.ext;

import com.csi0n.searchjob.business.pojo.event.BaseEvent;
import com.csi0n.searchjob.core.string.Constants;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class MainFragmentSkipEvent extends BaseEvent {

    public Constants.MainSkipTYPE MAIN_SKIP_TYPE;

    public MainFragmentSkipEvent(Constants.MainSkipTYPE MAIN_SKIP_TYPE) {
        this.MAIN_SKIP_TYPE = MAIN_SKIP_TYPE;
    }
}
