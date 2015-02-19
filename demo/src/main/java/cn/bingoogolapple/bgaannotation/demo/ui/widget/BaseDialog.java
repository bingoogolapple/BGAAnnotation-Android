package cn.bingoogolapple.bgaannotation.demo.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import cn.bingoogolapple.bgaannotation.BGAA;

public abstract class BaseDialog extends Dialog implements View.OnClickListener {

    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectView();
        setListener();
        processLogic(savedInstanceState);
    }

    protected void injectView() {
        BGAA.injectView2Dialog(this);
    }

    protected void setListener() {
    }

    protected abstract void processLogic(Bundle savedInstanceState);

    @Override
    public void onClick(View v) {
    }
}