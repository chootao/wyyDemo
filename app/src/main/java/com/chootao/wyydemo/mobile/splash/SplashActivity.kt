package com.chootao.wyydemo.mobile.splash

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.chootao.wyydemo.R
import com.chootao.wyydemo.base.BaseActivity
import com.chootao.wyydemo.mobile.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : BaseActivity() {
    private val TAG = "SplashActivity"
    private val viewModel: SplashViewModel by viewModels()

    override fun initData() {
        viewModel.startCountDown(4)
    }

    override fun initEvent() {
        viewModel.mElapsedRealTime.observe(this , Observer {
            tv_splash_jump.text = it.toString()
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
        setContentView(R.layout.activity_splash)
    }
}