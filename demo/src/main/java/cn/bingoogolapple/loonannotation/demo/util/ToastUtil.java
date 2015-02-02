package cn.bingoogolapple.loonannotation.demo.util;

import android.widget.Toast;

import cn.bingoogolapple.loonannotation.demo.App;


/**
 * Created by bingoogolapple on 14-10-10.
 */
public class ToastUtil {
    private ToastUtil() {
    }

    public static void makeText(CharSequence text) {
        if (text.length() < 10) {
            Toast.makeText(App.getInstance(), text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(App.getInstance(), text, Toast.LENGTH_LONG).show();
        }
    }

    public static void makeText(int resId) {
        makeText(App.getInstance().getResources().getString(resId));
    }
}