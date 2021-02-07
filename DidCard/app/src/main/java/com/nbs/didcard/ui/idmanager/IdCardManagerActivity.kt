package com.nbs.didcard.ui.idmanager

import android.os.Bundle
import androidx.lifecycle.Observer
import com.lxj.xpopup.XPopup
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.BR
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivityIdCardManagerBinding
import com.nbs.didcard.view.ImportSuccessPop
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class IDCardManagerActivity : BaseActivity<IDCardManagerViewModel, ActivityIdCardManagerBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_id_card_manager
    override val mViewModel: IDCardManagerViewModel by viewModel()
    override fun initView() {
        mViewModel.title.set(getString(R.string.my_identity_management))
        mViewModel.showBackImage.set(true)
    }

    override fun initData() {
    }

    override fun initObserve() {
        mViewModel.exportSuccessEvent.observe(this, Observer {
            XPopup.Builder(this).isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                .asCustom(ImportSuccessPop(this)).show()
        })
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE


}