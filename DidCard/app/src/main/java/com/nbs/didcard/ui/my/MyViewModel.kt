package com.nbs.didcard.ui.my

import com.nbs.android.lib.base.BaseViewModel
import com.nbs.android.lib.command.BindingAction
import com.nbs.android.lib.command.BindingCommand
import com.nbs.didcard.ui.idmanager.IDCardManagerActivity

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class MyViewModel : BaseViewModel() {

    val clickIDCardManager = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            startActivity(IDCardManagerActivity::class.java)
        }
    })

    val clickNoSecret = BindingCommand<Any>(object : BindingAction {
        override fun call() {

        }
    })

    val clickCustomerService = BindingCommand<Any>(object : BindingAction {
        override fun call() {

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