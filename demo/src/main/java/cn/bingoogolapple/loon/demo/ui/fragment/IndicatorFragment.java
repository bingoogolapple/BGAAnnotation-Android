package cn.bingoogolapple.loon.demo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.loon.demo.R;
import cn.bingoogolapple.loon.demo.ui.view.BGAIndicator;
import cn.bingoogolapple.loon.library.LoonLayout;
import cn.bingoogolapple.loon.library.LoonView;

/**
 * Created by bingoogolapple on 14-10-10.
 */
@LoonLayout(id = R.layout.fragment_indicator)
public class IndicatorFragment extends BaseFragment {
    @LoonView(id = R.id.indicator)
    private BGAIndicator mIndicator;
    @LoonView(id = R.id.pager)
    private ViewPager mPager;
    private List<BGAIndicator.TabInfo> mTabInfos;

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mTabInfos = new ArrayList<BGAIndicator.TabInfo>();
        mTabInfos.add(new BGAIndicator.TabInfo("选项一",NestFragment.NextOneFragment.class.getName()));
        mTabInfos.add(new BGAIndicator.TabInfo("选项二",NestFragment.NextTwoFragment.class.getName()));
        mTabInfos.add(new BGAIndicator.TabInfo("选项三",NestFragment.NextThreeFragment.class.getName()));
        mIndicator.initData(3,mTabInfos,mPager,getChildFragmentManager());
    }
}
