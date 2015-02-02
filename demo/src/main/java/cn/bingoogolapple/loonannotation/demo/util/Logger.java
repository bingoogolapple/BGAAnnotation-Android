package cn.bingoogolapple.loonannotation.demo.util;

import android.util.Log;

/**
 * Created by bingoogolapple on 14-10-10.
 */
public class Logger {
    private Logger() {
    }

    private static int LOGLEVEL = 6;
    private static int ERROR = 1;
    private static int WARN = 2;
    private static int INFO = 3;
    private static int DEBUG = 4;
    private static int VERBOSE = 5;

    public static void v(String tag, String msg) {
        if (LOGLEVEL > VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOGLEVEL > DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LOGLEVEL > INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOGLEVEL > WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOGLEVEL > ERROR) {
            Log.e(tag, msg);
        }
    }
}