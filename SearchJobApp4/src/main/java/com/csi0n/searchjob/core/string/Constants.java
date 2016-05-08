package com.csi0n.searchjob.core.string;

import com.csi0n.searchjob.business.pojo.model.ext.MyModel;

/**
 * Created by chqss on 2016/5/1 0001.
 */
public class Constants {
    public static String DB_NAME="searchJob";
    public static MyModel LOGIN_USER;
    public static String BaseUrl="http://192.168.56.1/im/";
    public static String Preference_Name="search_job";
    public static final int CODE_CONFIG_UPDATE=16;
    public static final int CODE_SUCCESS=10;

    public static String[] getNewsTitle() {
        String data[] = {"0", "1", "2", "3", "4", "5", "6"};
        return data;
    }

    public static String[] getCompanyWorkDetailTop() {
        String data[] = {"返现岗位", "企业介绍", "经理人", "评论"};
        return data;
    }

    public static String switchNewsURL(int title) {
        switch (title) {
            case 0:
                return "http://hao.360.cn";
            case 1:
                return "http://h5.mse.360.cn/product/navi/news.html";
            case 2:
                return "http://h5.mse.360.cn/product/navi/video.html";
            case 3:
                return "http://h5.mse.360.cn/product/navi/mall.html";
            case 4:
                return "http://h5.mse.360.cn/product/navi/xiaohua.html";
            case 5:
                return "http://h5.mse.360.cn/product/navi/life.html";
            case 6:
                return "http://h5.mse.360.cn/product/navi/zhaopin.html";
            default:
                return null;
        }
    }

    public enum MainSkipTYPE {
        WANGZHIDAOHANG, SEARCHJOB, ME
    }
}
