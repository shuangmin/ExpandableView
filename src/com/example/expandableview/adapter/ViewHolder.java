package com.example.expandableview.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rander on 16-3-24.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private LayoutInflater layoutInflater;
    private View mContentView;
    public ViewHolder(int itemLayoutId,ViewGroup parent, Context mContext) {
        mViews = new SparseArray<View>();
        layoutInflater = LayoutInflater.from(mContext);
        mContentView = layoutInflater.inflate(itemLayoutId, parent, false);
        mContentView.setTag(this);
    }

    public static ViewHolder get(int position, View convertView, ViewGroup parent,int layoutId,Context context)
    {
        ViewHolder viewHolder;
        if(convertView == null)
        {
            viewHolder = new ViewHolder(layoutId,parent,context);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return viewHolder;
    }

    public <T extends View> T getView(int viewId) {
        View v = mViews.get(viewId);
        if (v == null) {
            v = mContentView.findViewById(viewId);
            mViews.put(viewId, v);
        }
        return (T)v;
    }

    public ViewHolder setTvText(int viewId,String str)
    {
        TextView v = getView(viewId);
        v.setText(str);
        return this;
    }

    public ViewHolder setBtnText(int viewId,String str)
    {
        TextView v = getView(viewId);
        v.setText(str);
        return this;
    }


    public View getmContentView() {
        return mContentView;
    }
}
