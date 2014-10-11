package cn.bingoogolapple.loon.demo.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.bingoogolapple.loon.demo.R;
import cn.bingoogolapple.loon.library.Loon;
import cn.bingoogolapple.loon.library.LoonLayout;
import cn.bingoogolapple.loon.library.LoonView;

/**
 * Created by bingoogolapple on 14-10-11.
 */
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
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingView);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SettingView_title:
                    mTitleTv.setText(a.getText(attr));
                    break;
                case R.styleable.SettingView_content_on:
                    mContentOn = a.getText(attr);
                    break;
                case R.styleable.SettingView_content_off:
                    mContentOff = a.getText(attr);
                    mStatusIv.setImageResource(R.drawable.setting_off);
                    mContentTv.setText(mContentOff);
                    mIsChecked = false;
                    break;
            }
        }
        a.recycle();
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