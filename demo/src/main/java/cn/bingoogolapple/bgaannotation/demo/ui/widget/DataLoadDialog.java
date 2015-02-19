package cn.bingoogolapple.bgaannotation.demo.ui.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bingoogolapple.bgaannotation.BGAALayout;
import cn.bingoogolapple.bgaannotation.BGAAView;
import cn.bingoogolapple.bgaannotation.demo.R;

@BGAALayout(R.layout.dialog_dataload)
public class DataLoadDialog extends BaseDialog {
    @BGAAView(R.id.iv_dataload_progress)
    private ImageView mProgressIv;
    @BGAAView(R.id.tv_dialog_msg)
    private TextView mMsgTv;
    private String mMsg;
    private AnimationDrawable mAd;

    public DataLoadDialog(Context context) {
        super(context, R.style.BGA_Dialog);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mAd = (AnimationDrawable) mProgressIv.getDrawable();
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    @Override
    public void show() {
        super.show();
        mMsgTv.setText(mMsg);
        mAd.start();
    }

}