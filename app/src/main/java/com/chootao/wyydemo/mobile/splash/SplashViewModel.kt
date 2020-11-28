package com.chootao.wyydemo.mobile.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lachesis.consumable.utils.RxTimer

class SplashViewModel : ViewModel() {
    val mElapsedRealTime = MutableLiveData<Long>()
    val isComplete = MutableLiveData<Boolean>()

    private var rxTimer : RxTimer? = null

    fun startCountDown(initCount : Long){
        mElapsedRealTime.value = initCount
        rxTimer = RxTimer()
        rxTimer?.countDown( initCount , rxAction = {
            mElapsedRealTime.value = it
            if(it <= 0 ){
                countComplete()
            }
        })
    }

    private fun countComplete(){
        rxTimer?.cancel()
        isComplete.value = true
    }

    fun jumpClick(){
        countComplete()
    }
}