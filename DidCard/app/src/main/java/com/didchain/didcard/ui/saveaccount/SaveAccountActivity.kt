package com.didchain.didcard.ui.saveaccount

import android.Manifest
import android.os.Bundle
import android.view.KeyEvent
import androidx.lifecycle.Observer
import com.didchain.android.lib.base.BaseActivity
import com.didchain.android.lib.utils.toast
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivitySaveAccountBinding
import com.didchain.didcard.ui.main.MainActivity
import com.didchain.didcard.utils.IDCardUtils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
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
class SaveAccountActivity : BaseActivity<SaveAccountViewModel, ActivitySaveAccountBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_save_account
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_GRAY
    override fun initVariableId(): Int = BR.viewModel
    override val mViewModel: SaveAccountViewModel by viewModel()

    override fun initView() {
    }

    override fun initData() {}

    override fun initObserve() {
        mViewModel.saveAlbumEvent.observe(this, Observer {
            if (!checkExternalPermission()) {
                requestExternalPermission()
            } else {
                saveCard()

            }
        })

        mViewModel.saveAlbumResultEvent.observe(this, Observer { isSaved ->
            if (isSaved) {
                toast(getString(R.string.save_account_success))
                startActivity(MainActivity::class.java)
                finish()
            } else {
                toast(getString(R.string.save_account_failure))
            }
        })
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 将结果转发给 EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun checkExternalPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun requestExternalPermission() {
        EasyPermissions.requestPermissions(this, getString(R.string.request_write_external_permission), Constants.CODE_WRITE_EXTERNAL_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    @AfterPermissionGranted(Constants.CODE_WRITE_EXTERNAL_PERMISSION)
    fun saveCard() {
        MainScope().launch {
            val card = IDCardUtils.loadIDCardJson(IDCardUtils.getIDCardPath(this@SaveAccountActivity))
            mViewModel.saveCard2Album(card)
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

}