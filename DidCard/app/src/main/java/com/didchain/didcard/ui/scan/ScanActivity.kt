package com.didchain.didcard.ui.scan

import android.os.Bundle
import android.view.KeyEvent
import com.didchain.android.lib.base.BaseActivity
import com.didchain.didcard.BR
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityScanBinding
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.ViewfinderView
import kotlinx.android.synthetic.main.activity_scan.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class ScanActivity : BaseActivity<ScanViewModel, ActivityScanBinding>() {

    val capture: CaptureManager by lazy { CaptureManager(this, zxing_barcode_scanner) }
    var bundle: Bundle? = null
    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        bundle = savedInstanceState
        return R.layout.activity_scan
    }

    override val mViewModel: ScanViewModel by viewModel()

    override fun initView() {
        mViewModel.title.set(getString(R.string.import_id))
        mViewModel.showBackImage.set(true)
        mViewModel.showRightText.set(true)
        mViewModel.rightText.set(getString(R.string.import_album))
        titleLayout.setBackgroundColor(resources.getColor(R.color.white, null))
        capture.initializeFromIntent(intent, bundle)
        capture.decode()
        zxing_barcode_scanner.findViewById<ViewfinderView>(R.id.zxing_viewfinder_view)
        zxing_barcode_scanner.findViewById<BarcodeView>(R.id.zxing_barcode_surface)
    }

    override fun initData() {
    }

    override fun initObserve() {
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_WHITE

    override fun initVariableId(): Int = BR.viewModel
    //    val vap:CaptureActivity

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return zxing_barcode_scanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }


}