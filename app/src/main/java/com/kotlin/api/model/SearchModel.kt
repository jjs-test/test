package com.kotlin.api.model

import com.google.gson.annotations.SerializedName

data class SearchModel constructor(
        @SerializedName("total_count") val count: Int,
        @SerializedName("items") val items: List<ItemModel>) {
}