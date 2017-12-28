package com.example.cz.greendao;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CZ on 2017/12/28.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    List<User> list;

    public MyAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder holder;
        if (view == null) {
            view = View.inflate(context, R.layout.item_adapter, null);
            holder = new MyViewHolder();
            holder.id = (TextView) view.findViewById(R.id.id);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.age = (TextView) view.findViewById(R.id.age);
            view.setTag(holder);
        }else {
            holder = (MyViewHolder) view.getTag();
        }
        //"id=========" + list.get(i).getId() + "name===== " + list.get(i).getName() + "age====" + list.get(i).getAge()
        holder.id.setText("id:"+list.get(i).getId()+"-------");
        holder.name.setText("name:"+list.get(i).getName()+"-------");
        holder.age.setText("age:"+list.get(i).getAge());
        return view;
    }

    public class MyViewHolder {
        TextView id;
        TextView name;
        TextView age;
    }
}
