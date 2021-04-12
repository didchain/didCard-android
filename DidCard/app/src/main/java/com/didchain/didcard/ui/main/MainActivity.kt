package com.didchain.didcard.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
import com.didchain.didcard.event.EventLoadIDCard
import com.didchain.didcard.ui.home.HomeFragment
import com.didchain.didcard.ui.my.MyFragment
import com.didchain.didcard.ui.scan.ScanActivity
import com.didchain.didcard.utils.DialogUtils
import com.didchain.didcard.utils.PermissionUtils
import com.didchain.didcard.view.PasswordPop
import com.google.zxing.integration.android.IntentIntegrator
import com.lxj.xpopup.core.BasePopupView
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

@KoinApiExtension
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private val VERIFY_SUCCESS = 0
    private val VERIFY_SIGNATURE_ERROR = 1
    private val VERIFY_NOT_FOUND = 2

    private lateinit var passwordDialog: BasePopupView
    private lateinit var randomToken: String
    private lateinit var authUrl: String
    private val fragments = arrayListOf<Fragment>()
    private val titles = arrayOf(R.string.main_home, R.string.main_my)
    private val icons = arrayOf(R.drawable.tab_home_selector, R.drawable.tab_my_selector)

    override fun getLayoutId(savedInstanceState: Bundle?): Int = R.layout.activity_main
    override val mViewModel: MainViewModel by viewModel()
    private val myFragment by inject<MyFragment>()
    private val homeFragment by inject<HomeFragment>()
    override fun initView() {
        EventBus.getDefault().register(this)

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
        mViewModel.openIDCard.observe(this, Observer {
            mViewModel.verify(randomToken, authUrl)
        })
        mViewModel.verifyEvent.observe(this, Observer {
            if (this::passwordDialog.isInitialized && passwordDialog.isShow) {
                passwordDialog.dismiss()
            }
            if (it == VERIFY_SUCCESS) {
                toast(getString(R.string.verify_success))
            } else if (it == VERIFY_SIGNATURE_ERROR) {
                toast(getString(R.string.verify_error))
            } else if (it == VERIFY_NOT_FOUND) {
                toast(getString(R.string.verify_user_not_found))
            }
        })
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
        icon.background = ResourcesCompat.getDrawable(resources, iconId, null)
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
        try {
            val qrmessage = JSONObject(result.contents)
            randomToken = qrmessage.optString("random_token")
            authUrl = qrmessage.optString("auth_url")
            if (TextUtils.isEmpty(randomToken) || TextUtils.isEmpty(authUrl)) {
                toast(getString(R.string.qr_error))
                return
            }
            if (Androidgolib.isOpen()) {
                mViewModel.verify(randomToken, authUrl)
            } else {
                showPasswordDialog()
            }
        } catch (e: Exception) {
            toast(getString(R.string.qr_error))
        }


    }

    private fun showPasswordDialog() {
        passwordDialog = DialogUtils.showPasswordDialog(this, object : PasswordPop.InputPasswordListener {
            override fun input(password: String) {
                mViewModel.openIdCard(password)
            }

        })
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun reloadIDcard(event: EventLoadIDCard) {
        mViewModel.getId()
    }

    override fun onDestroy() {
        super.onDestroy()
        Androidgolib.close()
        EventBus.getDefault().unregister(this)
    }


}