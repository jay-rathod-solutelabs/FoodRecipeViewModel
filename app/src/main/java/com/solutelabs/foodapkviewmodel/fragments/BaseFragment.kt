package com.solutelabs.foodapkviewmodel.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.solutelabs.foodapkviewmodel.R

abstract class BaseFragment : Fragment() {

    private lateinit var rootView: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
    }


    fun Fragment.showSnackBar(message: String) {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

}
