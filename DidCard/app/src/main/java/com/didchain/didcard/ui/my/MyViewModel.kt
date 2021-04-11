package com.didchain.didcard.ui.my

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.command.BindingConsumer
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.Constants
import com.didchain.didcard.R
import com.didchain.didcard.provider.context
import com.didchain.didcard.ui.authorization.AuthorizationActivity
import com.didchain.didcard.ui.authorizationManager.AuthorizationSystemActivity
import com.didchain.didcard.ui.idmanager.IDCardManagerActivity
import com.didchain.didcard.ui.privacyauthority.PrivacyAuthorityActivity
import com.didchain.didcard.utils.EncryptedPreferencesUtils
import com.didchain.didcard.utils.SharedPref
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class MyViewModel : BaseViewModel(), KoinComponent {
    private val model: MyModel by inject()
    val did = ObservableField<String>()
    var openFingerPrint: Boolean by SharedPref(context(), Constants.KEY_OPEN_FINGERPRINT, false)
    var openNoSecret: Boolean by SharedPref(context(), Constants.KEY_OPEN_NO_SECRET, false)
    var openFingerPrintObservable: ObservableBoolean
    var openNoSecretObservable: ObservableBoolean
    val showPasswordDialogEvent = SingleLiveEvent<Boolean>()
    val dismissPasswordDialogEvent = SingleLiveEvent<Boolean>()
    val fingerPrintEvent = SingleLiveEvent<Boolean>()
    val showFingerPrintDialogEvent = SingleLiveEvent<String>()

    init {
        openFingerPrintObservable = ObservableBoolean(openFingerPrint)
        openNoSecretObservable = ObservableBoolean(openNoSecret)
        getId()
    }

    fun getId() {
        MainScope().launch {
            did.set(model.getIDCard()?.did)
        }
    }

    val clickIDCardManager = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(IDCardManagerActivity::class.java)
        }
    })

    val clickAuthorizationManagement = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(AuthorizationSystemActivity::class.java)
        }
    })

    val onCheckedFingerprint = BindingCommand(bindConsumer = object : BindingConsumer<Boolean> {
        override fun call(isChecked: Boolean) {
            openFingerPrintObservable.set(isChecked)
            if (isChecked) {
                fingerPrintEvent.postValue(isChecked)
            }
        }

    })

    val onCheckedNoSecret = BindingCommand(bindConsumer = object : BindingConsumer<Boolean> {
        override fun call(isChecked: Boolean) {
            if (isChecked) {
                showPasswordDialogEvent.postValue(isChecked)
            } else {
                openNoSecret = false
            }
            openNoSecretObservable.set(isChecked)
        }

    })

    val clickNoSecret = BindingCommand<Any>(object : BindingAction {
        override fun call() {

        }
    })

    val clickAuthorization = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(AuthorizationActivity::class.java)
        }
    })

    val clickFeedBack = BindingCommand<Any>(object : BindingAction {
        override fun call() {

        }
    })

    val clickAbout = BindingCommand<Any>(object : BindingAction {
        override fun call() {

        }
    })

    val clickHelp = BindingCommand<Any>(object : BindingAction {
        override fun call() {

        }
    })

    val clickPrivacyAuthority = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(PrivacyAuthorityActivity::class.java)
        }
    })

    fun openIdCard(password: String, isOpenNoSecret: Boolean) {
        showDialog()
        model.openIdCard(password).subscribe(object : SingleObserver<Boolean> {
            override fun onSuccess(t: Boolean) {
                dismissDialog()
                dismissPasswordDialogEvent.call()
                if (isOpenNoSecret) {
                    EncryptedPreferencesUtils(context()).putString(Constants.KEY_ENCRYPTED_PASSWORD, password)
                    openNoSecret = true
                } else {
                    showFingerPrintDialogEvent.postValue(password)
                }

            }

            override fun onSubscribe(d: Disposable) {
                addSubscribe(d)
            }

            override fun onError(e: Throwable) {
                dismissDialog()
                showErrorToast(R.string.open_error, e)
            }

        })
    }

}