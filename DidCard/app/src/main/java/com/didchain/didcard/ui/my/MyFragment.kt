package com.didchain.didcard.ui.my

import android.content.ComponentName
import android.content.Intent
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.didchain.android.lib.base.BaseFragment
import com.didchain.android.lib.utils.toast
import com.didchain.didcard.BR
import com.didchain.didcard.Constants
import com.didchain.didcard.R
import com.didchain.didcard.databinding.FragmentMyBinding
import com.didchain.didcard.event.EventLoadIDCard
import com.didchain.didcard.utils.CryptographyManager
import com.didchain.didcard.utils.DialogUtils
import com.didchain.didcard.utils.EncryptedPreferencesUtils
import com.didchain.didcard.utils.StringUtils
import com.didchain.didcard.view.PasswordPop
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.SimpleCallback
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {

    private lateinit var passwordDialog: BasePopupView
    val biometricManager: BiometricManager by lazy { BiometricManager.from(mActivity) }
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var cryptographyManager: CryptographyManager
    override fun getLayoutId(): Int = R.layout.fragment_my
    override val mViewModel: MyViewModel by viewModel()
    override fun initView() {
        mViewModel.title.set(getString(R.string.my))
        EventBus.getDefault().register(this)
    }

    override fun initData() {
    }

    override fun initVariableId(): Int = BR.viewModel

    override fun initObserve() {
        mViewModel.showPasswordDialogEvent.observe(this, Observer { open ->
            if (open) {
                showPasswordDialog(true)
            }
        })
        mViewModel.dismissPasswordDialogEvent.observe(this, Observer {
            if (this::passwordDialog.isInitialized && passwordDialog.isShow) {
                passwordDialog.dismiss()
            }
        })

        mViewModel.fingerPrintEvent.observe(this, object : Observer<Boolean> {
            override fun onChanged(open: Boolean?) {
                val status = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
                if (status == BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE || status == BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE) {
                    toast(getString(R.string.my_no_support_fingerprint))
                    mViewModel.openFingerPrintObservable.set(false)
                    return
                }
                if (status == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
                    showStartFingerPrintsDialog()
                    mViewModel.openFingerPrintObservable.set(false)
                    return
                }
                showPasswordDialog(false)

            }
        })


        mViewModel.showFingerPrintDialogEvent.observe(this, Observer { password ->
            cryptographyManager = CryptographyManager()
            biometricPrompt = createBiometricPrompt(password)
            promptInfo = createPromptInfo()
            try {
                val cipher = cryptographyManager.getInitializedCipherForEncryption(Constants.KEY_DID_BIOMETRIC)
                biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
            } catch (e: Exception) {

            }

        })
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder().setTitle(getString(R.string.fingerprint_recognition_title)).setSubtitle(getString(R.string.fingerprint_recognition_subtitle)).setConfirmationRequired(false).setNegativeButtonText(getString(R.string.cancel)).build()
    }

    private fun showStartFingerPrintsDialog() {
        DialogUtils.showStartFingerPrintsDialog(mActivity, OnConfirmListener {
            startFingerPrintsSetting()
        }, OnCancelListener {

        })
    }

    private fun startFingerPrintsSetting() {
        val pcgName = "com.android.settings"
        val clsName = "com.android.settings.Settings"
        val intent = Intent()
        val componentName = ComponentName(pcgName, clsName)
        intent.action = Intent.ACTION_VIEW
        intent.component = componentName
        startActivity(intent)
    }

    private fun showPasswordDialog(isOpenNoSecret: Boolean) {
        passwordDialog = DialogUtils.showPasswordDialog(mActivity, object : PasswordPop.InputPasswordListener {
            override fun input(password: String) {
                mViewModel.openIdCard(password, isOpenNoSecret)
            }

        }, object : SimpleCallback() {
            override fun onBackPressed(popupView: BasePopupView?): Boolean {
                if (isOpenNoSecret) {
                    mViewModel.openNoSecretObservable.set(!mViewModel.openNoSecretObservable.get())
                } else {
                    mViewModel.openFingerPrintObservable.set(false)
                }
                return false
            }
        })
    }

    private fun createBiometricPrompt(password: String): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(mActivity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Logger.d("$errorCode :: $errString")
                toast(errString.toString())
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON || errorCode == BiometricPrompt.ERROR_USER_CANCELED || errorCode == BiometricPrompt.ERROR_LOCKOUT) {
                    mViewModel.openFingerPrintObservable.set(false)
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Logger.d("Authentication failed for an unknown reason")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Logger.d("Authentication was successful")
                mViewModel.openFingerPrint = true
                processData(password, result.cryptoObject)
            }
        }

        return BiometricPrompt(this, executor, callback)
    }



    private fun processData(password: String, cryptoObject: BiometricPrompt.CryptoObject?) {
        val encryptedData = cryptographyManager.encryptData(password, cryptoObject?.cipher!!)
        val encryptedPreference = EncryptedPreferencesUtils(mActivity)
        encryptedPreference.putString(Constants.KEY_BIOMETRIC_PASSWORD, StringUtils.bytesToHexString(encryptedData.ciphertext))
        encryptedPreference.putString(Constants.KEY_BIOMETRIC_INITIALIZATION_VECTOR, StringUtils.bytesToHexString(encryptedData.initializationVector))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun reloadIDcard(event: EventLoadIDCard){
        mViewModel.getId()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}