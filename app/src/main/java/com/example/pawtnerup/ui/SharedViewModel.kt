//package com.example.pawtnerup.ui
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//
//class SharedViewModel : ViewModel() {
//    private val serverAuthCodeLiveData = MutableLiveData<String>()
//
//    fun setServerAuthCode(serverAuthCode: String) {
//        serverAuthCodeLiveData.value = serverAuthCode
//    }
//
//    fun getServerAuthCode(): LiveData<String> {
//        return serverAuthCodeLiveData
//    }
//}