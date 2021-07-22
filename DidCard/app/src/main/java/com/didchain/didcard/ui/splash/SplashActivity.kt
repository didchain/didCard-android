package com.didchain.didcard.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivitySplashBinding
import com.didchain.didcard.ui.guide.GuideActivity
import com.didchain.didcard.utils.DialogUtils
import com.didchain.didcard.utils.SharedPref
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {
    private val mHandler : Handler by lazy { Handler(Looper.getMainLooper()) }
    var sureAuthority : Boolean by SharedPref(this, Constants.KEY_SURE_AUTHORITY, false)
    override val mViewModel: SplashViewModel by viewModel()

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_splash

    override fun initView() {
        mHandler.postDelayed({
            if (mViewModel.hasAccount) {
                mViewModel.loadCard()
            } else {
                if(sureAuthority){
                    startActivity(GuideActivity::class.java)
                    finish()
                    return@postDelayed
                }
                DialogUtils.showPrivacyAuthorityDialog(this, OnConfirmListener {
                    sureAuthority = true
                    startActivity(GuideActivity::class.java)
                    finish()
                }, OnCancelListener {
                    finish()
                })

            }
        }, 1000)

    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_TRANSPARENT

    override fun initVariableId(): Int = BR.viewModel


    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}