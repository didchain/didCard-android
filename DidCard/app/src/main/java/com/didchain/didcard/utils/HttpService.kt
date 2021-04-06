package com.didchain.didcard.utils

import com.didchain.didcard.Constants
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class HttpService {

    @Throws(Exception::class)
    fun sendGetRequest(url: String, charset: String = "utf-8"): String {
        var resultBuffer = StringBuffer()
        var httpURLConnection = URL(url).openConnection() as HttpURLConnection
        httpURLConnection.connectTimeout = Constants.TIMEOUT
        httpURLConnection.readTimeout =  Constants.TIMEOUT
        httpURLConnection.setRequestProperty("Accept-Charset", charset)
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        if (httpURLConnection.responseCode >= 300) throw Exception("HTTP Request is not success, Response code is ${httpURLConnection.responseCode}")
        val inputStream = httpURLConnection.inputStream
        val inputStreamReader = InputStreamReader(inputStream, charset)
        val reader = BufferedReader(inputStreamReader)
        reader.use { r ->
            var temp = r.readLine()
            if (temp != null) resultBuffer.append(temp)
        }
        reader.close()
        inputStreamReader.close()
        inputStream.close()
        return resultBuffer.toString()
    }

    @Throws(Exception::class)
    fun sendPostRequest(reqBody: String, url: String, charset: String = "utf-8"): String {
        val bufferResult = StringBuffer()
        val conn = URL(url).openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.connectTimeout = Constants.TIMEOUT
        conn.readTimeout =  Constants.TIMEOUT
        conn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
        conn.useCaches = false
        conn.doOutput = true
        conn.doInput = true
        conn.connect()

        val out = PrintWriter(OutputStreamWriter(conn.outputStream, charset))
        out.print(reqBody)
        out.flush()
        val inStream = BufferedReader(InputStreamReader(conn.inputStream, charset))
        inStream.use { r ->
            val temp = r.readLine()
            if (temp != null) bufferResult.append(temp)
        }
        out.close()
        inStream.close()

        return bufferResult.toString()
    }

    fun getParamStr (params: Map<String, String>): String {
        val str = StringBuffer()
        var temp = ""
        params.keys.map {k ->
            temp = k + "=" + URLEncoder.encode(params.get(k), "utf-8") + "&"
            str.append(temp)
        }
        return str.toString().substring(0, str.toString().count() - 1)
    }

}