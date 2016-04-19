package com.csi0n.searchjob.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.csi0n.searchjob.R;

import java.util.List;

/**
 * Created by csi0n on 2015/9/25 0025.
 */
public class ExpressionAdapter extends ArrayAdapter<String> {
    public ExpressionAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.row_expression, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_expression);
        String filename = getItem(position);
        int resId = getContext().getResources().getIdentifier(filename, "mipmap", getContext().getPackageName());
        imageView.setImageResource(resId);

        return convertView;
    }
}
