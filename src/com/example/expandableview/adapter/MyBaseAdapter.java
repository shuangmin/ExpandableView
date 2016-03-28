package com.example.expandableview.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**
 * Created by rander on 16-3-24.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected List<T> datas;
    protected int layoutId;
    protected LayoutInflater layoutInflater;
    protected Context mContext;

    public MyBaseAdapter(List<T> datas, int layoutId,Context mContext) {
        this.datas = datas;
        this.layoutId = layoutId;
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return null == datas ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null == datas ? null : datas.size() > position ? datas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent,layoutId);
    }

    public View getView(int position, View convertView, ViewGroup parent, int layoutId)
    {
        ViewHolder viewHolder = ViewHolder.get(position, convertView, parent, layoutId, mContext);
        convert(viewHolder,datas.get(position));
        return viewHolder.getmContentView();
    }

    protected abstract void convert(ViewHolder viewHolder, T t);

}
