package com.csi0n.searchjobapp.core.log;

import android.util.Log;

/**
 * Created by chqss on 2016/4/29 0029.
 */
public class CLog {
    static final String SEARCH_JOB="SearchJob";
    public static int v(String msg, Object... args) {
        if (args != null && args.length != 0) {
            msg = String.format(msg, args);
        }
        return Log.v(SEARCH_JOB, msg);
    }

    public static int d(String msg, Object... args) {
        if (args != null && args.length != 0) {
            msg = String.format(msg, args);
        }
        return Log.d(SEARCH_JOB, msg);
    }

    public static int i(String msg, Object... args) {
        if (args != null && args.length != 0) {
            msg = String.format(msg, args);
        }
        return Log.i(SEARCH_JOB, msg);
    }

    public static int w(String msg, Object... args) {
        if (args != null && args.length != 0) {
            msg = String.format(msg, args);
        }
        return Log.w(SEARCH_JOB, msg);
    }

    public static int w(Throwable tr) {
        return Log.w(SEARCH_JOB, tr);
    }

    public static int e(String msg, Object... args) {
        if (args != null && args.length != 0) {
            msg = String.format(msg, args);
        }
        return Log.e(SEARCH_JOB, msg);
    }

    public static int e(String msg, Throwable tr) {
        return Log.e(SEARCH_JOB, msg, tr);
    }
}
