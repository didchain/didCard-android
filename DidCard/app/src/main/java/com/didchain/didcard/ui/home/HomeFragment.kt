package com.didchain.didcard.ui.home

import android.Manifest
import android.content.Intent
import android.provider.Settings
import android.view.View
import androidgolib.Androidgolib
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.didchain.android.lib.base.BaseFragment
import com.didchain.android.lib.utils.toast
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.DidCardApp
import com.didchain.didcard.R
import com.didchain.didcard.bean.QRBean
import com.didchain.didcard.bean.SignatureBean
import com.didchain.didcard.databinding.FragmentHomeBinding
import com.didchain.didcard.event.EventLoadIDCard
import com.didchain.didcard.utils.*
import com.didchain.didcard.view.PasswordPop
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() , EasyPermissions.PermissionCallbacks{

    private lateinit var popupView: BasePopupView
    private lateinit var passwordDialog: BasePopupView
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var cryptographyManager: CryptographyManager

    //初始化定位
    private var locationClient = AMapLocationClient(DidCardApp.instance)
    lateinit var mMapLocation: AMapLocation
    private val locationOption: AMapLocationClientOption = AMapLocationClientOption()

    //声明定位回调监听器
    private var mLocationListener = AMapLocationListener { mapLocation ->
        if (mapLocation != null) {
            if (mapLocation.errorCode == 0) {
                if (this::mMapLocation.isInitialized) {
                    return@AMapLocationListener
                }

                mViewModel.city.set(mapLocation.city)

                val currentTimeMillis = System.currentTimeMillis()
                val signature = getSignature(currentTimeMillis, mapLocation.latitude, mapLocation.longitude)

                val signatureJson = JsonUtils.object2Json(signature, SignatureBean::class.java)
                if (Androidgolib.isOpen()) {
                    val sign = Androidgolib.sign(signatureJson)
                    val qrBean = QRBean(sign, mViewModel.id.get().toString(), currentTimeMillis, mapLocation.latitude, mapLocation.longitude)
                    val qrjson = JsonUtils.object2Json(qrBean, QRBean::class.java)
                    rq.setImageBitmap(BitmapUtils.stringToQRBitmap(qrjson))
                    progress.visibility= View.GONE
                    mMapLocation = mapLocation
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Logger.e( "location Error, ErrCode:" + mapLocation.errorCode + ", errInfo:" + mapLocation.errorInfo)
            }
        }
    }

    private fun getSignature(currentTimeMillis: Long, latitude: Double, longitude: Double): SignatureBean {
        return SignatureBean(mViewModel.id.get().toString(), currentTimeMillis, latitude, longitude)
    }

    override fun getLayoutId(): Int = R.layout.fragment_home
    override val mViewModel: HomeViewModel by viewModel()

    override fun initView() {
        mViewModel.title.set(getString(R.string.app_name))
        EventBus.getDefault().register(this)
    }

    override fun initData() {
        if (mViewModel.openNoSecret) {
            val password = EncryptedPreferencesUtils(mActivity).getString(Constants.KEY_ENCRYPTED_PASSWORD, "")
            mViewModel.openIdCard(password)
        } else if (mViewModel.openFingerPrint) {
            cryptographyManager = CryptographyManager()
            val encryptedPreference = EncryptedPreferencesUtils(mActivity)
            val encryptedPassword = encryptedPreference.getString(Constants.KEY_BIOMETRIC_PASSWORD, "")
            val encryptedVector = encryptedPreference.getString(Constants.KEY_BIOMETRIC_INITIALIZATION_VECTOR, "")
            biometricPrompt = createBiometricPrompt(encryptedPassword)
            promptInfo = createPromptInfo()
            try {
                val cipher = cryptographyManager.getInitializedCipherForDecryption(Constants.KEY_DID_BIOMETRIC, StringUtils.hexStringToByte(encryptedVector))
                biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
            } catch (e: Exception) {
                Logger.d("initData: ${e.message}")
                mViewModel.openFingerPrint = false

            }

        }

//        initLocation()
        //设置定位回调监听
//        locationClient.setLocationListener(mLocationListener)
//        if (!checkLocationPermission()) {
//            requestExternalPermission()
//        } else {
//            getLocation()
//        }


    }

    private fun showQR(){
        val currentTimeMillis = System.currentTimeMillis()
        val signature = getSignature(currentTimeMillis, 12.22222, 22.33333)

        val signatureJson = JsonUtils.object2Json(signature, SignatureBean::class.java)
        if (Androidgolib.isOpen()) {
            val sign = Androidgolib.sign(signatureJson)
            val qrBean = QRBean(sign, mViewModel.id.get().toString(), currentTimeMillis, 12.22222, 22.33333)
            val qrjson = JsonUtils.object2Json(qrBean, QRBean::class.java)
            rq.setImageBitmap(BitmapUtils.stringToQRBitmap(qrjson))
            progress.visibility= View.GONE
        }
    }

    private fun initLocation() {

        locationOption.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        locationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Device_Sensors
        locationOption.isNeedAddress = true
        locationOption.httpTimeOut = 20000
        locationOption.isOnceLocation = false
        locationOption.interval = 1000
        locationClient.setLocationOption(locationOption)

    }

    override fun initVariableId(): Int = BR.viewModel

    override fun initObserve() {
        mViewModel.showPasswordEvent.observe(this, Observer {
            showPasswordDialog()
        })

        mViewModel.showQREvent.observe(this, Observer {
            showQR()
        })

        mViewModel.dismissPasswordEvent.observe(this, Observer {
            if (this::passwordDialog.isInitialized && passwordDialog.isShow) {
                passwordDialog.dismiss()
                showQR()
            }
        })
    }

    override fun onResume() {
        super.onResume()
//        if (!isLocationEnabled()) {
//            toOpenGPS()
//        }

    }

    private fun createBiometricPrompt(encryptedPassword: String): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(mActivity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    showPasswordDialog()
                }
                toast(errString.toString())
                Logger.d("$errorCode :: $errString")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Logger.d("Authentication failed for an unknown reason")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Logger.d("Authentication was successful")
                processData(encryptedPassword, result.cryptoObject)
            }

        }

        return BiometricPrompt(this, executor, callback)
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder().setTitle(getString(R.string.fingerprint_recognition_title)).setSubtitle(getString(R.string.fingerprint_recognition_subtitle))
                //            .setDescription(getString(R.string.prompt_info_description))
                .setConfirmationRequired(false).setNegativeButtonText(getString(R.string.fingerprint_recognition_use_password))
                //            .setDeviceCredentialAllowed(true)
                .build()
    }


    private fun processData(encryptedPassword: String, cryptoObject: BiometricPrompt.CryptoObject?) {
        val password = cryptographyManager.decryptData(StringUtils.hexStringToByte(encryptedPassword), cryptoObject?.cipher!!)
        mViewModel.openIdCard(password)
    }

    private fun showPasswordDialog() {
        passwordDialog = DialogUtils.showPasswordDialog(mActivity, object : PasswordPop.InputPasswordListener {

            override fun input(password: String) {
                mViewModel.openIdCard(password)
            }

        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun reloadIDcard(event: EventLoadIDCard){
        mViewModel.initData()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).setTitle(getString(R.string.tip)).setRationale(R.string.refuse_location_permission).setPositiveButton(R.string.home_go_open).setNegativeButton(R.string.cancel).setRequestCode(Constants.CODE_LOCATION_PERMISSION).build().show()
        } else {
            toast(getString(R.string.refuse_location_permission))
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 将结果转发给 EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun checkLocationPermission(): Boolean {
        return EasyPermissions.hasPermissions(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun requestExternalPermission() {
        EasyPermissions.requestPermissions(this, getString(R.string.request_wlocation_permission), Constants.CODE_LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @AfterPermissionGranted(Constants.CODE_LOCATION_PERMISSION)
    fun getLocation() {
        locationClient.startLocation()
    }

    private fun isLocationEnabled(): Boolean {
        val locationMode = try {
            Settings.Secure.getInt(mActivity.contentResolver, Settings.Secure.LOCATION_MODE)
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
            return false
        }
        return locationMode != Settings.Secure.LOCATION_MODE_OFF
    }


    private fun toOpenGPS() {
        if (this::popupView.isInitialized && popupView.isShow) {
            return
        }
        popupView = XPopup.Builder(context).dismissOnBackPressed(true).dismissOnTouchOutside(true).hasNavigationBar(false).isDestroyOnDismiss(true).asConfirm(getString(R.string.tip), getString(R.string.home_no_location), getString(R.string.cancel), getString(R.string.home_go_open), OnConfirmListener {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }, null, false)
        popupView.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            requestExternalPermission()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        locationClient.stopLocation()
        locationClient.onDestroy()
    }
}