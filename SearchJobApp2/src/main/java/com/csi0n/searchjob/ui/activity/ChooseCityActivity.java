package com.csi0n.searchjob.ui.activity;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.ChooseCityController;
import com.csi0n.searchjob.ui.widget.LetterListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 2015/12/17 0017.
 */
@ContentView(R.layout.aty_choose_city)
public class ChooseCityActivity extends BaseActivity {
    @ViewInject(R.id.list_view)
    public ListView personList;
    @ViewInject(R.id.search_result)
    public ListView resultList;
    @ViewInject(R.id.sh)
    public EditText sh;
    @ViewInject(R.id.tv_noresult)
    public TextView tv_noresult;
    @ViewInject(R.id.MyLetterListView01)
    public LetterListView letterListView;
    private ChooseCityController mChooseCityController;

    @Override
    protected void initWidget() {
        mChooseCityController = new ChooseCityController(this);
        mChooseCityController.initChooseCity();
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title=getString(R.string.str_choose_location);
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void onStop() {
        mChooseCityController.onStop();
        super.onStop();
    }
}
