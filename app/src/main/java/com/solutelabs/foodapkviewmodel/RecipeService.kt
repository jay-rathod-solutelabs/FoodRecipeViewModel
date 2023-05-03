package com.solutelabs.foodapkviewmodel


import com.solutelabs.foodrecipeapp.model.RecipeSearchResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val BASE_URL = BuildConfig.BASE_URL
const val KEY_TOKEN = BuildConfig.KEY_TOKEN

interface RecipeInterface {

    @Headers("Accept: application/json", "Authorization: $KEY_TOKEN")
    @GET("search/")
    fun getAllRecipes(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Call<RecipeSearchResponse>

}

object RecipeService {
    val RecipeInstance: RecipeInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        RecipeInstance = retrofit.create(RecipeInterface::class.java)
    }
}






