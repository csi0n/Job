package com.csi0n.searchjob.core.io;

/**
 * Created by chqss on 2016/5/15 0015.
 */
public interface ProgressListener {
    void onProgress(long currentBytes, long contentLength, boolean done);
}
