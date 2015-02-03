package cn.bingoogolapple.loonannotation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public class Loon {
    private static final String LAYOUT_RES_ID_TIP = "LAYOUT_RES_ID NOT CORRECT";
    private static final String VIEW_RES_ID_TIP = "VIEW_RES_ID NOT CORRECT";

    private Loon() {
    }

    public static View injectView2ViewHolderOrFragment(Object obj, Context context) {
        View parent = initParentView(obj, context);
        injectViewField2Obj(obj, parent);
        return parent;
    }

    public static void injectView2Activity(Activity activity) {
        View parent = initParentView(activity, activity);
        injectViewField2Obj(activity, parent);
        activity.setContentView(parent);
    }

    public static void injectView2Dialog(Dialog dialog) {
        LoonLayout loonLayout = dialog.getClass().getAnnotation(LoonLayout.class);
        if (loonLayout != null) {
            dialog.setContentView(loonLayout.value());
            injectViewField2Obj(dialog, dialog.getWindow().getDecorView());
        } else {
            throw new RuntimeException(LAYOUT_RES_ID_TIP);
        }

    }

    public static void injectView2CustomCompositeView(ViewGroup parent) {
        LoonLayout loonLayout = parent.getClass().getAnnotation(LoonLayout.class);
        if (loonLayout != null) {
            View.inflate(parent.getContext(), loonLayout.value(), parent);
        } else {
            throw new RuntimeException(LAYOUT_RES_ID_TIP);
        }
        injectViewField2Obj(parent, parent);
    }

    private static View initParentView(Object obj, Context context) {
        LoonLayout loonLayout = obj.getClass().getAnnotation(LoonLayout.class);
        if (loonLayout != null) {
            return View.inflate(context, loonLayout.value(), null);
        } else {
            throw new RuntimeException(LAYOUT_RES_ID_TIP);
        }
    }

    public static void injectViewField2Obj(Object obj, View parent) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            LoonView loonView = field.getAnnotation(LoonView.class);
            if (loonView != null) {
                if (loonView.value() != 0) {
                    try {
                        field.set(obj, parent.findViewById(loonView.value()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new RuntimeException(VIEW_RES_ID_TIP);
                }
            }
        }
    }

}