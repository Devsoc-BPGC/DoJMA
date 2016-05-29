package com.csatimes.dojma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vikramaditya Kukreja on 23-05-2016.
 */
public class CustomSettingsAdapter extends BaseAdapter{
    Context context;
    List<Settings.SettingsItem> itemsList;
    public CustomSettingsAdapter(Context context, List<Settings.SettingsItem> itemsList) {
        this.context = context;
        this.itemsList=itemsList;
    }
    private static class ViewHolder {
        TextView name;
        TextView desc;
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.settings_lv_item_format, null);
        }
        holder.name = (TextView) view.findViewById(R.id.settings_lv_settings_name);
        holder.desc = (TextView) view.findViewById(R.id.settings_lv_settings_desc);


        holder.name.setText(itemsList.get(position).name);
        holder.desc.setText(itemsList.get(position).desc);


        view.setTag(holder);
        return view;

    }
}
