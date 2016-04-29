package com.csi0n.searchjob.enterpriseapp;

import com.csi0n.searchjob.enterpriseapp.utils.bean.UserCompanyBean;
import com.csi0n.searchjob.lib.utils.FileUtils;

import org.xutils.DbManager;

/**
 * Created by chqss on 2016/3/25 0025.
 */
public class Config {
    public static final String saveFolder="searchjobenterprise";
    public static final String APP_NAME="searchjobenterprise";
    public static final String FILE_NAME_SUFFIX = "SearchJobLog.log";
    public static final String SHARE_PREFERENCE_NAME="search_job_enterprise";
    public static final int APP_START_TIME_DELAY=3*1000;
    public static final String MARK_MAIN_ACTIVITY_CHECK_TIME_OUT="mrk_main_activity_check_time_out";
    public static UserCompanyBean LOGIN_COMPANY_USER;
    public static final int JPUSH_SUCCESS_CODE = 0;
    public static int PIC_FROM_DISK = 0X2002;
    public static int PIC_FROM_CAMERA = 0X2005;
    public static int D_PIC_FROM_DISK=0X2003;
    public static int D_PIC_FROM_CAMERA=0x2004;
    public static String MARK_SEND_ACTIVITY_IS_FROM_MONEY_BACK="mark_send_activity_is_from_money_back";
    public static DbManager.DaoConfig dbConfig = new DbManager.DaoConfig()
            .setDbName("SEARCH_JOB_ENTERPRISE.db")
            .setDbDir(FileUtils.getSaveFolder(Config.saveFolder))
            .setDbVersion(1)
            .setAllowTransaction(true)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    // TODO: ...
                    // db.addColumn(...);
                    // db.dropTable(...);
                    // ...
                }
            });
}
