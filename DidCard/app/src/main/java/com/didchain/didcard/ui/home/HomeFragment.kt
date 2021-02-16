package com.didchain.didcard.ui.home

import android.Manifest
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.Observer
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.didchain.android.lib.base.BaseFragment
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.DidCardApp
import com.didchain.didcard.R
import com.didchain.didcard.bean.SignatureBean
import com.didchain.didcard.databinding.FragmentHomeBinding
import com.didchain.didcard.provider.context
import com.didchain.didcard.utils.DialogUtils
import com.didchain.didcard.utils.EncryptedPreference
import com.didchain.didcard.utils.JsonUtils
import com.didchain.didcard.view.PasswordPop
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnConfirmListener
import kotlinx.android.synthetic.main.fragment_home.*
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
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    lateinit var popupView: BasePopupView
    lateinit var passwordDialog: BasePopupView

    //初始化定位
    private var locationClient = AMapLocationClient(DidCardApp.instance)
    private val locationOption: AMapLocationClientOption = AMapLocationClientOption()

    //声明定位回调监听器
    private var mLocationListener = AMapLocationListener { amapLocation ->
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                mViewModel.city.set(amapLocation.city)

                val currentTimeMillis = System.currentTimeMillis()
                val signature =
                    getSignature(currentTimeMillis, amapLocation.latitude, amapLocation.longitude)

                val signatureJson = JsonUtils.object2Json(signature, SignatureBean::class.java)
                // Log.d("~~~~", toJson)
                //                val sign = String(Androidgolib.sign(signatureJson))
                //                val qrBean = QRBean(
                //                    currentTimeMillis,
                //                    amapLocation.latitude,
                //                    amapLocation.longitude,
                //                    sign,
                //                    mViewModel.id.get().toString()
                //                )
                //                val qrjson = JsonUtils.object2Json(qrBean, QRBean::class.java)
                //                rq.setImageBitmap(BitmapUtils.stringToQRBitmap(qrjson))
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e(
                    "AmapError",
                    "location Error, ErrCode:" + amapLocation.errorCode + ", errInfo:" + amapLocation.errorInfo
                )
            }
        }
    }

    private fun getSignature(
        currentTimeMillis: Long, latitude: Double, longitude: Double
    ): SignatureBean {
        return SignatureBean(currentTimeMillis, latitude, longitude, mViewModel.id.get().toString())
    }

    override fun getLayoutId(): Int = R.layout.fragment_home
    override val mViewModel: HomeViewModel by viewModel()

    override fun initView() {
        mViewModel.title.set(getString(R.string.app_name))
        mViewModel.city.set(getString(R.string.home_location))
        rq.setLineColor(resources.getColor(R.color.color_0c123d, null))
        Log.d("~~~~~~", EncryptedPreference(context()).getString(Constants.KEY_ENCRYPTED_PASSWORD,""))
    }

    override fun initData() {
        initLocation()
        //设置定位回调监听
        locationClient.setLocationListener(mLocationListener)
        if (!checkLocationPermission()) {
            requestExternalPermission()
        } else {
            getLocation()
        }
    }

    private fun initLocation() {

        locationOption.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        locationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        locationOption.isNeedAddress = true
        locationOption.httpTimeOut = 20000

        locationClient.setLocationOption(locationOption)


    }

    override fun initVariableId(): Int = BR.viewModel

    override fun initObserve() {
        mViewModel.showPasswordEvent.observe(this, Observer {
            showPasswordDialog()
        })

        mViewModel.dismissPasswordEvent.observe(this, Observer {
            if (this::passwordDialog.isInitialized && passwordDialog.isShow) {
                passwordDialog.dismiss()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (!isLocationEnabled()) {
            toOpenGPS()
        }

    }

    private fun showPasswordDialog() {
        passwordDialog =
            DialogUtils.showPasswordDialog(mActivity, object : PasswordPop.InputPasswordListener {

                override fun input(password: String) {
                    mViewModel.openIdCard(password)
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

    private fun checkLocationPermission(): Boolean {
        return EasyPermissions.hasPermissions(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun requestExternalPermission() {
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.request_wlocation_permission),
            Constants.CODE_LOCATION_PERMISSION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    @AfterPermissionGranted(Constants.CODE_LOCATION_PERMISSION)
    fun getLocation() {
        locationClient.startLocation()
    }

    fun isLocationEnabled(): Boolean {
        val locationMode = try {
            Settings.Secure.getInt(
                mActivity.contentResolver, Settings.Secure.LOCATION_MODE
            )
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            return false
        }
        return locationMode != Settings.Secure.LOCATION_MODE_OFF
    }


    fun toOpenGPS() {
        if (this::popupView.isInitialized && popupView.isShow) {
            return
        }
        popupView = XPopup.Builder(context).dismissOnBackPressed(true).dismissOnTouchOutside(true)
            .hasNavigationBar(false).isDestroyOnDismiss(true).asConfirm(
                getString(R.string.tip),
                getString(R.string.home_no_location),
                getString(R.string.cancel),
                getString(R.string.home_go_open),
                OnConfirmListener {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    mActivity.startActivity(intent)
                },
                null,
                false
            )
        popupView.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.onDestroy()
    }
}