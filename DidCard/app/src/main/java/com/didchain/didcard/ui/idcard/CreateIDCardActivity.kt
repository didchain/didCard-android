package com.didchain.didcard.ui.idcard

import android.Manifest
import android.os.Bundle
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityCreateIdCardBinding
import com.didchain.didcard.ui.create.CreateCardViewModel
import com.didchain.didcard.utils.IDCardUtils
import com.didchain.didcard.utils.PermissionUtils
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class CreateIDCardActivity : BaseActivity<CreateCardViewModel, ActivityCreateIdCardBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_create_id_card
    override val mViewModel: CreateCardViewModel by viewModel()

    override fun initView() {
        mViewModel.title.set(getString(R.string.id_card_manager_new))
        mViewModel.showBackImage.set(true)
    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE

    override fun initVariableId(): Int = BR.viewModel



}