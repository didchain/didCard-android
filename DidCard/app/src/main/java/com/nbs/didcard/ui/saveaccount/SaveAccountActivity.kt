package com.nbs.didcard.ui.saveaccount

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.Observer
import com.nbs.android.lib.base.BaseActivity
import com.nbs.android.lib.utils.toast
import com.nbs.didcard.BR
import com.nbs.didcard.Constants
import com.nbs.didcard.R
import com.nbs.didcard.databinding.ActivitySaveAccountBinding
import org.koin.android.ext.android.inject
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
    override val mViewModel: SaveAccountViewModel by inject()

    override fun initView() {
    }

    override fun initData() {}

    override fun initObserve() {
        mViewModel.saveAlbumEvent.observe(this, Observer<Any> {
            if (!checkExternalPermission()) {
                requestExternalPermission()
            } else {
//                BitmapUtils.saveBitmapToAlbum(this,BitmapUtils.stringToQRBitmap("111111"),"hahah")
                toast("授权了写权限")
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 1)
            }
        })
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
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
    fun saveAccount() {
        //        mViewModel.saveAlbum
        toast("授权了写权限")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("!!!", "onDestroy: ")
    }


}