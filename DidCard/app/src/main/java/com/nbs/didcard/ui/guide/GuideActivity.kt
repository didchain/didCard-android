package com.nbs.didcard.ui.guide

import android.os.Bundle
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivityGuideBinding
import org.koin.android.ext.android.inject

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class GuideActivity : BaseActivity<GuideViewModel, ActivityGuideBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_guide
    override val mViewModel: GuideViewModel by inject()

    override fun initView() {

    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_TRANSPARENT


}