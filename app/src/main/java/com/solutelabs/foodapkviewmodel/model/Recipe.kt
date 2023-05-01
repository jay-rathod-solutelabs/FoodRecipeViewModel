package com.solutelabs.foodrecipeapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Recipe(
    @SerializedName("pk") val pk: Int,

    @SerializedName("title") val title: String,

    @SerializedName("publisher") val publisher: String,

    @SerializedName("featured_image") val featured_image: String,

    @SerializedName("rating") val rating: Int,

    @SerializedName("source_url") val sourceUrl: String,

    @SerializedName("ingredients") val ingredients: List<String>,

    @SerializedName("date_added") val dateAdded: String,

    @SerializedName("date_updated") val dateUpdated: String,

    var isSaved: Boolean = false
)
