package com.didchain.didcard.ui.guide

import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.R
import com.didchain.didcard.provider.context
import com.didchain.didcard.ui.create.account.CreateCardActivity
import com.didchain.didcard.ui.saveaccount.SaveAccountActivity
import com.didchain.didcard.utils.CardUtils
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class GuideViewModel : BaseViewModel(), KoinComponent {
    private val model: GuideModel by inject()
    val showImportDialog = SingleLiveEvent<Boolean>()
    fun importIdCard(idCardJson: String, password: String) {
        showDialog(R.string.loading)
        model.import(idCardJson, password).subscribe(object : SingleObserver<String> {
            override fun onSuccess(t: String) {
                dismissDialog()
                saveIdCard(idCardJson)
                startActivity(SaveAccountActivity::class.java)
            }

            override fun onSubscribe(d: Disposable) {
                addSubscribe(d)
            }

            override fun onError(e: Throwable) {
                dismissDialog()
                showErrorToast(R.string.import_qr_error, e)
            }

        })

    }

    private fun saveIdCard(idCardJson: String) {
        val accountPath = CardUtils.getCardPath(context())
        CardUtils.saveCard(accountPath, idCardJson)
    }


    val clickCreate = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivityAndFinish(CreateCardActivity::class.java)
        }
    })
    val clickImport = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showImportDialog.call()
        }
    })

}