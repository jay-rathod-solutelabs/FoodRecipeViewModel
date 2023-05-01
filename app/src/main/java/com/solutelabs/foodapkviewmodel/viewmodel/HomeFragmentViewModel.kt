package com.solutelabs.foodapkviewmodel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solutelabs.foodapkviewmodel.Constants
import com.solutelabs.foodapkviewmodel.RecipeService
import com.solutelabs.foodrecipeapp.model.Recipe
import com.solutelabs.foodrecipeapp.model.RecipeSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel : ViewModel() {

    // Initialize properties with default values
    private var pageNum = 1
    private var searchQuery = Constants.constDefaultQuery
    private var recipeList = mutableListOf<Recipe>()
    private var totalResults = 0

    // Create a MutableLiveData to observe changes in the recipe list
    val recipeListLiveData = MutableLiveData<List<Recipe>?>()

    // Fetch recipes from the API using the RecipeService
    fun getRecipes(pageNum: Int, searchQuery: String) {
        val recipes = RecipeService.RecipeInstance.getAllRecipes(pageNum, searchQuery)
        recipes.enqueue(object : Callback<RecipeSearchResponse> {
            override fun onResponse(
                call: Call<RecipeSearchResponse>,
                response: Response<RecipeSearchResponse>
            ) {
                val recipes = response.body()
                if (recipes != null) {
                    // Update total results count
                    totalResults = recipes.count
                    if (pageNum == 1) {
                        // If it's the first page, replace the current recipe list with new results
                        recipeList = recipes.results.toMutableList()
                    } else {
                        // If it's not the first page, add new results to the existing recipe list
                        recipeList.addAll(recipes.results)
                    }
                    // Update recipeListLiveData with the updated recipe list
                    recipeListLiveData.value = recipeList
                }
            }

            override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                // If there is an error, set recipeListLiveData to null
                recipeListLiveData.value = null
            }
        })
    }

    // Search recipes based on a query string
    fun searchRecipes(searchquery: String) {
        // Reset page number and recipe list
        pageNum = 1
        recipeList.clear()
        // Fetch new recipes based on the search query
        getRecipes(pageNum, searchquery)
    }

    // Load more recipes when the user reaches the end of the current list
    fun loadMoreRecipes() {
        if (recipeList.size < totalResults) {
            // Increment page number and fetch new recipes
            pageNum++
            getRecipes(pageNum, searchQuery)
        }
    }

    // Clear search query and reset page number
    fun clearSearchQuery() {
        searchQuery = Constants.constDefaultQuery
        pageNum = 1
    }

    // Fetch all recipes again
    fun reloadAllRecipes() {
        getRecipes(pageNum, searchQuery)
    }

}
