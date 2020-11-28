package com.chootao.wyydemo.mobile.splash

import android.content.Intent
import androidx.lifecycle.Observer
import com.chootao.wyy_base.base.BaseActivity
import com.chootao.wyydemo.R
import com.chootao.wyydemo.mobile.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : BaseActivity<SplashViewModel>() {
    override fun initData() {
        viewModel.startCountDown(4)
    }

    override fun initEvent() {
        viewModel.mElapsedRealTime.observe(this , Observer {
            tv_splash_jump.text = "跳过 ${it}"
        })

        viewModel.isComplete.observe(this , Observer {
            if(it){
                startActivity(Intent(this , MainActivity::class.java))
                finish()
            }
        })

        tv_splash_jump.setOnClickListener {
            viewModel.jumpClick()
        }
    }

    override fun initView() {

    }

    override val layoutID: Int
        get() = R.layout.activity_splash
}