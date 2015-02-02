package cn.bingoogolapple.loonannotation.library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.List;

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
        initViewHolder((V) convertView.getTag(), mDatas.get(position), position);
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

    protected abstract void initViewHolder(V viewHolder, T listItem, int position);

}