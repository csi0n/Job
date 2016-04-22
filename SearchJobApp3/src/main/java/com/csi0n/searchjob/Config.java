package com.csi0n.searchjob;

import com.csi0n.searchjob.lib.utils.FileUtils;
import org.xutils.DbManager;

/**
 * Created by csi0n on 2015/12/14 0014.
 */
public class Config {
    public static final int APP_START_TIME_DELAY = 2 * 1000;
    public static final String FILE_NAME_SUFFIX = "SearchJobLog.log";
    public static final String saveFolder = "SearchJob";
    public static final String APP_NAME="search_job";
    public static final String IM_CONFIGS = "search_job_configs";
    public static final int SUCCESS_CODE = 10;

    public static final int CODE_EMPTY = 14;
    public static final int CODE_TIMEOUT=15;
    public static final int CODE_FRIEND_LIST_CHANGE = 250;
    public static int DEFAULT_PAGE = 20;
    /*
0:展示通知，有声音有震动
2:展示通知，无声音有震动
3:展示通知，有声音无震动
1:不展示通知
4:展示通知，无声音无震动
*/
    public static final int NOTI_MODE_DEFAULT = 0;
    public static final int NOTI_MODE_NO_NOTIFICATION = 1;
    public static final int NOTI_MODE_NO_SOUND = 2;
    public static final int NOTI_MODE_NO_VIBRATE = 3;
    public static final int NOTI_MODE_SILENCE = 4;
    public static final int CHAT_TXT_SIZE_DEFAULT = 15;
    public static final int CHAT_TXT_SIZE_2 = 18;
    public static final int CHAT_TXT_SIZE_3 = 21;
    public static final int CHAT_TXT_SIZE_4 = 24;
    public static final int CHAT_TXT_SIZE_5 = 27;
    public static DbManager.DaoConfig dbConfig = new DbManager.DaoConfig()
            .setDbName("SEARCH_JOB.db")
            .setDbDir(FileUtils.getSaveFolder(Config.saveFolder))
            .setDbVersion(1)
            .setAllowTransaction(true)
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    db.getDatabase().enableWriteAheadLogging();
                }
            })
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    // TODO: ...
                    // db.addColumn(...);
                    // db.dropTable(...);
                    // ...
                }
            });
    public  static  final String MARK_MAIN_IS_NEED_AUTO_LOGIN="mark_main_is_need_auto_login";
    public  static final String MARK_MAIN_ACTIVITY_CHANGE_SEARCH_JOB="mark_main_activity_change_search_job";
    public static final String MARK_SHOW_SEARCH_JOB_RESULT_KEY="mark_show_search_job_result_key";
    public static final String MARK_COMAPNY_WORK_DETAIL_ACTIVITY_COMPANY_DATA="mark_company_work_detail_activity_company_data";
}
