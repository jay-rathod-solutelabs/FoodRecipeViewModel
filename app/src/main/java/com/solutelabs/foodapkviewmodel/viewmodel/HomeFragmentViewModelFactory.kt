package com.solutelabs.foodapkviewmodel.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solutelabs.foodapkviewmodel.R

class HomeFragmentViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java)) {
            // If the class is HomeFragmentViewModel, instantiate it and return
            return HomeFragmentViewModel(context) as T
        }
        // Throw an exception if the ViewModel class is unknown
        throw IllegalArgumentException(context.getString(R.string.unknown_viewmodel_class))
    }
}

