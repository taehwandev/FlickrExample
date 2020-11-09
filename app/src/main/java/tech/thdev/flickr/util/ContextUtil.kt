package tech.thdev.flickr.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

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

inline fun Context.isOnline(): Boolean =
    (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
        activeNetworkInfo?.isConnected ?: false
    }