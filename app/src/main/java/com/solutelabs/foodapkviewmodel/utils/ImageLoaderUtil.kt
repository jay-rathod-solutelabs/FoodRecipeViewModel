package com.solutelabs.foodrecipeapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.solutelabs.foodapkviewmodel.R

object ImageLoaderUtil {
    fun load(context: Context, imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.imge)
            .into(imageView)
    }
}
