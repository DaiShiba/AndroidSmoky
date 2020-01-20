package com.yomogi.smoky.Biometric.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BiometricViewModel : ViewModel() {
    val biometricCallback: MutableLiveData<Pair<String, String>> = MutableLiveData()

    fun updateCallbackMessage (callback : String, message : String) {
        biometricCallback.postValue(Pair(callback, message))
    }
}