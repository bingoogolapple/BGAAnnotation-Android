package cn.bingoogolapple.bgaannotation.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.widget.RadioGroup;

import cn.bingoogolapple.bgaannotation.BGAALayout;
import cn.bingoogolapple.bgaannotation.BGAAView;
import cn.bingoogolapple.bgaannotation.demo.R;
import cn.bingoogolapple.bgaannotation.demo.ui.fragment.IndicatorFragment;
import cn.bingoogolapple.bgaannotation.demo.ui.fragment.NestFragment;
import cn.bingoogolapple.bgaannotation.demo.ui.fragment.OneFragment;
import cn.bingoogolapple.bgaannotation.demo.ui.fragment.TwoFragment;

@BGAALayout(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private enum TabTag {
        OneTab, TwoTab, ThreeTab, FourTab
    }

    private TabTag mCurrentTab = TabTag.OneTab;

    @BGAAView(android.R.id.tabhost)
    private FragmentTabHost mTabHost;
    @BGAAView(R.id.rg_main_tab)
    private RadioGroup mTabRg;

    @Override
    protected void setListener() {
        mTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_one:
                        changeTab(TabTag.OneTab);
                        break;
                    case R.id.rb_main_two:
                        changeTab(TabTag.TwoTab);
                        break;
                    case R.id.rb_main_three:
                        changeTab(TabTag.ThreeTab);
                        break;
                    case R.id.rb_main_nest:
                        changeTab(TabTag.FourTab);
                        break;
                }
            }

            private void changeTab(TabTag newTab) {
                if (!newTab.equals(mCurrentTab)) {
                    mTabHost.setCurrentTabByTag(newTab.name());
                    mCurrentTab = newTab;
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        initTab();
        if (savedInstanceState != null) {
            mCurrentTab = TabTag.valueOf(savedInstanceState.getString("mCurrentTab"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mCurrentTab", mCurrentTab.name());
    }

    private void initTab() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(mTabHost.newTabSpec(TabTag.OneTab.name()).setIndicator(""), OneFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TabTag.TwoTab.name()).setIndicator(""), TwoFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TabTag.ThreeTab.name()).setIndicator(""), IndicatorFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TabTag.FourTab.name()).setIndicator(""), NestFragment.class, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        changeTabByTag(mCurrentTab);
    }

    private void changeTabByTag(TabTag newTab) {
        switch (newTab) {
            case OneTab:
                mTabRg.check(R.id.rb_main_one);
                break;
            case TwoTab:
                mTabRg.check(R.id.rb_main_two);
                break;
            case ThreeTab:
                mTabRg.check(R.id.rb_main_three);
                break;
            case FourTab:
                mTabRg.check(R.id.rb_main_nest);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        mApp.exitWithDoubleClick();
    }
}