package com.csi0n.searchjob.business.pojo.request.ext;

import android.text.TextUtils;

import com.csi0n.searchjob.business.pojo.request.BaseRequest;
import com.csi0n.searchjob.core.string.Constants;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class GetCompanyCommentResultRequest extends BaseRequest {
    public int company_id;
    public String content;
    public long reply_uid;

    public GetCompanyCommentResultRequest(int company_id, String content, long reply_uid) {
        this.company_id = company_id;
        this.content = content;
        this.reply_uid = reply_uid;
    }
}
