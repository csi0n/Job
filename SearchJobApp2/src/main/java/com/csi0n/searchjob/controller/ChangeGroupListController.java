package com.csi0n.searchjob.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.adapters.ChangeGroupAdapter;
import com.csi0n.searchjob.lib.controller.BaseController;
import com.csi0n.searchjob.lib.utils.CLog;
import com.csi0n.searchjob.lib.utils.HttpPost;
import com.csi0n.searchjob.lib.utils.ObjectHttpCallBack;
import com.csi0n.searchjob.lib.utils.PostParams;
import com.csi0n.searchjob.lib.widget.alert.AlertView;
import com.csi0n.searchjob.lib.widget.alert.OnItemClickListener;
import com.csi0n.searchjob.ui.activity.ChangeGroupListActivity;
import com.csi0n.searchjob.utils.bean.EmptyBean;
import com.csi0n.searchjob.utils.bean.GroupListBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by csi0n on 2015/12/30 0030.
 */
public class ChangeGroupListController extends BaseController implements BGARefreshLayout.BGARefreshLayoutDelegate, AdapterView.OnItemClickListener {
    private ChangeGroupListActivity mChangeGroupListActivity;
    private ChangeGroupAdapter adapter;
    private AlertView alertView;
    private EditText etName;

    public ChangeGroupListController(ChangeGroupListActivity changeGroupListActivity) {
        this.mChangeGroupListActivity = changeGroupListActivity;
    }

    public void initChangeGroupList() {
        adapter = new ChangeGroupAdapter(mChangeGroupListActivity, mChangeGroupListActivity.getFRIEND_INFO());
        mChangeGroupListActivity.addFooterView();
        mChangeGroupListActivity.setListAdapter(adapter);
        getGroupFromNet();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                if (adapter.getIndex() != -1)
                    changeGroup(mChangeGroupListActivity.getFRIEND_INFO().getData().getId(), adapter.mGroups.get(adapter.getIndex()).getId());
                else
                    CLog.show("无法获取选中的信息!");
                break;
            default:
                break;
        }
    }

    private void getGroupFromNet() {
        PostParams params = getDefaultPostParams(R.string.url_getGroups);
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<GroupListBean>(GroupListBean.class) {
            @Override
            public void SuccessResult(GroupListBean result) throws JSONException {
                adapter.mGroups.clear();
                adapter.mGroups.addAll(result.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mChangeGroupListActivity.setStopRefresh();
            }
        });
        post.post();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        getGroupFromNet();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final int group_count = adapter.mGroups.size();
        if (i == group_count) {
            alertView = new AlertView("提示", "请输入分组名称！", "取消", null, new String[]{"完成"}, mChangeGroupListActivity, AlertView.Style.Alert, onItemClickListener);
            ViewGroup extView = (ViewGroup) LayoutInflater.from(mChangeGroupListActivity).inflate(R.layout.view_alert_text_edit, null);
            etName = (EditText) extView.findViewById(R.id.etName);
            alertView.addExtView(extView);
            alertView.show();
        }
    }

    private void closeKeyboard() {
        //关闭软键盘
        mChangeGroupListActivity.imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
        //恢复位置
        alertView.setMarginBottom(0);
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(Object o, int position) {
            closeKeyboard();
            //判断是否是拓展窗口View，而且点击的是非取消按钮
            if (o == alertView && position != AlertView.CANCELPOSITION) {
                String name = etName.getText().toString();
                if (name.isEmpty()) {
                    CLog.getInstance().iMessage("no string");
                } else {
                    addGroup(name);
                }
                return;
            }
        }
    };

    private void addGroup(String name) {
        PostParams params = getDefaultPostParams(R.string.url_addGroup);
        params.put("name", name);
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                getGroupFromNet();
            }
        });
        post.post();
    }

    private void changeGroup(String id, String group_id) {
        PostParams params = getDefaultPostParams(R.string.url_changeGroup);
        params.put("id", id);
        params.put("group_id", group_id);
        HttpPost post=new HttpPost(params, new ObjectHttpCallBack<EmptyBean>(EmptyBean.class) {
            @Override
            public void SuccessResult(EmptyBean result) throws JSONException {
                CLog.show("修改成功!");
                mChangeGroupListActivity.finish();
            }
        });
        post.post();
    }
}
