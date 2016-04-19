package com.csi0n.searchjob.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ShowQRCodeController;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.ui.widget.qr.ui.CaptureActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 2015/12/19 0019.
 */
@ContentView(R.layout.aty_show_qr_code)
public class ShowQRCodeActivity extends BaseActivity {
    @ViewInject(value = R.id.iv_qr)
    private ImageView mIVQR;
    @ViewInject(value = R.id.ri_head)
    private RoundImageView mRIHead;
    @ViewInject(value = R.id.tv_uname)
    private TextView mTVUname;
    private ShowQRCodeController mShowQRCodeController;

    @Event(value = {R.id.ll_scan})
    private void onClick(View view) {
        if (mShowQRCodeController != null)
            mShowQRCodeController.onClick(view);
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_qr_card);
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mShowQRCodeController = new ShowQRCodeController(this);
        mShowQRCodeController.initShowQRCode();
    }

    public void startScanQR() {
        startActivity(this, CaptureActivity.class);
    }
}
