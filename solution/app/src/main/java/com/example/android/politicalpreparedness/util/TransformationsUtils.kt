package com.example.android.politicalpreparedness.util

import androidx.annotation.MainThread
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

object TransformationsUtils {
    @MainThread
    fun <X, Y> map(
            source: LiveData<X>,
            defaultValue: Y,
            mapFunction: Function<X?, Y>
    ): LiveData<Y> {
        val result = MediatorLiveData<Y>().apply { value = defaultValue }
        result.addSource(source) { x -> result.value = mapFunction.apply(x) }
        return result
    }
}