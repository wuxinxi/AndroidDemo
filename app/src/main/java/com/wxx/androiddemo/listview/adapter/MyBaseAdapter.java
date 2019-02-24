package com.wxx.androiddemo.listview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.bean.User;

import java.util.List;

/**
 * 作者: TangRen on 2019/2/24
 * 包名：com.wxx.androiddemo.listview.adapter
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MyBaseAdapter extends BaseAdapter {
    private List<User> list;
    private LayoutInflater inflater;

    public MyBaseAdapter(Context context,List<User> list ) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_view, parent, false);
            holder = new ViewHolder();
            holder.img = convertView.findViewById(R.id.img);
            holder.content = convertView.findViewById(R.id.content);
            holder.info = convertView.findViewById(R.id.info);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.content.setText(list.get(position).getContent());
        holder.info.setText("年龄：" + list.get(position).getAge() + ",姓名:" + list.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView content;
        TextView info;
    }
}
