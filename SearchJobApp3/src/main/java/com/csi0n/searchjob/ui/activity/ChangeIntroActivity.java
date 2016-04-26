package com.csi0n.searchjob.ui.activity;

import android.view.View;
import android.widget.EditText;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ChangeIntroController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 4/25/16.
 */
@ContentView(R.layout.aty_change_intro)
public class ChangeIntroActivity extends BaseActivity {
    @ViewInject(value = R.id.edit_content)
    private EditText mContent;

    @Event(value = {R.id.btn_change})
    private void onClick(View view) {
        if (mChangeIntroController != null)
            mChangeIntroController.onClick(view);
    }

    private ChangeIntroController mChangeIntroController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = "个人介绍";
        super.setActionBarRes(actionBarRes);
    }

    public String getContent() {
        return mContent.getText().toString();
    }

    @Override
    protected void initWidget() {
        mChangeIntroController = new ChangeIntroController(this);
        mChangeIntroController.initChangeIntro();
    }
}
