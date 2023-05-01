package com.solutelabs.foodapkviewmodel.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solutelabs.foodapkviewmodel.Constants
import com.solutelabs.foodapkviewmodel.adapter.RecipeAdapter
import com.solutelabs.foodapkviewmodel.databinding.FragmentHomeBinding
import com.solutelabs.foodapkviewmodel.viewmodel.HomeFragmentViewModel

class HomeFragment : BaseFragment() {

    // Declare binding, viewmodel and adapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout and return root view
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView with RecipeAdapter
        recipeAdapter = RecipeAdapter(emptyList())
        binding.recycleViewRecipeList.apply {
            adapter = recipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Initialize viewmodel
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)

        // Observe for changes in recipe list and update adapter accordingly
        viewModel.recipeListLiveData.observe(viewLifecycleOwner, Observer { recipes ->
            if (recipes != null && recipes.isNotEmpty()) {
                recipeAdapter.updateList(recipes) // update the existing adapter instance with new data
                binding.noDataImage.visibility = View.GONE // hide the image
            } else {
                recipeAdapter.updateList(emptyList()) // clear the adapter when no data is found
                binding.noDataImage.visibility = View.VISIBLE // show the image
            }
            binding.progressBar.visibility = View.GONE // hide the progress bar
        })

        binding.progressBar.visibility = View.VISIBLE // show the progress bar
        viewModel.getRecipes(1, Constants.constDefaultQuery)

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.searchRecipes(query)
                    binding.progressBar.visibility = View.VISIBLE


                    // hide keyboard
                    val inputMethodManager =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding.searchView.windowToken, 0)

                    return true
                }
                return false
            }

            // Clear search query and reload all recipes on query text change
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    viewModel.clearSearchQuery()
                    viewModel.reloadAllRecipes()
                    binding.noDataImage.visibility =
                        if (recipeAdapter.itemCount == 0) View.VISIBLE else View.GONE
                } else {
                    binding.noDataImage.visibility = View.GONE
                }
                return true
            }
        })

    }

    override fun onResume() {
        super.onResume()

        // Load more recipes on RecyclerView scroll to end
        binding.recycleViewRecipeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisiblePosition == recipeAdapter.itemCount - 1) {
                    viewModel.loadMoreRecipes()
                }
            }
        })

    }

}

