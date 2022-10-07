package com.example.testthread.utils;

import android.util.Log;

public class TLog {
    private static String TAG = "TAG";

    public static void e(String msg){
        Log.e(TAG, msg);
    }
}
