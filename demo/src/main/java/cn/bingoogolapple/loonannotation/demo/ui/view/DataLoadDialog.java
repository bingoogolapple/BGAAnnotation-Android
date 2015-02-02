package cn.bingoogolapple.loonannotation.demo.ui.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bingoogolapple.loonannotation.demo.R;
import cn.bingoogolapple.loonannotation.library.LoonLayout;
import cn.bingoogolapple.loonannotation.library.LoonView;

/**
 * Created by bingoogolapple on 14-10-10.
 */
@LoonLayout(R.layout.dialog_dataload)
public class DataLoadDialog extends BaseDialog {
    @LoonView(R.id.iv_dataload_progress)
    private ImageView mProgressIv;
    @LoonView(R.id.tv_dialog_msg)
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