package com.lachesis.consumable.utils

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RxTimer {
    private var mDisposable: Disposable? = null
    /**
     * milliseconds毫秒后执行指定动作
     *
     * @param milliSeconds
     * @param rxAction
     */
    fun timer(milliSeconds: Long, rxAction: RxAction?) {
        Observable.timer(milliSeconds, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(disposable: Disposable) {
                    mDisposable = disposable
                }

                override fun onNext(number: Long) {
                    rxAction?.action(number)
                }

                override fun onError(e: Throwable) { //取消订阅
                    cancel()
                }

                override fun onComplete() { //取消订阅
                    cancel()
                }
            })
    }

    /**
     * 每隔milliseconds毫秒后执行指定动作
     *
     * @param milliSeconds
     * @param rxAction
     */
    fun interval(milliSeconds: Long, rxAction: RxAction?) {
        Observable.interval(milliSeconds, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(disposable: Disposable) {
                    mDisposable = disposable
                }

                override fun onNext(number: Long) {
                    rxAction?.action(number)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {
                }
            })
    }

    fun countDown(seconds: Long, rxAction:((Long)->Unit)?=null) {
        Observable.intervalRange(0, seconds, 0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { seconds-1-it }
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(disposable: Disposable) {
                    mDisposable = disposable
                }

                override fun onNext(number: Long) {
                    rxAction?.invoke(number)
                }
                override fun onError(e: Throwable) {}
                override fun onComplete() {
                }
            })
    }

    /**
     * 取消订阅
     */
    fun cancel() {
        if (mDisposable?.isDisposed == false) {
            mDisposable?.dispose()
        }
    }

    interface RxAction {
        /**
         * 让调用者指定指定动作
         *
         * @param number
         */
        fun action(number: Long)
    }
}