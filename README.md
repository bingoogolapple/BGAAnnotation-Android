Loon
====

Android Annotation

![Image of LoonDemo](http://bingoshare.u.qiniudn.com/LoonDemo.gif)

>扫描二维码下载Demo

![Image of LoonDemo](http://bingoshare.u.qiniudn.com/LoonDemo.png)


>BaseFragment.java

```java
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected App mApp;
    protected LocalBroadcastManager mLocalBroadcastManager;
    protected View mRootView;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mApp = App.getInstance();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(activity);
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            injectView();
            setListener();
            processLogic(savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    protected void injectView() {
        mRootView = Loon.injectView2ViewHolderOrFragment(this,mApp);
    }

    protected void setListener() {
    }

    protected abstract void processLogic(Bundle savedInstanceState);

    @Override
    public void onClick(View v) {
    };

}
```
>IndicatorFragment.java

```java
@LoonLayout(id = R.layout.fragment_indicator)
public class IndicatorFragment extends BaseFragment {
    @LoonView(id = R.id.indicator1)
    private BGAIndicator mIndicator1;
    @LoonView(id = R.id.pager1)
    private ViewPager mPager1;

    @LoonView(id = R.id.indicator2)
    private BGAIndicator mIndicator2;
    @LoonView(id = R.id.pager2)
    private ViewPager mPager2;

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        List<BGAIndicator.TabInfo> mTabInfos = new ArrayList<BGAIndicator.TabInfo>();
        mTabInfos.add(new BGAIndicator.TabInfo("选项一",NestFragment.NextOneFragment.class.getName()));
        mTabInfos.add(new BGAIndicator.TabInfo("选项二",NestFragment.NextTwoFragment.class.getName()));
        mTabInfos.add(new BGAIndicator.TabInfo("选项三",NestFragment.NextThreeFragment.class.getName()));
        mIndicator1.initData(0,mTabInfos,mPager1,getChildFragmentManager());
        mIndicator2.initData(1,mTabInfos,mPager2,getChildFragmentManager());
    }
}
```