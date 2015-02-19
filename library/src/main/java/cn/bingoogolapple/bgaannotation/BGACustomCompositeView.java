package cn.bingoogolapple.bgaannotation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public abstract class BGACustomCompositeView extends RelativeLayout {

    public BGACustomCompositeView(Context context) {
        this(context, null);
    }

    public BGACustomCompositeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BGACustomCompositeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        BGAA.injectView2CustomCompositeView(this);
        initAttrs(context, attrs);
        setListener();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, getAttrs());
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    protected abstract int[] getAttrs();

    protected abstract void initAttr(int attr, TypedArray typedArray);

    protected void setListener() {
    }
}