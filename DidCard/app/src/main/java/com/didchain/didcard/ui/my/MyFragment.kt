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
import com.didchain.didcard.utils.CryptographyManager
import com.didchain.didcard.utils.DialogUtils
import com.didchain.didcard.utils.EncryptedPreference
import com.didchain.didcard.utils.StringUtils
import com.didchain.didcard.view.PasswordPop
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.SimpleCallback
import com.orhanobut.logger.Logger
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {

    private lateinit var passwordDialog: BasePopupView
    val biometricManager: BiometricManager by lazy {
        BiometricManager.from(mActivity)
    }
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var cryptographyManager: CryptographyManager
    override fun getLayoutId(): Int = R.layout.fragment_my
    override val mViewModel: MyViewModel by viewModel()
    override fun initView() {
        mViewModel.title.set(getString(R.string.my))
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
                val status =
                    biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
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


        mViewModel.showfingerPrintDialogEvent.observe(this, Observer { password ->
            cryptographyManager = CryptographyManager()
            biometricPrompt = createBiometricPrompt(password)
            promptInfo = createPromptInfo()
            val secretKeyName = String.format(Constants.KEY_BIOMETRIC_BY_ID, mViewModel.id)
            val cipher = cryptographyManager.getInitializedCipherForEncryption(secretKeyName)
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        })
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.fingerprint_recognition_title))
            .setSubtitle(getString(R.string.fingerprint_recognition_subtitle))
            //            .setDescription(getString(R.string.prompt_info_description))
            .setConfirmationRequired(false).setNegativeButtonText(getString(R.string.cancel))
            //            .setDeviceCredentialAllowed(true)
            .build()
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

    private fun showPasswordDialog(isOpenNoScret: Boolean) {
        passwordDialog =
            DialogUtils.showPasswordDialog(mActivity, object : PasswordPop.InputPasswordListener {
                override fun input(password: String) {
                    mViewModel.openIdCard(password, isOpenNoScret)
                }

            }, object : SimpleCallback() {
                override fun onBackPressed(popupView: BasePopupView?): Boolean {
                    if (isOpenNoScret) {
                        mViewModel.openNoScretObservable.set(!mViewModel.openNoScretObservable.get())
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
                Logger.d(TAG, "$errorCode :: $errString")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Logger.d(TAG, "Authentication failed for an unknown reason")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Logger.d(TAG, "Authentication was successful")
                mViewModel.openFingerPrint = true
                processData(password, result.cryptoObject)
            }
        }

        return BiometricPrompt(this, executor, callback)
    }

    private fun processData(password: String, cryptoObject: BiometricPrompt.CryptoObject?) {
        val encryptedData = cryptographyManager.encryptData(password, cryptoObject?.cipher!!)
        val encryptedPreference = EncryptedPreference(mActivity)
        encryptedPreference.putString(
            Constants.KEY_BIOMETRIC_PASSWORD,
            StringUtils.bytesToHexString(encryptedData.ciphertext)
        )
        encryptedPreference.putString(
            Constants.KEY_BIOMETRIC_INITIALIZATIONVECTOR,
            StringUtils.bytesToHexString(encryptedData.initializationVector)
        )
    }

}