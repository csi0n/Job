package com.csi0n.searchjob.business.pojo.request.ext;

import com.csi0n.searchjob.business.pojo.request.BaseRequest;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by chqss on 2016/5/13 0013.
 */
public class GetChangeUserInfoRequest extends BaseRequest {
    public RequestBody head;
    public String old_pass_word;
    public String new_pass_word;
    public String uname;
    public String intro;
    public String sex;
    public String name;
    public String code;

    public GetChangeUserInfoRequest(File head_id, String old_pass_word, String new_pass_word, String uname, String intro, String sex, String name, String code) {
        if (head_id != null){
            RequestBody head = RequestBody.create(MultipartBody.FORM, head_id);
            this.head = head;
        }
        this.old_pass_word = old_pass_word;
        this.new_pass_word = new_pass_word;
        this.uname = uname;
        this.intro = intro;
        this.sex = sex;
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return "GetChangeUserInfoRequest{" +
                "head=" + head +
                ", old_pass_word='" + old_pass_word + '\'' +
                ", new_pass_word='" + new_pass_word + '\'' +
                ", uname='" + uname + '\'' +
                ", intro='" + intro + '\'' +
                ", sex='" + sex + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
