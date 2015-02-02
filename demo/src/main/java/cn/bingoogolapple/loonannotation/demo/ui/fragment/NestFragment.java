package cn.bingoogolapple.loonannotation.demo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.bingoogolapple.loonannotation.demo.R;
import cn.bingoogolapple.loonannotation.demo.util.Logger;
import cn.bingoogolapple.loonannotation.library.LoonLayout;
import cn.bingoogolapple.loonannotation.library.LoonView;

@LoonLayout(R.layout.fragment_nest)
public class NestFragment extends BaseFragment {
    private static final String TAG = NestFragment.class.getSimpleName();
    private final String[] FRAGMENTS = { NextOneFragment.class.getName(), NextTwoFragment.class.getName(),NextThreeFragment.class.getName() };
    private enum TabTag {
        OneTab,TwoTab,ThreeTab
    }
    private TabTag mCurrentTab = TabTag.OneTab;
    @LoonView(R.id.rg_nest_tab)
    private RadioGroup mTabRg;
    @LoonView(R.id.vp_nest_pager)
    private ViewPager mPagerVp;
    private MyPagerAdapter mPagerAdapter;

    @Override
    protected void setListener() {
        mTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_nest_one:
                        changeTab(TabTag.OneTab);
                        break;
                    case R.id.rb_nest_two:
                        changeTab(TabTag.TwoTab);
                        break;
                    case R.id.rb_nest_three:
                        changeTab(TabTag.ThreeTab);
                        break;
                }
            }

            private void changeTab(TabTag newTab) {
                if (!newTab.equals(mCurrentTab)) {
                    Logger.i(TAG,"new ordinal " + newTab.ordinal());
                    mPagerVp.setCurrentItem(newTab.ordinal());
                }
            }
        });
        mPagerVp.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentTab = TabTag.values()[position];
                refreshTab();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        mPagerVp.setAdapter(mPagerAdapter);
        if (savedInstanceState != null) {
            mCurrentTab = TabTag.valueOf(savedInstanceState.getString("mCurrentTab"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mCurrentTab",mCurrentTab.name());
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshTab();
    }

    private void refreshTab() {
        switch (mCurrentTab) {
            case OneTab:
                ((RadioButton) mTabRg.findViewById(R.id.rb_nest_one)).setChecked(true);
                break;
            case TwoTab:
                ((RadioButton) mTabRg.findViewById(R.id.rb_nest_two)).setChecked(true);
                break;
            case ThreeTab:
                ((RadioButton) mTabRg.findViewById(R.id.rb_nest_three)).setChecked(true);
                break;
        }
    }

    @LoonLayout(R.layout.fragment_nest_one)
    public static class NextOneFragment extends BaseFragment {
        @Override
        protected void processLogic(Bundle savedInstanceState) {

        }
    }

    @LoonLayout(R.layout.fragment_nest_two)
    public static class NextTwoFragment extends BaseFragment {
        @Override
        protected void processLogic(Bundle savedInstanceState) {

        }
    }

    @LoonLayout(R.layout.fragment_nest_three)
    public static class NextThreeFragment extends BaseFragment {
        @Override
        protected void processLogic(Bundle savedInstanceState) {

        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return FRAGMENTS.length;
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment.instantiate(mApp, FRAGMENTS[position]);
        }

    }

}
