package com.didchain.didcard.ui.splash

import android.os.Bundle
import android.os.Handler
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivitySplashBinding
import com.didchain.didcard.ui.guide.GuideActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {
    override val mViewModel: SplashViewModel by viewModel()

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_splash

    override fun initView() {
        Handler(mainLooper).postDelayed({
            if (mViewModel.hasAccount) {
                mViewModel.loadCard()
            } else {
                startActivity(GuideActivity::class.java)
                finish()
            }
        }, 1000)

    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_TRANSPARENT

    override fun initVariableId(): Int = BR.viewModel


}