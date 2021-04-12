package com.didchain.didcard.ui.idcard

import android.Manifest
import android.os.Bundle
import androidx.lifecycle.Observer
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityShowIdCardBinding
import com.didchain.didcard.utils.BitmapUtils
import com.didchain.didcard.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_show_id_card.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

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

        mViewModel.idCardJsonEvent.observe(this, Observer { qrjson ->
            qrjson?.let {
                idQR.setImageBitmap(BitmapUtils.stringToQRBitmap(it))
            }
        })

        mViewModel.requestLocalPermissionEvent.observe(this, Observer {
            requestLocalMemoryPermission()
        })
    }

    override fun initVariableId(): Int = BR.viewModel
    override fun statusBarStyle(): Int = STATUSBAR_STYLE_GRAY

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

}