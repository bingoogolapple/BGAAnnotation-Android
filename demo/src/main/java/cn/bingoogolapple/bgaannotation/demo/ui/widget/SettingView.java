package cn.bingoogolapple.bgaannotation.demo.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bingoogolapple.bgaannotation.BGAALayout;
import cn.bingoogolapple.bgaannotation.BGAAView;
import cn.bingoogolapple.bgaannotation.BGACustomCompositeView;
import cn.bingoogolapple.bgaannotation.demo.R;

@BGAALayout(R.layout.view_setting)
public class SettingView extends BGACustomCompositeView implements View.OnClickListener {
    @BGAAView(R.id.tv_view_setting_title)
    private TextView mTitleTv;
    @BGAAView(R.id.tv_view_setting_content)
    private TextView mContentTv;
    @BGAAView(R.id.iv_view_setting_status)
    private ImageView mStatusIv;

    private CharSequence mContentOn;
    private CharSequence mContentOff;
    private boolean mIsChecked = false;
    private OnToggleChangeListener mOnToggleChangeListener;

    public SettingView(Context context) {
        this(context, null);
    }

    public SettingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int[] getAttrs() {
        return R.styleable.SettingView;
    }

    @Override
    public void initAttr(int attr, TypedArray typedArray) {
        switch (attr) {
            case R.styleable.SettingView_sv_title:
                mTitleTv.setText(typedArray.getText(attr));
                break;
            case R.styleable.SettingView_content_on:
                mContentOn = typedArray.getText(attr);
                break;
            case R.styleable.SettingView_content_off:
                mContentOff = typedArray.getText(attr);
                mStatusIv.setImageResource(R.drawable.apple_toggle_off);
                mContentTv.setText(mContentOff);
                mIsChecked = false;
                break;
        }
    }

    @Override
    protected void setListener() {
        mStatusIv.setOnClickListener(this);
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
        if (mIsChecked) {
            mContentTv.setText(mContentOn);
            mStatusIv.setImageResource(R.drawable.apple_toggle_on);
        } else {
            mContentTv.setText(mContentOff);
            mStatusIv.setImageResource(R.drawable.apple_toggle_off);
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