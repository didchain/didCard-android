package com.didchain.didcard.ui.my

import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.command.BindingConsumer
import com.didchain.didcard.Constants
import com.didchain.didcard.provider.context
import com.didchain.didcard.ui.authorization.AuthorizationActivity
import com.didchain.didcard.ui.idmanager.IDCardManagerActivity
import com.didchain.didcard.utils.SharedPref

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class MyViewModel : BaseViewModel() {

    var openFingerprint: Boolean by SharedPref(context(), Constants.KEY_OPEN_FINGERPRINT, false)
    var openNoScret: Boolean by SharedPref(context(), Constants.KEY_OPEN_NO_SCRET, false)

    val clickIDCardManager = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(IDCardManagerActivity::class.java)
        }
    })

    val onCheckedFingerprint = BindingCommand(bindConsumer = object : BindingConsumer<Boolean> {
        override fun call(isChecked: Boolean) {
            openFingerprint = isChecked
        }

    })

    val onCheckedNoScret = BindingCommand(bindConsumer = object : BindingConsumer<Boolean> {
        override fun call(isChecked: Boolean) {
            openNoScret = isChecked
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
}