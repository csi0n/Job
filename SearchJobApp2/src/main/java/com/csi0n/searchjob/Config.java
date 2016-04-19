package com.csi0n.searchjob;

import com.csi0n.searchjob.lib.utils.FileUtils;
import com.csi0n.searchjob.utils.bean.LoginUser;

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
    public static LoginUser LOGINUSER;
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
    public static final int ERROR_CODE_JSON = 100001;
    public static final String ERROR_STRING_JSON = "数据解析错误!";
    public static final int JMESSAGE_SUCCESS_CODE = 0;
    public static final int RESULT_CODE_SELECT_PICTURE = 8;
    public static final int REQUEST_CODE_TAKE_PHOTO = 4;
    public static final int REQUEST_CODE_SELECT_PICTURE = 6;
    public static final String[] FU_LI = {"住宿", "厂车", "工作餐", "五险一金"};
    public static final String MARK_CHAT_ACTIVITY_COPY_IMAGE = "EASEMOBIMG";
    public static final int MARK_CHAT_ACTIVITY_REQUEST_CODE_COPY_AND_PASTE = 11;
    public static final String MARK_CHAT_ACTIVITY_UPDATE_GROUP_NAME_ACTION = "mark_chat_activity_update_group_name_action";
    public static final String MARK_CHAT_ACTIVITY_TARGET_ID = "mark_chat_activity_targetID";
    public static final String MARK_CHAT_ACTIVITY_GROUP_ID = "mark_chat_activity_group_Id";
    public static final String MARK_CHAT_ACTIVITY_IS_GROUP = "mark_chat_activity_is_group";
    public static final String MARK_CHAT_ACTIVITY_FROM_GROUP = "mark_chat_activity_from_group";
    public static final String MARK_CHAT_ACTIVITY_TITLE_NAME = "mark_chat_activity_title_name";
    public static final String MARK_CHAT_ACTIVITY_MSG_IDS = "mark_chat_activity_msg_ids";
    public static final String MARK_CHAT_ACTIVITY_SEND_PICTURES = "mark_chat_activity_send_pictures";
    public static final String MARK_SHOW_SEARCH_RESULT_ACTIVITY_KEY = "mark_show_search_result_activity_key";
    public static final String MARK_USER_INFO_ACTIVITY_USER_DATA = "mark_user_info_activity_user_data";
    public static final String MARK_USER_INFO_ACTIVITY_USER_ID = "mark_user_info_activity_user_id";
    public static final String MARK_USER_INFO_ACTIVITY_IS_FROM_CAPTURE = "mark_user_info_activity_is_from_capture";
    public static final String MARK_CHANGE_REMARK_ACTIVITY_FRIEND_DATA = "mark_change_remark_activity_user_data";
    public static final String MARK_CHANGE_GROUP_LIST_ACTIVITY_FRIEND_INFO = "mark_change_group_list_activity_friend_info";
    public static final String MARK_MESSAGE_LIST_ACTIVITY_REMARK_CHANGE = "mark_message_list_activity_remark_change";
    public static final String MARK_MESSAGE_LIST_ACTIVITY_REMARK_CHANGE_DATA = "mark_message_list_activity_remark_change_data";
    public static final String MARK_COMAPNY_WORK_DETAIL_ACTIVITY_COMPANY_DATA = "mark_company_work_detail_activity_company_data";
    public static final int MARK_CHAT_ACTIVITY_UPDATE_CHAT_LIST_VIEW = 10;
    public static final int MARK_CHAT_ACTIVITY_REFRESH_GROUP_NAME = 3000;
    public static final int MARK_CHAT_ACTIVITY_REFRESH_GROUP_NUM = 3001;
    public static final String MARK_USER_DYNAMIC_ACTIVITY_IS_SELF = "mark_user_dynamic_activity_is_self";
    public static final String INTENT_USER_LOGIN_SUCCESS = "intent_user_login_success";
    public static final String MARK_MAIN_ACTIVITY_CHANGE_CONTACT = "mark_main_activity_change_contact";
    public static final String MARK_MAIN_ACTIVITY_CHANGE_SEARCH_JOB = "mark_main_activity_change_search_job";
    public static final String MARK_SHOW_SEARCH_JOB_RESULT_KEY="mark_show_search_result_key";
    public static DbManager.DaoConfig dbConfig = new DbManager.DaoConfig()
            .setDbName("SEARCH_JOB.db")
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
