package com.csi0n.searchjob.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.csi0n.searchjob.lib.R;


/**
 * @author csi0n
 * @date 2015/7/20 002012:54
 * @email chen655@163.com
 */
public class ProgressLoading extends Dialog {
    private String text;
    private TextView txt_text;

    public ProgressLoading(Context context, String text) {
        super(context, R.style.progress_loading_dialog);
        this.text = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.view_progress_loading_dialog);
        txt_text = (TextView) findViewById(R.id.txt_text);
        txt_text.setText(text);
    }
}
