package com.enablex.demoenablex.web_communication

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.util.Base64
import android.util.Log
import android.widget.Toast
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*


class WebCall(private val context: Context, private val callback: WebResponse, `object`: JSONObject?,
              private val urlPath: String, private val callCode: Int, isGetCall: Boolean?, isAuthenticated: Boolean?) : AsyncTask<Void, Void, String>() {
    private val paramsMap: HashMap<String, String>? = null
    private val isShowDialog = true
    private var isGetCall = false
    private var isAuthenticated = false
    private var `object`: JSONObject? = null
    internal var dialog: ProgressDialog? = null

    init {
        this.`object` = `object`
        this.isGetCall = isGetCall!!
        this.isAuthenticated = isAuthenticated!!
    }

    override fun onPreExecute() {
        super.onPreExecute()
        if (!checkIntenetConnection()) {

            return
        }
        if (isShowDialog) {
            dialog = ProgressDialog(context)
            dialog!!.setCancelable(false)
            dialog!!.show()
        }
    }

    override fun doInBackground(vararg params: Void): String? {
        return if (isGetCall) {
            callGetCall()
        } else {
            callPostCallWithJSON()
        }

    }

    private fun callGetCall(): String? {
        var httpURLConnection: HttpURLConnection? = null
        try {
            val url = URL(WebConstants.kBaseURL + urlPath + getDataForGetCall(paramsMap))
            Log.e("Weburl", url.toString())
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.readTimeout = 60000
            httpURLConnection.connectTimeout = 60000
            httpURLConnection.requestMethod = "GET"
            if(WebConstants.kTry){
                httpURLConnection.setRequestProperty("x-app-id", WebConstants.kAppId)
                httpURLConnection.setRequestProperty("x-app-key", WebConstants.kAppkey)
            }
            httpURLConnection.setRequestProperty("Content-Type", "application/json")
            httpURLConnection.connect()
            Log.e("responseCode", httpURLConnection.responseCode.toString())
            if (httpURLConnection.responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                httpURLConnection.disconnect()
                return "HTTP_FORBIDDEN"
            }
            if (httpURLConnection.responseCode == HttpURLConnection.HTTP_OK ||
                    httpURLConnection.responseCode == 422 ||
                    httpURLConnection.responseCode == HttpURLConnection.HTTP_UNAUTHORIZED ||
                    httpURLConnection.responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                var `is`: InputStream? = null
                if (httpURLConnection.responseCode == HttpURLConnection.HTTP_NOT_FOUND ||
                        httpURLConnection.responseCode == 422 || httpURLConnection.responseCode == 401) {
                    `is` = httpURLConnection.errorStream
                } else {
                    `is` = httpURLConnection.inputStream
                }
                val bufferedReader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
                val builder = StringBuilder()
                var line: String? = bufferedReader.readLine()
                while (line != null) {
                    builder.append(line + "\n")
                    line = bufferedReader.readLine()
                }
                `is`!!.close()
                httpURLConnection?.disconnect()
                return builder.toString()
            } else {
                httpURLConnection?.disconnect()
                return "Invalid response from the server"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            httpURLConnection?.disconnect()
        }
        return null
    }

    private fun callPostCallWithJSON(): String? {
        var httpURLConnection: HttpURLConnection? = null
        try {
            val url = URL(WebConstants.kBaseURL + urlPath)
            Log.e("Weburl", url.toString())
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.readTimeout = 60000
            httpURLConnection.connectTimeout = 60000
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true
            if(WebConstants.kTry){
                httpURLConnection.setRequestProperty("x-app-id", WebConstants.kAppId)
                httpURLConnection.setRequestProperty("x-app-key", WebConstants.kAppkey)
            }
            httpURLConnection.setRequestProperty("Content-Type", "application/json")
            httpURLConnection.connect()
            val os = httpURLConnection.outputStream
            if (`object` != null) {
                Log.e("jsonObject", `object`.toString())
                os.write(`object`.toString().toByteArray())
                os.flush()
                os.close()
            }
            Log.e("responseCode", httpURLConnection.responseCode.toString())
            if (httpURLConnection.responseCode == HttpURLConnection.HTTP_OK ||
                    httpURLConnection.responseCode == 422 ||
                    httpURLConnection.responseCode == HttpURLConnection.HTTP_UNAUTHORIZED ||
                    httpURLConnection.responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                var `is`: InputStream? = null
                if (httpURLConnection.responseCode == HttpURLConnection.HTTP_NOT_FOUND ||
                        httpURLConnection.responseCode == 422 || httpURLConnection.responseCode == 401) {
                    `is` = httpURLConnection.errorStream
                } else {
                    `is` = httpURLConnection.inputStream
                }
                val bufferedReader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
                val builder = StringBuilder()
                var line: String? = bufferedReader.readLine()
                while (line != null) {
                    builder.append(line + "\n")
                    line = bufferedReader.readLine()
                }
                `is`!!.close()
                httpURLConnection?.disconnect()
                return builder.toString()
            } else {
                httpURLConnection?.disconnect()
                return "Invalid response from the server"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            httpURLConnection?.disconnect()
        }
        return null
    }

    override fun onPostExecute(response: String?) {
        super.onPostExecute(response)
        try {
            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
            }
            if (response == null) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            } else {
                Log.d(TAG, response)
                if (!response.isEmpty()) {
                    callback.onWebResponse(response, callCode)
                } else {
                    callback.onWebResponseError(response, callCode)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getDataForGetCall(postparams: HashMap<String, String>?): String {
        val builder = StringBuilder()
        var count = 0
        if (postparams != null && postparams.size > 0) {
            for ((key, value) in postparams) {
                try {
                    if (count == 0) {
                        builder.append("?")
                    } else {
                        builder.append("&")
                    }
                    builder.append(URLEncoder.encode(key, "UTF-8"))
                    builder.append("=")
                    builder.append(URLEncoder.encode(value, "UTF-8"))
                    count++
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            Log.d("Web Query", builder.toString())
            return builder.toString()
        } else {
            return ""
        }
    }

    private fun checkIntenetConnection(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val activeNetInfoWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting || activeNetInfoWifi != null && activeNetInfoWifi.isConnectedOrConnecting
        if (isConnected) {
            //            Singleton.isNetworkLost = false;
            Log.i("NET", "connected$isConnected")
            return true
        } else {
            Log.i("NET", "disconnected$isConnected")
            return false
        }
    }

    companion object {

        private val TAG = "WebCall"
    }

}

