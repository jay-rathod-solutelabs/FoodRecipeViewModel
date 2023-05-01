package com.solutelabs.foodrecipeapp.model

import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(

    @SerializedName("count") var count: Int,

    @SerializedName("results") var results: List<Recipe>

)
