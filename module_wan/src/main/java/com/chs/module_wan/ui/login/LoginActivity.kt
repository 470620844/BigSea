package com.chs.module_wan.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.chs.lib_common_ui.base.BaseActivity
import com.chs.lib_core.constant.WanBusKey
import com.chs.lib_core.event.LiveDataBus
import com.chs.module_wan.R
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.wan_activity_login.*

/**
 * author：chs
 * date：2020/4/11
 * des：
 */
class LoginActivity : BaseActivity() {

    private val mViewModel:LoginViewModel by viewModels()

    override fun getContentView(savedInstanceState: Bundle?): Int = R.layout.wan_activity_login

    override fun initView() {
        ImmersionBar.with(this).transparentStatusBar().init()
    }

    override fun initListener() {
        super.initListener()
        btn_submit.setOnClickListener{
            doLogin()
        }
        tv_login_forget.setOnClickListener{
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fl_root,RegisterFragment.newInstance(),RegisterFragment::class.java.simpleName)
                .commit()
        }
        LiveDataBus.get<String>(WanBusKey.KEY_CLOSE_FRAGMENT).observe(this, Observer {
            val fragment = supportFragmentManager.findFragmentByTag(RegisterFragment::class.java.simpleName)
            if(fragment!=null){
                val transaction = supportFragmentManager.beginTransaction()
                transaction.remove(fragment).commit()
            }
        })

    }

    private fun doLogin() {
        val username = et_username.text.toString()
        val password = et_password.text.toString()
        mViewModel.mLoginEntity.observe(this, Observer {
            UserManager.get().save(it)
            finish()
        })
        mViewModel.doLogin(username,password)
    }

    override fun initData() {
    }
}