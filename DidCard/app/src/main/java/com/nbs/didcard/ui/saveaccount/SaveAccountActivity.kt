package com.nbs.didcard.ui.saveaccount

import android.Manifest
import android.os.Bundle
import androidx.lifecycle.Observer
import com.nbs.android.lib.base.BaseActivity
import com.nbs.android.lib.utils.toast
import com.nbs.didcard.BR
import com.nbs.didcard.Constants
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivitySaveAccountBinding
import com.nbs.didcard.ui.main.MainActivity
import com.nbs.didcard.utils.CardUtils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class SaveAccountActivity : BaseActivity<SaveAccountViewModel, ActivitySaveAccountBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_save_account
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_GRAY
    override fun initVariableId(): Int = BR.viewModel
    override val mViewModel: SaveAccountViewModel by viewModel()

    override fun initView() {
    }

    override fun initData() {}

    override fun initObserve() {
        mViewModel.saveAlbumEvent.observe(this, Observer<Any> {
            if (!checkExternalPermission()) {
                requestExternalPermission()
            } else {
                saveCard()

            }
        })

        mViewModel.saveAlbumResultEvent.observe(this, Observer<Boolean> { isSaved ->
            if (isSaved) {
                toast(getString(R.string.save_account_success))
                startActivity(MainActivity::class.java)
                finish()
            } else {
                toast(getString(R.string.save_account_failure))
            }
        })
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 将结果转发给 EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun checkExternalPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun requestExternalPermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.request_write_external_permission),
            Constants.WRITE_EXTERNAL_PERMISSION_CODE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    @AfterPermissionGranted(Constants.WRITE_EXTERNAL_PERMISSION_CODE)
    fun saveCard() {
        MainScope().launch {
            val card = CardUtils.loadCardByPath(CardUtils.getCardPath(this@SaveAccountActivity))
            mViewModel.saveCard2Album(card)
        }

    }


}