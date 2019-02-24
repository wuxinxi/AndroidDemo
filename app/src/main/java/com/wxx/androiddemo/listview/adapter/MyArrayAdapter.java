package com.wxx.androiddemo.listview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxx.androiddemo.R;
import com.wxx.androiddemo.bean.User;

import java.util.List;

/**
 * 作者: TangRen on 2019/2/23
 * 包名：com.wxx.androiddemo.listview.adapter
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */
public class MyArrayAdapter extends ArrayAdapter {

    private List<User> list;
    private LayoutInflater inflater;

    public MyArrayAdapter(@NonNull Context context, List<User> list) {
        super(context, -1, list);
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.content = convertView.findViewById(R.id.content);
            viewHolder.info = convertView.findViewById(R.id.info);
            viewHolder.img = convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.content.setText(list.get(position).getContent());
        viewHolder.info.setText("姓名:" + list.get(position).getName() + "  年龄:" + list.get(position).getAge());
        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView content;
        TextView info;
    }
}
