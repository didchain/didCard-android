package com.didchain.didcard.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidgolib.Androidgolib
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.didchain.android.lib.base.BaseActivity
import com.didchain.android.lib.utils.toast
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.R
import com.didchain.didcard.databinding.ActivityMainBinding
import com.didchain.didcard.ui.home.HomeFragment
import com.didchain.didcard.ui.my.MyFragment
import com.didchain.didcard.ui.scan.ScanActivity
import com.didchain.didcard.utils.PermissionUtils
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

@KoinApiExtension
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val fragments = arrayListOf<Fragment>()
    private val titles = arrayOf(R.string.main_home, R.string.main_my)
    private val icons = arrayOf(R.drawable.tab_home_selector, R.drawable.tab_my_selector)

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_main
    override val mViewModel: MainViewModel by viewModel()
    private val myFragment by inject<MyFragment>()
    private val homeFragment by inject<HomeFragment>()
    override fun initView() {
    }

    override fun initData() {
        fragments.add(homeFragment)
        fragments.add(myFragment)
        viewpager.adapter = MainPagerAdapter(supportFragmentManager, fragments)
        tablayout.setupWithViewPager(viewpager, false)
        titles.forEachIndexed { index, titleId ->
            tablayout.getTabAt(index)?.setCustomView(getItemView(index, titleId, icons[index]))
        }
    }

    override fun initObserve() {
        mViewModel.openCameraEvent.observe(this, Observer { requestCameraPermission() })
    }

    override fun statusBarStyle(): Int = STATUSBAR_STYLE_TRANSPARENT

    override fun initVariableId(): Int = BR.viewModel

    private fun getItemView(index: Int, titleId: Int, iconId: Int): View {
        val view: View = if (index == 0) {
            View.inflate(this, R.layout.item_tab_home, null)
        } else {
            View.inflate(this, R.layout.item_tab_my, null)
        }

        val icon = view.findViewById<ImageView>(R.id.icon)
        val title = view.findViewById<TextView>(R.id.title)
        title.text = getString(titleId)
        icon.background =  ResourcesCompat.getDrawable(resources,iconId,null)
        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(Constants.CODE_OPEN_CAMERA)
    fun requestCameraPermission() {
        if (PermissionUtils.hasCameraPermission(this)) {
            val ii = IntentIntegrator(this)
            ii.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            ii.captureActivity = ScanActivity::class.java
            ii.setCameraId(0)
            ii.setBarcodeImageEnabled(true)
            ii.setRequestCode(IntentIntegrator.REQUEST_CODE)
            ii.initiateScan()
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.import_apply_camera_permission), Constants.CODE_OPEN_CAMERA, Manifest.permission.CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data) ?: return
        if (result.contents == null) {
            return
        }
        toast(result.contents)
    }

    var last: Long = -1

    override fun onBackPressed() {
        val now = System.currentTimeMillis()
        if (last == -1L) {
            toast(getString(R.string.main_click_exit_application))
            last = now
        } else {
            val doubleClickDifference = 2000
            if (now - last < doubleClickDifference) {
                finish()
            } else {
                last = now
                toast(getString(R.string.main_click_exit_application))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Androidgolib.close()
    }


}