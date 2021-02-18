package com.example.android.politicalpreparedness.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visibleIf")
fun visibleIf(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}