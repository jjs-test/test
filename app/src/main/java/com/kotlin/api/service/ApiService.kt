package com.kotlin.api.service

import com.kotlin.api.EndPoint
import com.kotlin.api.OnApiResponse
import com.kotlin.api.RequestBuilder
import com.kotlin.api.model.SearchModel
import okhttp3.Call

class ApiService {
    private object Holder {
        val INSTANCE = ApiService()
    }

    companion object {
        val instance: ApiService by lazy { Holder.INSTANCE }
    }

    fun startService(searchItemText: String,
                     onApiResponse: OnApiResponse<SearchModel>): Call {
        val params = HashMap<String, Any>()
        params.put("q", searchItemText)

        val request = RequestBuilder.instance
                .getBaseRequest(
                        EndPoint("https://api.github.com/search/users")
                                .add(params)
                                .get())
                .get()
                .build()
        return RequestBuilder.instance.submit(request, onApiResponse, SearchModel::class.java)
    }

}