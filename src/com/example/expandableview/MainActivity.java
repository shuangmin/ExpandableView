package com.example.expandableview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private ExpandableView mExpandableView;
	private Map<String, ExpandleItemView> mExpandleItemViews;
	private String[] mColleages = { "A学校", "B学校", "C学校", "D学校", "E学校", "F学校", "G学校", "H学校", "I学校", "J学校" };
	private String[] mDepartments = { "A系", "B系", "C系", "D系", "E系", "F系", "G系", "H系" , "I系" };
	private String[] mProfessions = { "A专业", "B专业", "C专业" , "D专业" , "E专业" , "F专业" };
	private String[] mClasses = { "A班", "B班", "C班", "D班" , "E班" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mExpandableView = (ExpandableView) findViewById(R.id.expandview);
		mExpandleItemViews = new LinkedHashMap<>();
		//不要问我为什么这么用，因为我想用LinkedHashMap
		mExpandleItemViews.put("大学", new ExpandleItemView("大学", this, Arrays.asList(mColleages)));
		mExpandleItemViews.put("院系", new ExpandleItemView("院系", this, Arrays.asList(mDepartments)));
		mExpandleItemViews.put("专业", new ExpandleItemView("专业", this, Arrays.asList(mProfessions)));
		mExpandleItemViews.put("班级", new ExpandleItemView("班级", this, Arrays.asList(mClasses)));
		mExpandableView.initViews(new ArrayList<>(mExpandleItemViews.values()));
	}
}
