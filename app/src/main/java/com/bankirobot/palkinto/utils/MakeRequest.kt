package com.bankirobot.palkinto.utils

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class MakeRequest(private val url: String) {
    var userValidator: String = ""
        set(value) {
            //Connect
            val urlConn = URL(url).openConnection() as HttpURLConnection
            urlConn.doOutput = true
            urlConn.setRequestProperty("Content-Type", "application/json")
            urlConn.requestMethod = "POST"
            urlConn.connect()
            //Write
            val outStream = urlConn.outputStream
            val writer = BufferedWriter(OutputStreamWriter(outStream, StandardCharsets.UTF_8))
            writer.write(value)
            writer.close()
            outStream.close()
            //Read
            val reader =
                BufferedReader(InputStreamReader(urlConn.inputStream, StandardCharsets.UTF_8))
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) sb.append(line)
            reader.close()
            field = sb.toString()
        }
}