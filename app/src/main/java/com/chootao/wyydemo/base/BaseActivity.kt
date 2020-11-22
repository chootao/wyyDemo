package com.chootao.wyydemo.base

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.chootao.wyydemo.R
import kotlinx.android.synthetic.main.activity_base.*
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<T: ViewModel >  : AppCompatActivity() {
    private var rootView : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
        initView()
        initData()
        initEvent()
    }

    fun setView(){
        rootView =View.inflate(this , R.layout.activity_base , null)
        super.setContentView(rootView)

        val contentView =View.inflate(this , layoutID , null)
        contentView.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        fl_content.addView(contentView)
    }

    val viewModel : T by lazy{
        val classType = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        ViewModelProvider(this).get(classType)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
    }

    protected abstract fun initData()
    protected abstract fun initEvent()
    protected abstract fun initView()
    protected abstract val layoutID : Int
}