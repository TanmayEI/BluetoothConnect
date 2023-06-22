package com.equalinfotech.miiwey.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

open class BaseViewModel<Any>() : ViewModel() {

    private val mIsLoading = ObservableBoolean()

    private var mNavigator: WeakReference<Any>? = null

    fun getIsLoading(): ObservableBoolean? {
        return mIsLoading
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }

    fun getNavigator(): Any? {
        return mNavigator!!.get()
    }

    fun setNavigator(navigator: Any) {
        mNavigator = WeakReference(navigator)
    }

}