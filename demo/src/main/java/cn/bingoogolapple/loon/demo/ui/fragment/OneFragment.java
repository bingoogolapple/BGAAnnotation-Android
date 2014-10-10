package cn.bingoogolapple.loon.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;

import cn.bingoogolapple.loon.demo.R;
import cn.bingoogolapple.loon.demo.ui.view.DataLoadDialog;
import cn.bingoogolapple.loon.library.LoonLayout;

/**
 * Created by bingoogolapple on 14-10-10.
 */
@LoonLayout(id = R.layout.fragment_one)
public class OneFragment extends BaseFragment {
    private DataLoadDialog mDataLoadDialog;
    @Override
    protected void setListener() {
        mRootView.findViewById(R.id.btn_one_dataload).setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mDataLoadDialog = new DataLoadDialog(getActivity());
    }

    public void showDataLoadDialog(String msg, boolean cancelable) {
        mDataLoadDialog.setMsg(msg);
        mDataLoadDialog.setCancelable(cancelable);
        mDataLoadDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one_dataload:
                showDataLoadDialog("哈哈",true);
                break;
        }
    }
}
