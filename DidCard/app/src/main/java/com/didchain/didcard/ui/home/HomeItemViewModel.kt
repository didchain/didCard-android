package com.didchain.didcard.ui.home

import com.didchain.android.lib.base.ItemViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.didcard.R
import com.didchain.didcard.bean.ServiceBean
import org.koin.core.component.KoinApiExtension

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class HomeItemViewModel(viewModel: HomeViewModel, val serviceBean: ServiceBean) : ItemViewModel<HomeViewModel>(viewModel) {
    val clickItem = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            viewModel.showToast(R.string.developing)
        }
    })
}