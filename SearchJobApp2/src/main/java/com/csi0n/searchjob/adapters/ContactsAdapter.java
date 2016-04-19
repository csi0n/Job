package com.csi0n.searchjob.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csi0n.searchjob.R;
import com.csi0n.searchjob.lib.widget.RoundImageView;
import com.csi0n.searchjob.ui.activity.ContactsActivity;
import com.csi0n.searchjob.utils.bean.PhoneNumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by csi0n on 2/21/16.
 */
public class ContactsAdapter extends BaseAdapter {
    public List<PhoneNumberInfo> datas;
    private ContactsActivity mContactsActivity;

    public ContactsAdapter(ContactsActivity mContactsActivity) {
        this.mContactsActivity = mContactsActivity;
        datas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if (datas != null)
            return datas.size();
        else
            return 0;
    }

    @Override
    public PhoneNumberInfo getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContactsActivity, R.layout.view_adapter_contacts_item, null);
            holder.mhead = (RoundImageView) convertView.findViewById(R.id.ri_head);
            holder.mname = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mphonenumber = (TextView) convertView.findViewById(R.id.tv_phone);
            holder.add = (TextView) convertView.findViewById(R.id.tv_add);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.mname.setText(getItem(position).getName());
        holder.mphonenumber.setText(getItem(position).getNumber());
        return convertView;
    }

    private class ViewHolder {
        RoundImageView mhead;
        TextView mname, mphonenumber, add;
    }
}
