package com.nbs.didcard.ui.scan

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.ViewfinderView
import com.nbs.android.lib.base.BaseActivity
import com.nbs.didcard.R
import com.nbs.didcard.BR
import com.nbs.didcard.databinding.ActivityScanBinding
import kotlinx.android.synthetic.main.activity_scan.*

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class ScanActivity : BaseActivity<ScanViewModel, ActivityScanBinding>() {
    lateinit var capture: CaptureManager
    override fun getLayoutId(): Int = R.layout.activity_scan
    var bundle :Bundle?=null

    override fun onContentViewBefor(savedInstanceState: Bundle?) {
        super.onContentViewBefor(savedInstanceState)
        bundle = savedInstanceState
    }
    override fun initView() {
        mViewModel.title.set(getString(R.string.import_id))
        mViewModel.showBackImage.set(true)
        mViewModel.showRightText.set(true)
        mViewModel.rightText.set(getString(R.string.import_album))
        title_layout.setBackgroundColor(resources.getColor(R.color.white,null))
        capture = CaptureManager(this, zxing_barcode_scanner)
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


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return zxing_barcode_scanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    override val mViewModel: ScanViewModel
        get() = createViewModel(ScanViewModel::class.java)
}