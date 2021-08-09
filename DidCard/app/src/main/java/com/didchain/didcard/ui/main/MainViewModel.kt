package com.didchain.didcard.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.rxLifeScope
import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.R
import com.didchain.didcard.provider.context
import com.didchain.didcard.room.DataBaseManager
import com.didchain.didcard.ui.IDCardModel
import com.didchain.didcard.utils.IDCardUtils
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class MainViewModel : BaseViewModel(), KoinComponent {


    private val model: MainModel by inject()
    private val idCardModel: IDCardModel by inject()
    val id = ObservableField<String>()
    val openCameraEvent = SingleLiveEvent<Boolean>()
    val verifyEvent = SingleLiveEvent<Int>()
    val openIDCard = SingleLiveEvent<Boolean>()

    init {
        getId()

    }

    fun getId() {
        rxLifeScope.launch {
            id.set(IDCardUtils.getId(context()))
        }
    }

    val clickQR = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            openCameraEvent.call()
        }
    })

    fun verify(randomToken: String, authUrl: String) {
        rxLifeScope.launch {

            val accounts = DataBaseManager.queryByUrl(authUrl)
            if (accounts == null || accounts.isEmpty()) {
                showToast(R.string.no_account)
                return@launch
            }

            val currentAccount = accounts.firstOrNull { it.isUsed }
            if (currentAccount == null) {
                showToast(R.string.no_account)
                return@launch
            }
            showDialog()

            rxLifeScope.launch {
                try {
                    val result = model.verify(id.get()!!, randomToken, authUrl, currentAccount)
                    dismissDialog()
                    val resultObj = JSONObject(result)
                    val resultCode = resultObj.optInt("result_code")
                    verifyEvent.postValue(resultCode)
                } catch (e: Exception) {
                    dismissDialog()
                    showToast(R.string.verify_exception)
                }

            }
        }
    }

    fun openIdCard(password: String) {
        idCardModel.openIdCard(password).subscribe(object : SingleObserver<Boolean> {
            override fun onSuccess(t: Boolean) {
                openIDCard.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                addSubscribe(d)
            }

            override fun onError(e: Throwable) {
                showErrorToast(R.string.open_error, e)
            }

        })

    }


}