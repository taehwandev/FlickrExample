package tech.thdev.instagramexample.network

import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection


fun <T> URL.use(body: HttpsURLConnection.() -> T?): T? {
    var urlConnection: HttpsURLConnection? = null
    try {
        urlConnection = (this.openConnection() as? HttpsURLConnection)
        return urlConnection?.body()
    } catch (ioException: IOException) {
        throw IOException(ioException)
    } finally {
        urlConnection?.let {
            it.inputStream?.close()
            it.disconnect()
        }
    }
}