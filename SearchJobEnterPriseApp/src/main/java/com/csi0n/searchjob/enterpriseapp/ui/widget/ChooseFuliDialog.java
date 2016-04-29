package com.csi0n.searchjob.enterpriseapp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.csi0n.searchjob.enterpriseapp.Config;
import com.csi0n.searchjob.enterpriseapp.R;
import com.csi0n.searchjob.enterpriseapp.adapter.FuliCheckAdapter;
import com.csi0n.searchjob.lib.utils.bean.FuliBean;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by csi0n on 3/30/16.
 */
public class ChooseFuliDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private List<FuliBean> TEMP_BEAN_LIST;
    private FuliCheckAdapter adapter;
    private DbManager db = x.getDb(Config.dbConfig);
    private onSubmit onSubmit;

    public interface onSubmit {
        void onSub(List<FuliBean> fuliBeenList);
    }

    public ChooseFuliDialog(Context context, onSubmit onSubmit) {
        super(context,R.style.title_message_dialog);
        this.mContext = context;
        this.onSubmit = onSubmit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_dialog_choose_fuli);
        ListView mList = (ListView) findViewById(R.id.mList);
        Button btnSub = (Button) findViewById(R.id.btn_submit);

        try {
            List<FuliBean> fuliBeanList = db.selector(FuliBean.class).findAll();
            adapter = new FuliCheckAdapter(mContext);
            adapter.listData = fuliBeanList;
            mList.setAdapter(adapter);
            btnSub.setOnClickListener(this);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                onSubmit.onSub(adapter.listData);
                dismiss();
                break;
            default:
                break;
        }
    }
}
