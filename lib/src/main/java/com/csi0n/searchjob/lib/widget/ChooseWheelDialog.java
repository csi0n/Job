package com.csi0n.searchjob.lib.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.csi0n.searchjob.lib.R;

import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author csi0n
 * @date 2015/7/20 002011:55
 * @email chen655@163.com
 */
public class ChooseWheelDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private List<String> mListItem;
    private String mTEMP_STR = "";
    private int TEMP_POSITION;
    private OnClickSubmit onClickSubmit;
    private String title;

    public interface OnClickSubmit {
        void text(int position, String txt);
    }

    public ChooseWheelDialog(Context context, String title, ArrayList<String> list, OnClickSubmit onClickSubmit) {
        super(context, R.style.title_message_dialog);
        this.mContext = context;
        this.title = title;
        this.mListItem = list;
        this.onClickSubmit = onClickSubmit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.view_wheel_choose_string);
        WindowManager m = ((Activity) mContext).getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        WheelView wheelView = (WheelView) findViewById(R.id.wheel_view);
        Button btn_submit = (Button) findViewById(R.id.btn_submit);
        TextView tv_title = (TextView) findViewById(R.id.txt_title);
        tv_title.setText(title);
        btn_submit.setOnClickListener(this);
        wheelView.setOffset(2);
        wheelView.setItems(mListItem);
        wheelView.setSelection(1);
        wheelView.setOnWheelPickerListener(new WheelView.OnWheelPickerListener() {
            @Override
            public void wheelSelect(int position, String content) {
                mTEMP_STR = content;
                TEMP_POSITION = position;
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_submit) {
            onClickSubmit.text(TEMP_POSITION, mTEMP_STR);
            dismiss();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
