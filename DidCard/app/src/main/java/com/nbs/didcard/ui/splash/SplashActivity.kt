package com.nbs.didcard.ui.splash

import android.os.Bundle
import android.os.Handler
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivitySplashBinding
import com.nbs.didcard.ui.guide.GuideActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
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