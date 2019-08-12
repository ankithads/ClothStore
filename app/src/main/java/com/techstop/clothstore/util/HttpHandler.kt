package com.techstop.clothstore.util

import android.os.AsyncTask
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class HttpHandler(callback: (List<String>) -> Unit) : AsyncTask<String, String, List<String>>() {

    var callback = callback

    /*
    params[0] = Json string for POST request
    params[1] = request type (POST/GET)
    params[2] = URL
    params[3] = access_token
     */
    override fun doInBackground(vararg params: String?): List<String> {


        val connection = URL(params[2]).openConnection() as HttpURLConnection


        connection.requestMethod = params[1]
        println("connection.requestMethod" + connection.requestMethod)

        if(params[1] == "POST") {
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
        }
        connection.setRequestProperty("Accept", "application/json")

        try {
            connection.connect()

            if(params[1] == "POST") {
                val os = connection.getOutputStream()
                val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                println(params[0])
                writer.write(params[0])
                writer.flush()
                writer.close()
                os.close()
            }
            println(connection.responseCode)
            if (connection.responseCode >= 200 && connection.responseCode < 300 ) {
                val stream = BufferedInputStream(connection.inputStream)
                val bufferedReader = BufferedReader(InputStreamReader(stream))
                val stringBuilder = StringBuilder()
                bufferedReader.forEachLine { stringBuilder.append(it) }
                println(stringBuilder.toString())
                return listOf("1", stringBuilder.toString())

            } else {
                println("ERROR ${connection.responseCode}")
                val stream = BufferedInputStream(connection.errorStream)
                val bufferedReader = BufferedReader(InputStreamReader(stream))
                val stringBuilder = StringBuilder()
                bufferedReader.forEachLine { stringBuilder.append(it) }
                println(stringBuilder.toString())
                return listOf("0", stringBuilder.toString())

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {

            connection.disconnect()
        }
        return listOf("0", "")

    }

    override fun onPostExecute(result: List<String>) {
        super.onPostExecute(result)
        callback(result)
    }
}
