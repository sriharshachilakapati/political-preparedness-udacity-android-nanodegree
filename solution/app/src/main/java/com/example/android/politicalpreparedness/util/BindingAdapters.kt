package com.example.android.politicalpreparedness.util

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.android.politicalpreparedness.R

@BindingAdapter("app:visibleIf")
fun visibleIf(view: View, isVisible: Boolean?) {
    view.visibility = if (isVisible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("app:circularCutImage")
fun circularCutImage(view: ImageView, url: String?) {
    val uri = (url ?: "").toUri().buildUpon().scheme("https").build()

    Glide.with(view).load(uri)
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
            .circleCrop()
            .into(view)
}