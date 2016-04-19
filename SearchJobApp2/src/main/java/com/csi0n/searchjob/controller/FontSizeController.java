package com.csi0n.searchjob.controller;

import android.view.View;
import android.widget.SeekBar;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.ui.activity.FontSizeActivity;
import com.csi0n.searchjob.utils.SharePreferenceManager;

/**
 * Created by csi0n on 2015/12/16 0016.
 */
public class FontSizeController extends BaseController implements SeekBar.OnSeekBarChangeListener {
    private FontSizeActivity mFontSizeActivity;

    public FontSizeController(FontSizeActivity fontSizeActivity) {
        this.mFontSizeActivity = fontSizeActivity;
    }

    public void initFontSize() {
        int chat_text_size = SharePreferenceManager.getFlagChatTextSize();
        CLog.getInstance().iMessage("get chat text size is:" + chat_text_size);
        mFontSizeActivity.setTextSize(chat_text_size);
        if (chat_text_size == Config.CHAT_TXT_SIZE_DEFAULT)
            mFontSizeActivity.setSeekValue(0);
        else if (chat_text_size == Config.CHAT_TXT_SIZE_2)
            mFontSizeActivity.setSeekValue(1);
        else if (chat_text_size == Config.CHAT_TXT_SIZE_3)
            mFontSizeActivity.setSeekValue(2);
        else if (chat_text_size == Config.CHAT_TXT_SIZE_4)
            mFontSizeActivity.setSeekValue(3);
        else if (chat_text_size == Config.CHAT_TXT_SIZE_5)
            mFontSizeActivity.setSeekValue(4);
    }
@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                mFontSizeActivity.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int value = Config.CHAT_TXT_SIZE_DEFAULT;
        switch (i) {
            case 0:
                value = Config.CHAT_TXT_SIZE_DEFAULT;
                break;
            case 1:
                value = Config.CHAT_TXT_SIZE_2;
                break;
            case 2:
                value = Config.CHAT_TXT_SIZE_3;
                break;
            case 3:
                value = Config.CHAT_TXT_SIZE_4;
                break;
            case 4:
                value = Config.CHAT_TXT_SIZE_5;
                break;
        }
        CLog.getInstance().iMessage("set chat text size is:" + value);
        mFontSizeActivity.setTextSize(value);
        SharePreferenceManager.setFlagChatTextSize(value);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
