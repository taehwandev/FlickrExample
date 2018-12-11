@file:Suppress("NOTHING_TO_INLINE")

package tech.thdev.flickr.util

import android.content.Context
import android.content.Intent
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

inline fun AppCompatActivity.loadFragment(@IdRes idRes: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().run {
        this.replace(idRes, fragment)
    }.commit()
}

inline fun <reified T : Any> Context.launchActivity(noinline body: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply(body))
}

inline fun Context.show(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

inline fun Context.show(messageId: Int) =
        show(getText(messageId).toString())