package com.nbs.didcard.ui.guide

import com.gyf.barlibrary.ImmersionBar
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivityGuideBinding

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class GuideActivity : BaseActivity<GuideViewModel, ActivityGuideBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_guide

    override fun initView() {

    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_TRANSPARENT
    override val mViewModel: GuideViewModel
        get() = createViewModel(GuideViewModel::class.java)

}