package com.csi0n.searchjob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.SearchUserController;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by csi0n on 2015/12/20 0020.
 * 搜索用户和群
 */
@ContentView(R.layout.aty_search_user)
public class SearchUserActivity extends BaseActivity {
    @ViewInject(value = R.id.tv_person_id)
    public TextView mTVPersonName;
    @ViewInject(value = R.id.tv_group_id)
    public TextView mTVGroupName;
    @ViewInject(value = R.id.query)
    private EditText mEditSearch;

    @Event(value = {R.id.tv_person_id})
    private void onClick(View view) {
        if (mSearchUserController != null)
            mSearchUserController.onClick(view);
    }

    private SearchUserController mSearchUserController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchUserController = new SearchUserController(this);
        mSearchUserController.initModule();
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.title = getString(R.string.str_search_user);
        super.setActionBarRes(actionBarRes);
    }

    @Override
    protected void initWidget() {
        mEditSearch.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int count = mEditSearch.getText().length();
            if (count != 0) {
                setSearchKey(mEditSearch.getText().toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void setSearchKey(final String key) {
        mTVPersonName.setText(key);
        mTVGroupName.setText(key);
    }

    public String getSearchKey() {
        return mEditSearch.getText().toString().trim();
    }

    public void startShowSearchResult(final String key) {
        Bundle bundle = new Bundle();
        bundle.putString(Config.MARK_SHOW_SEARCH_RESULT_ACTIVITY_KEY, key);
        skipActivityWithBundle(this, ShowSearchUserResultActivity.class, bundle);
    }
}
