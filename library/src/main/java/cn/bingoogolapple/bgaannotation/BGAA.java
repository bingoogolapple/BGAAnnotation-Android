package cn.bingoogolapple.bgaannotation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public class BGAA {
    private static final String LAYOUT_RES_ID_TIP = "LAYOUT_RES_ID NOT CORRECT";
    private static final String VIEW_RES_ID_TIP = "VIEW_RES_ID NOT CORRECT";

    private BGAA() {
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
        BGAALayout bgaaLayout = dialog.getClass().getAnnotation(BGAALayout.class);
        if (bgaaLayout != null) {
            dialog.setContentView(bgaaLayout.value());
            injectViewField2Obj(dialog, dialog.getWindow().getDecorView());
        } else {
            throw new RuntimeException(LAYOUT_RES_ID_TIP);
        }

    }

    public static void injectView2CustomCompositeView(ViewGroup parent) {
        BGAALayout BGAALayout = parent.getClass().getAnnotation(BGAALayout.class);
        if (BGAALayout != null) {
            View.inflate(parent.getContext(), BGAALayout.value(), parent);
        } else {
            throw new RuntimeException(LAYOUT_RES_ID_TIP);
        }
        injectViewField2Obj(parent, parent);
    }

    private static View initParentView(Object obj, Context context) {
        BGAALayout BGAALayout = obj.getClass().getAnnotation(BGAALayout.class);
        if (BGAALayout != null) {
            return View.inflate(context, BGAALayout.value(), null);
        } else {
            throw new RuntimeException(LAYOUT_RES_ID_TIP);
        }
    }

    public static void injectViewField2Obj(Object obj, View parent) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            BGAAView bgaaView = field.getAnnotation(BGAAView.class);
            if (bgaaView != null) {
                if (bgaaView.value() != 0) {
                    try {
                        field.set(obj, parent.findViewById(bgaaView.value()));
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