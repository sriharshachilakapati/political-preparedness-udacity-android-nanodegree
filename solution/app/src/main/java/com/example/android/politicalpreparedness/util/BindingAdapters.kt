package com.example.android.politicalpreparedness.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.android.politicalpreparedness.R

@BindingAdapter("app:visibleIf")
fun visibleIf(view: View, isVisible: Boolean?) {
    view.visibility = if (isVisible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("app:circularCutImage")
fun circularCutImage(view: ImageView, url: String?) {
    Glide.with(view).load(url)
            .placeholder(R.drawable.ic_profile)
            .circleCrop()
            .into(view)
}