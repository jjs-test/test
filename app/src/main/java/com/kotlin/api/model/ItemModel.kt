package com.kotlin.api.model

import com.google.gson.annotations.SerializedName

data class ItemModel constructor(
        @SerializedName("login") val login: String,
        @SerializedName("id") val id: Int,
        @SerializedName("avatar_url") val avatarUrl: String) {
}