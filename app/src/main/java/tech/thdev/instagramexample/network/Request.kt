package tech.thdev.instagramexample.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

suspend fun String.request(readTimeout: Int, connectTimeout: Int): String? {
    val request = CoroutineScope(Dispatchers.IO).async {
        URL(this@request).use {
            setReadTimeout(readTimeout)
            setConnectTimeout(connectTimeout)
            println(this@request)
            // Default test
            readTimeout = 3000
            connectTimeout = 3000
            requestMethod = HTTP_GET
            connect()

            // request test
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw Exception("URLConnection error $responseCode")
            }

            println("inputStream read ${inputStream?.read()}")

            return@use responseCode
        }
    }
    return request.await()
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