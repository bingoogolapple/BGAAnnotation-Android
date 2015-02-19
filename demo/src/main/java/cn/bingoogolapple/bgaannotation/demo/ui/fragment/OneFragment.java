package cn.bingoogolapple.bgaannotation.demo.ui.fragment;

import android.os.Bundle;
import android.view.View;

import cn.bingoogolapple.bgaannotation.BGAALayout;
import cn.bingoogolapple.bgaannotation.BGAAView;
import cn.bingoogolapple.bgaannotation.demo.R;
import cn.bingoogolapple.bgaannotation.demo.ui.widget.ConfirmDialog;
import cn.bingoogolapple.bgaannotation.demo.ui.widget.DataLoadDialog;
import cn.bingoogolapple.bgaannotation.demo.ui.widget.DialogDelegate;
import cn.bingoogolapple.bgaannotation.demo.ui.widget.SettingView;
import cn.bingoogolapple.bgaannotation.demo.util.ToastUtil;


@BGAALayout(R.layout.fragment_one)
public class OneFragment extends BaseFragment {
    private DataLoadDialog mDataLoadDialog;
    private ConfirmDialog mConfirmDialog;
    @BGAAView(R.id.sv_one_bluetooth)
    private SettingView mBluetoothSv;

    @Override
    protected void setListener() {
        mRootView.findViewById(R.id.btn_one_dataload).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_one_confirm).setOnClickListener(this);
        mBluetoothSv.setOnToggleChangeListener(new SettingView.OnToggleChangeListener() {
            @Override
            public void onOpen() {
                ToastUtil.makeText("打开蓝牙功能");
                mBluetoothSv.setChecked(true);
            }

            @Override
            public void onClose() {
                ToastUtil.makeText("关闭蓝牙功能");
                mBluetoothSv.setChecked(false);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mDataLoadDialog = new DataLoadDialog(getActivity());
        mConfirmDialog = new ConfirmDialog(getActivity());
    }

    public void showDataLoadDialog(String msg, boolean cancelable) {
        mDataLoadDialog.setMsg(msg);
        mDataLoadDialog.setCancelable(cancelable);
        mDataLoadDialog.show();
    }

    public void showConfirmDialog() {
        mConfirmDialog.setData("操作提示","我是消息","取消","确定",new DialogDelegate() {
            @Override
            public void onNegative() {
                ToastUtil.makeText("取消");
            }

            @Override
            public void onPositive() {
                ToastUtil.makeText("确定");
            }
        });
        mConfirmDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one_dataload:
                showDataLoadDialog("哈哈",true);
                break;
            case R.id.btn_one_confirm:
                showConfirmDialog();
                break;

        }
    }
}
