:heartpulse:Loon v1.1.0:heartpulse:
====

Android自动注入注解框架

主要功能：activity、fragment、dialog、自定义组合控件中layout和view的自动注入

PS：很多注解框架都有这个功能，它们同时还包含了数据库以及网络请求等注解，但是有些项目中根本用不到数据库和网络请求，如果这时使用那些注解框架，处女座就要:angry:了

效果图
====
![Image of LoonDemo](http://bingoshare.u.qiniudn.com/LoonDemo3.gif)
![Image of LoonDemo](http://bingoshare.u.qiniudn.com/LoonDemo1.gif)
![Image of LoonDemo](http://bingoshare.u.qiniudn.com/LoonDemo2.gif)

>扫描二维码下载Demo

![Image of LoonDemo](http://bingoshare.u.qiniudn.com/LoonDemo.png)

>Gradle

```groovy
dependencies {
    compile 'cn.bingoogolapple.loon:library:1.1.0@aar'
}
```

>抽取BaseFragment

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
>这时IndicatorFragment就只需要重写processLogic方法即可

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
>在自定义组合控件中的应用

```java
@LoonLayout(id = R.layout.view_setting)
public class SettingView extends RelativeLayout implements View.OnClickListener {
    @LoonView(id = R.id.tv_view_setting_title)
    private TextView mTitleTv;
    @LoonView(id = R.id.tv_view_setting_content)
    private TextView mContentTv;
    @LoonView(id = R.id.iv_view_setting_status)
    private ImageView mStatusIv;

    private CharSequence mContentOn;
    private CharSequence mContentOff;
    private boolean mIsChecked;
    private OnToggleChangeListener mOnToggleChangeListener;

    public SettingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Loon.injectView2CustomCompositeView(this);
        initAttrs(context, attrs);
        mStatusIv.setOnClickListener(this);
    }

    public SettingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingView);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.SettingView_title:
                    mTitleTv.setText(typedArray.getText(attr));
                    break;
                case R.styleable.SettingView_content_on:
                    mContentOn = typedArray.getText(attr);
                    break;
                case R.styleable.SettingView_content_off:
                    mContentOff = typedArray.getText(attr);
                    mStatusIv.setImageResource(R.drawable.setting_off);
                    mContentTv.setText(mContentOff);
                    mIsChecked = false;
                    break;
            }
        }
        typedArray.recycle();
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
        if (mIsChecked) {
            mContentTv.setText(mContentOn);
            mStatusIv.setImageResource(R.drawable.setting_on);
        } else {
            mContentTv.setText(mContentOff);
            mStatusIv.setImageResource(R.drawable.setting_off);
        }
    }

    public void setOnToggleChangeListener(OnToggleChangeListener controlChangeListener) {
        mOnToggleChangeListener = controlChangeListener;
    }


    @Override
    public void onClick(View v) {
        if (mIsChecked) {
            if (mOnToggleChangeListener != null) {
                mOnToggleChangeListener.onClose();
            } else {
                setChecked(false);
            }
        } else {
            if (mOnToggleChangeListener != null) {
                mOnToggleChangeListener.onOpen();
            } else {
                setChecked(true);
            }
        }
    }

    public interface OnToggleChangeListener {
        public void onOpen();

        public void onClose();
    }
}
```
>在View中的应用

```java
/**
 * @param <T> 适配的数据类型
 * @param <V> ViewHolder的类型
 */
public abstract class LoonRecyclerAdapter<T, V> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected Class<V> mViewHolderClazz;
    protected AbsListView mAbsListView;

    public LoonRecyclerAdapter(AbsListView absListView, Class<V> viewHolderClazz) {
        this(absListView, viewHolderClazz, null);
    }

    public LoonRecyclerAdapter(AbsListView absListView, Class<V> viewHolderClazz, List<T> datas) {
        mViewHolderClazz = viewHolderClazz;
        mAbsListView = absListView;
        mDatas = datas;
        mContext = absListView.getContext();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            V viewHolder = null;
            try {
                viewHolder = mViewHolderClazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(mViewHolderClazz.getName() + "没有空的构造方法");
            }
            convertView = Loon.injectView2ViewHolderOrFragment(viewHolder, mContext);
            convertView.setTag(viewHolder);
        }
        initViewHolder((V) convertView.getTag(), mDatas.get(position));
        return convertView;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void reloadItem(int position) {
        if (mDatas == null) {
            return;
        }
        getView(position, mAbsListView.getChildAt(position), mAbsListView);
    }

    protected abstract void initViewHolder(V viewHolder, T listItem);

}
```
```java
@LoonLayout(id = R.layout.item_two)
public static class ViewHolder {
    @LoonView(id = R.id.iv_item_two_icon)
    private ImageView iconIv;
    @LoonView(id = R.id.tv_item_two_name)
    private TextView nameTv;
}

private class MyAdapter extends LoonRecyclerAdapter<User,ViewHolder> {

    public MyAdapter(AbsListView absListView, Class<ViewHolder> viewHolderClazz) {
        super(absListView, viewHolderClazz);
    }

    @Override
    protected void initViewHolder(ViewHolder viewHolder, User listItem) {
        viewHolder.iconIv.setImageResource(listItem.icon);
        viewHolder.nameTv.setText(listItem.name);
    }
};
```

##### 详细用法请查看[demo](https://github.com/bingoogolapple/Loon/tree/master/demo):feet: