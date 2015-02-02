package cn.bingoogolapple.loonannotation.library;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public class Loon {
    private static final String LAYOUT_RES_ID_TIP = "请指定正确的布局文件资源id";
    private static final String VIEW_RES_ID_TIP = "请指定正确的控件资源id";

    private Loon() {
    }

    /**
     * 注入控件属性，并返回这些控件属性的父视图
     *
     * @param obj     包含控件属性的实例对象（例如ViewHolder或者Fragment）
     * @param context 应用程序上下文
     * @return 返回父视图（例如ListView优化中的convertView或者Fragment中onCreateView方法返回的视图）
     */
    public static View injectView2ViewHolderOrFragment(Object obj, Context context) {
        View parent = initParentView(obj, context);
        injectViewField2Obj(obj, parent);
        return parent;
    }

    /**
     * 注入activity的控件属性
     *
     * @param activity activity实例对象
     */
    public static void injectView2Activity(Activity activity) {
        View parent = initParentView(activity, activity);
        injectViewField2Obj(activity, parent);
        activity.setContentView(parent);
    }

    /**
     * 注入自定义对话框的控件属性
     *
     * @param dialog 自定义对话框实例对象
     */
    public static void injectView2Dialog(Dialog dialog) {
        LoonLayout loonLayout = dialog.getClass().getAnnotation(LoonLayout.class);
        if (loonLayout != null) {
            // dialog要指定布局id,否则指定layout_width不生效
            dialog.setContentView(loonLayout.value());
            injectViewField2Obj(dialog, dialog.getWindow().getDecorView());
        } else {
            throw new RuntimeException(LAYOUT_RES_ID_TIP);
        }

    }

    /**
     * 注入自定义组合控件的控件属性
     *
     * @param parent 自定义组合控件的实例对象
     */
    public static void injectView2CustomCompositeView(ViewGroup parent) {
        LoonLayout loonLayout = parent.getClass().getAnnotation(LoonLayout.class);
        if (loonLayout != null) {
            // 这里需要追加到父视图
            View.inflate(parent.getContext(), loonLayout.value(), parent);
        } else {
            throw new RuntimeException(LAYOUT_RES_ID_TIP);
        }
        injectViewField2Obj(parent, parent);
    }

    /**
     * 初始化父视图
     *
     * @param obj     有LoonLayout注解的实例对象
     * @param context 应用程序上下文
     * @return 返回父视图
     */
    private static View initParentView(Object obj, Context context) {
        LoonLayout loonLayout = obj.getClass().getAnnotation(LoonLayout.class);
        if (loonLayout != null) {
            // 这里不用追加到父视图，所以第三个root参数指定为nul
            return View.inflate(context, loonLayout.value(), null);
        } else {
            throw new RuntimeException(LAYOUT_RES_ID_TIP);
        }
    }

    /**
     * 注入控件属性
     *
     * @param obj    带有控件属性的实例对象
     * @param parent 控件属性的父视图
     */
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
                        // 上面已经设置过可以访问了，不可达
                    }
                } else {
                    throw new RuntimeException(VIEW_RES_ID_TIP);
                }
            }
        }
    }

}