package cyril.cieslak.simplMeteoByGpsPoint.UtilsClass

import android.content.Context
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

@Suppress("DEPRECATION")
class JSONDownloaderNotification(private var c: Context, private var jsonResultSearchActivity: String) {


    // Connect to NetWork via HTTPURLConnection
    // ***

    // ***
    private fun connect(jsonResultSearchActivity: String): Any {

        try {
            val url = URL(jsonResultSearchActivity)
            val con = url.openConnection() as HttpURLConnection

            // Con Props

            con.requestMethod = "GET"
            con.connectTimeout = 15000
            con.readTimeout = 15000
            con.doInput = true


            return con

        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return "URL ERROR" + e.message

        } catch (e: IOException) {
            e.printStackTrace()
            return "CONNECT ERROR" + e.message

        }

    }


    // ***
    // Download JsonData
    // ***
    fun download(): String {

        val connection = connect(jsonResultSearchActivity)
        if (connection.toString().startsWith("Error")) {
            return connection.toString()
        }
        // DOWNLOAD
        try {
            val con = connection as HttpURLConnection
            // if response is HTTP OK
            if (con.responseCode == 200) {
                // GET INPUT FROM STREAM
                val `is` = BufferedInputStream(con.inputStream)
                val br = BufferedReader(InputStreamReader(`is`))

                val jsonData = StringBuffer()
                var line: String?

                do {
                    line = br.readLine()

                    if (line == null) {
                        break
                    }
                    jsonData.append(line + "\n")

                } while (true)

                // CLOSING RESOURCES
                br.close()
                `is`.close()


                // RETURN JSON
                return jsonData.toString()


            } else {
                return "Error" + con.responseMessage
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return "Error" + e.message
        }


    }

}