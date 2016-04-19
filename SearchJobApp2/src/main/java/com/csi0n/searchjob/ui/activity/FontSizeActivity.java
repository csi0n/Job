package com.csi0n.searchjob.ui.activity;

import android.widget.SeekBar;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.FontSizeController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 2015/12/16 0016.
 */
@ContentView(R.layout.aty_font)
public class FontSizeActivity extends BaseActivity {
    private FontSizeController mFontSizeController;
    @ViewInject(value = R.id.seekbar)
    private SeekBar mSeekBar;
    @ViewInject(value = R.id.rece_msg_content)
    private TextView mTVReceContent;
    @ViewInject(value = R.id.my_msg_content)
    private TextView mTVSendContent;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "修改字体";
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mFontSizeController = new FontSizeController(this);
        mFontSizeController.initFontSize();
        mSeekBar.setOnSeekBarChangeListener(mFontSizeController);
    }

    public void setTextSize(float size) {
        mTVReceContent.setTextSize(size);
        mTVSendContent.setTextSize(size);
    }

    public void setSeekValue(int value) {
        mSeekBar.setProgress(value);
    }
}
