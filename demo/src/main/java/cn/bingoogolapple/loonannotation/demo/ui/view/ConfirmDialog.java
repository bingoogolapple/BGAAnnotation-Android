package cn.bingoogolapple.loonannotation.demo.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.bingoogolapple.loonannotation.demo.R;
import cn.bingoogolapple.loonannotation.library.LoonLayout;
import cn.bingoogolapple.loonannotation.library.LoonView;

/**
 * Created by bingoogolapple on 14-10-10.
 */
@LoonLayout(R.layout.dialog_confirm)
public class ConfirmDialog extends BaseDialog {
    @LoonView(R.id.tv_dialog_title)
    private TextView mTitleTv;
    @LoonView(R.id.tv_dialog_msg)
    private TextView mMsgTv;
    @LoonView(R.id.btn_dialog_negative)
    private Button mNegativeBtn;
    @LoonView(R.id.btn_dialog_positive)
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
