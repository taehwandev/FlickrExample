package tech.thdev.support.network.util

import tech.thdev.support.network.UTF_8
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
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
            try {
                it.inputStream?.close()
            } catch (e: FileNotFoundException) {
                // Do nothing
            }
            it.disconnect()
        }
    }
}

/**
 * stream의 데이터를 읽어들인다.
 * @receiver InputStream
 * @return String 읽은 데이터를 return
 */
fun InputStream.readStream(dec: String = UTF_8): String? {
    val stringBuilder = StringBuilder()

    // Read inputStream using default UTF-8
    InputStreamReader(this, dec).run {
        readLines().forEach { line ->
            stringBuilder.append(line)
        }
    }
    return stringBuilder.toString()
}