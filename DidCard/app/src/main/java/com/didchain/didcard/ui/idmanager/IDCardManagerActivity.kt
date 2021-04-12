package com.didchain.didcard.ui.idmanager

import android.Manifest
import android.os.Bundle
import androidx.lifecycle.Observer
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityIdCardManagerBinding
import com.didchain.didcard.event.EventLoadIDCard
import com.didchain.didcard.utils.DialogUtils
import com.didchain.didcard.utils.PermissionUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

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
        EventBus.getDefault().register(this)
    }

    override fun initData() {
    }

    override fun initObserve() {
        mViewModel.exportSuccessEvent.observe(this, Observer {
            DialogUtils.showExportSuccessDialog(this)
        })

        mViewModel.requestLocalPermissionEvent.observe(this, Observer {
            requestLocalMemoryPermission()
        })
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE

    @AfterPermissionGranted(Constants.CODE_OPEN_ALBUM)
    fun requestLocalMemoryPermission() {
        if (PermissionUtils.hasStoragePermission(this)) {
            mViewModel.saveIDCard()
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.import_apply_album_permission), Constants.CODE_OPEN_ALBUM, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun reloadIDcard(event: EventLoadIDCard) {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}