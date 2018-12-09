package tech.thdev.support.data

import javax.net.ssl.HttpsURLConnection

data class Response(val message: String? = "", val requestCode: Int = HttpsURLConnection.HTTP_CREATED)