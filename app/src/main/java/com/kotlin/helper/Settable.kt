package com.kotlin.helper

interface Settable<in T> {
    fun setData(t: T?)
}
