package com.ozancanguz.instagram.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ozancanguz.instagram.R

fun ImageView.loadUrl(url: String?, errorDrawable: Int = R.drawable.ic_background) {
    context?.let {
        val options = RequestOptions()
            .error(errorDrawable)
        Glide.with(context.applicationContext)
            .load(url)
            .apply(options)
            .into(this)
    }
}

