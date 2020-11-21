package com.chootao.wyydemo.mobile.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class SplashViewModel : ViewModel() {
    val mElapsedRealTime = MutableLiveData<Int>()
    val isComplete = MutableLiveData<Boolean>()


    fun startCountDown(initCount : Int){
        mElapsedRealTime.value = initCount
        GlobalScope.launch(Dispatchers.IO) {
            while ( mElapsedRealTime.value?: 0 > 0){
                delay(1000)
                withContext(Dispatchers.Main){
                    val count = (mElapsedRealTime.value?:0) - 1
                    mElapsedRealTime.value = count
                }
            }
            withContext(Dispatchers.Main){
                isComplete.value = true
            }
        }
    }

    fun jumpClick(){
        isComplete.value = true
    }
}