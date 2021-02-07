package com.nbs.didcard.ui.idcard

import android.os.Bundle
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivityShowIdCardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class ShowIDCardActivity : BaseActivity<ShowIDCardViewModel, ActivityShowIdCardBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_show_id_card
    override val mViewModel: ShowIDCardViewModel by viewModel()

    override fun initView() {
        mViewModel.title.set(getString(R.string.id_card_title))
        mViewModel.showBackImage.set(true)
    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_GRAY

}