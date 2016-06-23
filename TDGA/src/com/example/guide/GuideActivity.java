package com.example.guide;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.ViewPagerAdapter;
import com.example.fragment.Fragment1;
import com.example.fragment.Fragment2;
import com.example.fragment.Fragment3;
import com.example.fragment.Fragment4;
import com.example.tieda.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 
 * Òýµ¼Ò³
 * 
 * @author ck
 * @version V1.0
 *
 */
public class GuideActivity extends FragmentActivity {

	private ViewPager viewPage;
	private Fragment1 mFragment1;
	private Fragment2 mFragment2;
	private Fragment3 mFragment3;
	private Fragment4 mFragment4;
	private PagerAdapter mPgAdapter;
	private RadioGroup dotLayout;
	private List<Fragment> mListFragment = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		initView();
		viewPage.setOnPageChangeListener(new MyPagerChangeListener());

	}

	private void initView() {
		dotLayout = (RadioGroup) findViewById(R.id.advertise_point_group);
		viewPage = (ViewPager) findViewById(R.id.viewpager);
		mFragment1 = new Fragment1();
		mFragment2 = new Fragment2();
		mFragment3 = new Fragment3();
		mFragment4 = new Fragment4();
		mListFragment.add(mFragment1);
		mListFragment.add(mFragment2);
		mListFragment.add(mFragment3);
		mListFragment.add(mFragment4);
		mPgAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
				mListFragment);
		viewPage.setAdapter(mPgAdapter);

	}

	public class MyPagerChangeListener implements OnPageChangeListener {

		public void onPageSelected(int position) {

		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			((RadioButton) dotLayout.getChildAt(position)).setChecked(true);
		}

	}
}
