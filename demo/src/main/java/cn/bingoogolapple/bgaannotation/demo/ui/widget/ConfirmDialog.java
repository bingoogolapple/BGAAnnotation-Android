package cn.bingoogolapple.bgaannotation.demo.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.bingoogolapple.bgaannotation.BGAALayout;
import cn.bingoogolapple.bgaannotation.BGAAView;
import cn.bingoogolapple.bgaannotation.demo.R;

@BGAALayout(R.layout.dialog_confirm)
public class ConfirmDialog extends BaseDialog {
    @BGAAView(R.id.tv_dialog_title)
    private TextView mTitleTv;
    @BGAAView(R.id.tv_dialog_msg)
    private TextView mMsgTv;
    @BGAAView(R.id.btn_dialog_negative)
    private Button mNegativeBtn;
    @BGAAView(R.id.btn_dialog_positive)
    private Button mPositiveBtn;

    private DialogDelegate mDelegate;
    private String mTitle;
    private String mMsg;
    private String mPositive;
    private String mNegative;

    public ConfirmDialog(Context context) {
        super(context, R.style.BGA_Dialog);
    }

    @Override
    protected void setListener() {
        mPositiveBtn.setOnClickListener(this);
        mNegativeBtn.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        this.setCancelable(false);
    }

    public void setData(String title, String msg, String negative, String positive,DialogDelegate delegate) {
        mTitle = title;
        mMsg = msg;
        mPositive = positive;
        mNegative = negative;
        mDelegate = delegate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_negative:
                mDelegate.onNegative();
                break;
            case R.id.btn_dialog_positive:
                mDelegate.onPositive();
                break;
        }
        this.dismiss();
    }

    @Override
    public void show() {
        super.show();
        if (!TextUtils.isEmpty(mTitle)) {
            mTitleTv.setText(mTitle);
        }
        mMsgTv.setText(mMsg);
        mPositiveBtn.setText(mPositive);
        mNegativeBtn.setText(mNegative);
    }
}
