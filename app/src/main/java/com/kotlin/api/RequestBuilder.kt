package com.kotlin.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.kotlin.api.OnApiResponse.Ask.PARSE_FAIL_CODE
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.ParseException
import java.util.concurrent.TimeUnit

class RequestBuilder {
    private val TAG = "RequestBuilder"

    private object Holder {
        val INSTANCE = RequestBuilder()
    }

    companion object {
        val instance: RequestBuilder by lazy { Holder.INSTANCE }
    }

    fun getBaseRequest(endpoint: String): Request.Builder {
        return Request.Builder()
                .url(endpoint)
    }

    fun getBaseFormBody(params: Map<String, Any>?): RequestBody {
        val builder = FormBody.Builder()
        if (params != null) {
            for ((key, value) in params) {
                builder.add(key, value.toString())
            }
        }
        return builder.build()
    }


    fun <T> submit(request: Request,
                   onApiResponse: OnApiResponse<T>,
                   clazz: Class<T>): Call {
        return submit(request, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onApiResponse.onFail(OnApiResponse.Ask.NETWORK_FAIL_CODE, e)
                Log.e(request.url().toString(), "submit", e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val responseTransformer = GsonBuilder().create().fromJson(responseBody?.string(), clazz)
                        onApiResponse.onSuccess(response.code(), responseTransformer)
                    } else {
                        onApiResponse.onFail(response.code(), IllegalStateException(getMessage(response)))
                    }
                } catch (e: ParseException) {
                    onApiResponse.onFail(PARSE_FAIL_CODE, e)
                } finally {
                    response.close()
                }
            }
        })
    }

    private fun submit(request: Request, callback: Callback): Call {
        val call = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
                .newCall(request)
        call.enqueue(callback)
        return call
    }


    private fun getMessage(response: Response): String {
        var message = response.message()
        try {
            message = JSONObject(response.body()?.string()).getString("message")
        } catch (e: JSONException) {
            Log.d(TAG, "getMessage", e)
        } catch (e: IOException) {
            Log.d(TAG, "getMessage", e)
        }

        return message
    }


}