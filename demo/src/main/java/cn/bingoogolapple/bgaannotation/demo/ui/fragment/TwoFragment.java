package cn.bingoogolapple.bgaannotation.demo.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgaannotation.BGAALayout;
import cn.bingoogolapple.bgaannotation.BGAAView;
import cn.bingoogolapple.bgaannotation.BGAAbsListViewAdapter;
import cn.bingoogolapple.bgaannotation.demo.R;
import cn.bingoogolapple.bgaannotation.demo.ui.model.User;
import cn.bingoogolapple.bgaannotation.demo.ui.widget.DataLoadDialog;

@BGAALayout(R.layout.fragment_two)
public class TwoFragment extends BaseFragment {

    @BGAAView(R.id.srl_two_container)
    private SwipeRefreshLayout mContainerSrl;
    @BGAAView(android.R.id.list)
    private ListView mList;
    @BGAAView(R.id.btn_two_update)
    private Button mUpdateBtn;

    private List<User> mDatas;
    private MyAdapter mMyAdapter;

    private DataLoadDialog mDataLoadDialog;

    @Override
    protected void setListener() {
        mContainerSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        showDataLoadDialog("正在加载数据", false);
                    }

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mDatas.add(new User("name" + mDatas.size(), R.drawable.ic_launcher));
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        mDataLoadDialog.dismiss();
                        mContainerSrl.setRefreshing(false);
                        mMyAdapter.notifyDataSetChanged();
                    }
                }.execute();
            }
        });
        mUpdateBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mDatas.get(1).name = "修改后的名字";
        mMyAdapter.reloadItem(1);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mDataLoadDialog = new DataLoadDialog(getActivity());
        mContainerSrl.setColorSchemeResources(R.color.srl_blue_bright, R.color.srl_green_light, R.color.srl_orange_light, R.color.srl_red_light);
        mDatas = new ArrayList<User>();
        for (int i = 0; i < 5; i++) {
            mDatas.add(new User("name" + i, R.drawable.ic_launcher));
        }
        mMyAdapter = new MyAdapter(mList, ViewHolder.class);
        mMyAdapter.setDatas(mDatas);
        mList.setAdapter(mMyAdapter);
    }

    public void showDataLoadDialog(String msg, boolean cancelable) {
        mDataLoadDialog.setMsg(msg);
        mDataLoadDialog.setCancelable(cancelable);
        mDataLoadDialog.show();
    }

    @BGAALayout(R.layout.item_two)
    public static class ViewHolder {
        @BGAAView(R.id.iv_item_two_icon)
        private ImageView iconIv;
        @BGAAView(R.id.tv_item_two_name)
        private TextView nameTv;
    }

    private class MyAdapter extends BGAAbsListViewAdapter<User, ViewHolder> {

        public MyAdapter(AbsListView absListView, Class<ViewHolder> viewHolderClazz) {
            super(absListView, viewHolderClazz);
        }

        @Override
        protected void initViewHolder(ViewHolder viewHolder, User listItem, int position) {
            viewHolder.iconIv.setImageResource(listItem.icon);
            viewHolder.nameTv.setText(listItem.name);
        }
    }

}
