package com.nbs.didcard.ui.my

import com.nbs.android.lib.base.BaseViewModel
import com.nbs.android.lib.command.BindingAction
import com.nbs.android.lib.command.BindingCommand
import com.nbs.android.lib.command.BindingConsumer
import com.nbs.didcard.Constants
import com.nbs.didcard.provider.context
import com.nbs.didcard.ui.authorization.AuthorizationActivity
import com.nbs.didcard.ui.idmanager.IDCardManagerActivity
import com.nbs.didcard.utils.SharedPref

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