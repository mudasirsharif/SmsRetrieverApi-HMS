package com.huawei.dtse.retrievingsmscode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    var clickObserver = MutableLiveData<String>()
    var mobileNumber = MutableLiveData<String>()
    var otp = MutableLiveData<String>()

    fun onClickGenerateCode(eventName: String) {

        clickObserver.value = eventName
    }
}