package com.example.expandableview;

import java.util.ArrayList;
import java.util.List;

import com.example.expandableview.ExpandleItemView.OnExpandItemClick;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

public class ExpandableView extends LinearLayout implements OnExpandItemClick {
	/** 记录选中的ToggleButton */
	private ToggleButton mSelectToggleBtn;
	/** 筛选 */
	private List<View> mToggleButtons = new ArrayList<>();
	/** 筛选项集合 */
	private List<View> mPopupviews;

	/** popupwindow展示的宽 */
	private int mDisplayWidth;
	/** popupwindow展示的高 */
	private int mDisplayHeight;
	/** 筛选内容用PopupWindow弹出来 */
	private PopupWindow mPopupWindow;
	private Context mContext;

	/** toggleButton正常的字体颜色 */
	int mNormalTextColor = getResources().getColor(R.color.grey);
	/** toggleButton被选中的类型字体颜色 */
	int mSelectTextColor = Color.RED;

	public ExpandableView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public ExpandableView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public ExpandableView(Context context) {
		this(context, null);
	}

	private void init() {
		setOrientation(LinearLayout.HORIZONTAL);
		mDisplayWidth = getResources().getDisplayMetrics().widthPixels;
		mDisplayHeight = getResources().getDisplayMetrics().heightPixels;
		mContext = getContext();
		setBackgroundResource(R.drawable.choosearea_bg_right);
	}

	/**
	 * 初始化数据和布局，做的工作如下： 1.根据筛选项的数量，动态增加上面一排ToggleButton 2.设置每一个ToggleButton的监听事件
	 * 3.toggleButton.setTag(i)这一句非常重要，我们取View数据都是根据这个tag取的 4.
	 * 
	 * @param views
	 */
	public void initViews(List<ExpandleItemView> views) {
		mPopupviews = new ArrayList<>();
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < views.size(); i++) {
			ExpandleItemView view = views.get(i);
			view.setOnExpandItemClick(this);
			final RelativeLayout r = new RelativeLayout(mContext);
			RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			r.addView(view, rl);
			mPopupviews.add(r);

			final ToggleButton toggleButton = (ToggleButton) inflater.inflate(R.layout.toggle_button, this, false);
			toggleButton.setText(view.getTitle());
			mToggleButtons.add(toggleButton);
			addView(toggleButton);
			toggleButton.setTag(i);
			toggleButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					/** 记录选中的ToggleButton,有了这个什么都好办 */
					mSelectToggleBtn = toggleButton;
					showPopWindow();
				}
			});
			/**
			 * 点击popupwindow外部，就隐藏popupwindow,这个r是点击事件包裹了一个ExpandleItemView
			 * 如果用户所点之处为ExpandleItemView所在范围，点击事件由ExpandleItemView，如果点到
			 * ExpandleItemView外面，则有r处理，处理方式就是收缩
			 */
			r.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onPressBack();
				}

				private void onPressBack() {
					hidePopWindow();
				}
			});
		}
	}

	/**
	 * 隐藏popupWindow，并且重置ToggleButton字体颜色
	 */
	private void hidePopWindow() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
		if (mSelectToggleBtn != null) {
			mSelectToggleBtn.setTextColor(mNormalTextColor);
			mSelectToggleBtn.setChecked(false);
		}
	}

	/**
	 * 显示popupWindow
	 */
	private void showPopWindow() {
		if (null == mPopupWindow) {
			mPopupWindow = new PopupWindow(mPopupviews.get((int) mSelectToggleBtn.getTag()), mDisplayWidth,
					mDisplayHeight);
			/** 监听popupWindow的收缩,并重置字体颜色 */
			mPopupWindow.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					if (mSelectToggleBtn != null) {
						mSelectToggleBtn.setTextColor(mNormalTextColor);
						mSelectToggleBtn.setChecked(false);
					}
				}
			});
			mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
			mPopupWindow.setFocusable(true);
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		} else {
			mPopupWindow.setContentView(mPopupviews.get((int) mSelectToggleBtn.getTag()));
		}

		if (mPopupWindow.isShowing()) {
			hidePopWindow();
		} else {
			/** 显示的时候，设为选中颜色 */
			mSelectToggleBtn.setTextColor(mSelectTextColor);
			mPopupWindow.showAsDropDown(mToggleButtons.get(0), 0, 0);
		}
	}

	/**
	 * Item项选中的回调 注意Tag的使用 筛选项视图是根据tag拿的，因为mSelectToggleBtn的tag就是视图的索引
	 * mSelectToggleBtn显示筛选的内容
	 */
	@Override
	public void onItemClick(int position) {
		hidePopWindow();
		if (null != mSelectToggleBtn) {
			int selectBtnIndex = (int) mSelectToggleBtn.getTag();
			mSelectToggleBtn
					.setText(((ExpandleItemView) (((RelativeLayout) mPopupviews.get(selectBtnIndex)).getChildAt(0)))
							.getmGridviewDatas().get(position));
		}
	}

	/**
	 * 底部按钮点击的回调 mSelectToggleBtn显示筛选的内容
	 */
	@Override
	public void onBottomClick() {
		hidePopWindow();
		if (null != mSelectToggleBtn) {
			int selectBtnIndex = (int) mSelectToggleBtn.getTag();
			mSelectToggleBtn
					.setText((((ExpandleItemView) (((RelativeLayout) mPopupviews.get(selectBtnIndex)).getChildAt(0)))
							.getTitle()));
		}
	}
}
