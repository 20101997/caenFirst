package com.grupposts.trasporti.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.grupposts.domain.util.SingleLiveEvent
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    private val _loading = SingleLiveEvent<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = SingleLiveEvent<Exception>()
    val error: LiveData<Exception> = _error

    private val _toast = SingleLiveEvent<String>()
    val toast: LiveData<String> = _toast

    protected fun setLoading(value: Boolean) {
        _loading.value = value
    }

    protected fun setError(exception: Exception) {
        Timber.e(exception)
        _error.postValue(exception)
    }

    protected fun showToast(message: String) {
        _toast.postValue(message)
    }

}