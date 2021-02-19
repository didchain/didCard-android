package com.didchain.didcard.ui.idmanager

import android.os.Bundle
import androidx.lifecycle.Observer
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityIdCardManagerBinding
import com.didchain.didcard.utils.DialogUtils
import com.didchain.didcard.view.ImportSuccessPop
import com.lxj.xpopup.XPopup
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
           DialogUtils.showExportSuccessDialog(this)
        })
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE


}