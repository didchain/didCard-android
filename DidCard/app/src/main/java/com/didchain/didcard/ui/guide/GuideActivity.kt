package com.didchain.didcard.ui.guide

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import com.didchain.android.lib.base.BaseActivity
import com.didchain.android.lib.utils.toast
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityGuideBinding
import com.didchain.didcard.ui.scan.ScanActivity
import com.didchain.didcard.utils.DialogUtils
import com.didchain.didcard.utils.IDCardUtils
import com.didchain.didcard.utils.PermissionUtils
import com.didchain.didcard.view.PasswordPop
import com.google.zxing.integration.android.IntentIntegrator
import com.lxj.xpopup.interfaces.OnSelectListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class GuideActivity : BaseActivity<GuideViewModel, ActivityGuideBinding>() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_guide
    override val mViewModel: GuideViewModel by viewModel()

    override fun initView() {
//        DialogUtils.showPrivacyAuthorityDialog(this, OnConfirmListener { }, OnCancelListener { finish() })
    }

    override fun initData() {}

    override fun initObserve() {
        mViewModel.showImportDialog.observe(this, Observer {
            DialogUtils.showImportDialog(this, OnSelectListener { position, text ->
                if (DialogUtils.POSITION_ALBUM == position) {
                    requestLocalMemoryPermission()
                } else if (DialogUtils.POSITION_CAMERA == position) {
                    requestCameraPermission()
                }
            })
        })
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_TRANSPARENT

    @AfterPermissionGranted(Constants.CODE_OPEN_ALBUM)
    fun requestLocalMemoryPermission() {
        if (PermissionUtils.hasStoragePermission(this)) {
            IDCardUtils.openAlbum(this)
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.import_apply_album_permission), Constants.CODE_OPEN_ALBUM, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }


    @AfterPermissionGranted(Constants.CODE_OPEN_CAMERA)
    fun requestCameraPermission() {
        if (PermissionUtils.hasCameraPermission(this)) {
            val ii = IntentIntegrator(this)
            ii.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            ii.captureActivity = ScanActivity::class.java
            ii.setCameraId(0)
            ii.setBarcodeImageEnabled(true)
            ii.initiateScan()
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.import_apply_camera_permission), Constants.CODE_OPEN_CAMERA, Manifest.permission.CAMERA)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (Constants.CODE_OPEN_ALBUM == requestCode) {
            if (null == data) {
                return
            }
            loadIdCardFromUri(data.data)
        } else {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
                    ?: return
            if (result.contents == null) {
                return
            }
            try {
                val walletStr = result.contents
                showPasswordDialog(walletStr)
            } catch (ex: Exception) {
                toast(getString(R.string.import_qr_error) + ex.localizedMessage)
            }
        }
    }

    private fun loadIdCardFromUri(uri: Uri?) {
        if (null == uri) {
            toast(getString(R.string.import_qr_error))
            return
        }
        try {
            val walletStr = IDCardUtils.parseQRCodeFile(uri, contentResolver)
            showPasswordDialog(walletStr)
        } catch (e: Exception) {
            toast(getString(R.string.import_qr_error) + e.localizedMessage)
            e.printStackTrace()
        }
    }


    private fun showPasswordDialog(walletStr: String) {
        DialogUtils.showPasswordDialog(this, object : PasswordPop.InputPasswordListener {
            override fun input(password: String) {
                mViewModel.importIdCard(walletStr, password)
            }

        })
    }
}