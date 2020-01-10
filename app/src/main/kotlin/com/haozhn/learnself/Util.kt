package com.haozhn.learnself

import android.os.Looper

fun isMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}