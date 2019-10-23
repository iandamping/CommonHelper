package com.ian.app.helper.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {
    protected var fetchingJob = Job()
    protected var uiScope = CoroutineScope(Dispatchers.Main + fetchingJob)
    protected val vmScope = viewModelScope
}