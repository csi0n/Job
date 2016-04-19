package com.csi0n.searchjob.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.NearByPersonController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 2015/12/20 0020.
 */
@ContentView(R.layout.aty_near_by_person)
public class NearByPersonActivity extends BaseActivity {
    private NearByPersonController mNearByPersonController;

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_nearby_person);
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mNearByPersonController = new NearByPersonController(this);
        mNearByPersonController.initNearByPerson();
    }
}
