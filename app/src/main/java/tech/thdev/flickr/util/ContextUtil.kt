@file:Suppress("NOTHING_TO_INLINE")

package tech.thdev.flickr.util

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

inline fun AppCompatActivity.loadFragment(@IdRes idRes: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().run {
        this.replace(idRes, fragment)
    }.commit()
}