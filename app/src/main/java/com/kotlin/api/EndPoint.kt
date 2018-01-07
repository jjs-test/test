package com.kotlin.api

import java.util.*

class EndPoint(path: String) {

    private var mWholePath = ""
    private var mParams: Map<String, Any>? = null

    init {
        this.mWholePath = path
        this.mParams = null
    }

    fun add(params: Map<String, Any>): EndPoint {
        val endPoint = copy()
        endPoint.mParams = params
        return endPoint
    }

    private fun copy(): EndPoint {
        val endPoint = EndPoint(mWholePath)
        endPoint.mParams = HashMap(mParams.orEmpty())
        return endPoint
    }

    fun get(): String {
        return  mWholePath +
                mParams?.let { putParameterAtLast(it.entries) }.orEmpty()
    }

    /**
     * @param entrySet
     * @return ? 로 시작하는 get parameter string
     */
    private fun putParameterAtLast(entrySet: Set<Map.Entry<String, Any>>): String {
        return entrySet
                .map { entry -> "${entry.key}=${entry.value}" }
                .fold("?", { a, b -> "$a&$b" })
                .replace(PARAM_REGEX_HEAD, "?")
                .replace(PARAM_REGEX_TAIL, "")
    }

    companion object {
        private val DELIMITER = "/"
        private val PARAM_REGEX_HEAD = "^\\?&".toRegex()
        private val PARAM_REGEX_TAIL = "([?|&]$)".toRegex()
    }
}