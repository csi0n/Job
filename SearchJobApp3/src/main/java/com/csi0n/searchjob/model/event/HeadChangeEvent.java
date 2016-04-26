package com.csi0n.searchjob.model.event;

import com.csi0n.searchjob.lib.utils.bean.BaseBean;

import java.io.File;

/**
 * Created by csi0n on 4/25/16.
 */
public class HeadChangeEvent extends BaseBean{


    private File headFile;

    public HeadChangeEvent(File headFile) {
        this.headFile = headFile;
    }

    public File getHeadFile() {
        return headFile;
    }

    public void setHeadFile(File headFile) {
        this.headFile = headFile;
    }
}
