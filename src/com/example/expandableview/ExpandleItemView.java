package com.example.expandableview;

import java.util.ArrayList;
import java.util.List;

import com.example.expandableview.adapter.MyBaseAdapter;
import com.example.expandableview.adapter.ViewHolder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

public class ExpandleItemView extends LinearLayout {
	/**显示在toggleButton的标题文字*/
	public String mTitle;
	/** 底部按钮 */
	private Button mBottomBtn;
	/** 展示要筛选的数据*/
	private GridView mGridView;
	/** 筛选的数据内容*/
	private List<String> mGridviewDatas;

	public ExpandleItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ExpandleItemView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public ExpandleItemView(Context context) {
		this(context, null);
	}

	public ExpandleItemView(String title, Context context,List<String> datas) {
		this(context);
		setTitle(title);
		mGridviewDatas = datas;
		init();
	}
	
	private void init() {
		setBackgroundColor(getResources().getColor(android.R.color.white));
		/**将布局inflate到此视图中*/
		LayoutInflater.from(getContext()).inflate(R.layout.expand_item_layout, this, true);
		setOrientation(LinearLayout.VERTICAL);
		
		mGridView = (GridView) findViewById(R.id.gridview);
		mBottomBtn = (Button) findViewById(R.id.btn_all);
		/**自己写的万能适配器，一句话就使用了*/
		mGridView.setAdapter(new MyBaseAdapter<String>(mGridviewDatas, R.layout.gridview_item, getContext()) {

			@Override
			protected void convert(ViewHolder viewHolder, String t) {
				viewHolder.setBtnText(R.id.item_text, t);
			}
		});
		/**每一个子项回调给监听者*/
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(mOnExpandItemClick != null)
				{
					mOnExpandItemClick.onItemClick(position);
				}
			}
		});
		/**底部按钮的回调*/
		mBottomBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mOnExpandItemClick != null)
				{
					mOnExpandItemClick.onBottomClick();
				}
			}
		});
	}
	
	public String getTitle() {
		return mTitle == null ? new String() : mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public List<String> getmGridviewDatas() {
		return mGridviewDatas == null ? new ArrayList<String>() : mGridviewDatas;
	}

	public void setmGridviewDatas(List<String> mGridviewDatas) {
		this.mGridviewDatas = mGridviewDatas;
	}

	/**
	 * 累加子类的高度作为自身的高度
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int cCount = getChildCount();

		int desireWidth = MeasureSpec.getSize(widthMeasureSpec);
		int desireHeight = 0;
		for (int i = 0; i < cCount; i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
			desireHeight += child.getMeasuredHeight();
		}
		setMeasuredDimension(desireWidth, desireHeight);
	}

	/**
	 * 点击item事件回调给监听者
	 * @author rander
	 */
	public interface OnExpandItemClick
	{
		void onItemClick(int position);
		void onBottomClick();
	}
	
	private OnExpandItemClick mOnExpandItemClick;

	public void setOnExpandItemClick(OnExpandItemClick onExpandItemClick) {
		this.mOnExpandItemClick = onExpandItemClick;
	}
	
	
	
}
