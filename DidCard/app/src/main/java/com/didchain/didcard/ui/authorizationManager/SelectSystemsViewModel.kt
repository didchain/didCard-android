package com.didchain.didcard.ui.authorizationManager

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.rxLifeScope
import com.didchain.android.lib.base.BaseViewModel
import com.didchain.android.lib.command.BindingAction
import com.didchain.android.lib.command.BindingCommand
import com.didchain.android.lib.event.SingleLiveEvent
import com.didchain.didcard.R
import com.didchain.didcard.BR
import me.tatarka.bindingcollectionadapter2.ItemBinding
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class SelectSystemsViewModel:BaseViewModel(), KoinComponent {
    var finishRefreshingEvent = SingleLiveEvent<Any>()
    var finishResultActivityEvent = SingleLiveEvent<String>()
    val items: ObservableList<SelectSystemsItemViewModel> = ObservableArrayList()
    val itemBinding = ItemBinding.of<SelectSystemsItemViewModel>(BR.item, R.layout.item_system)
    val selectSystemsModel:SelectSystemsModel by inject()
    var currentUrl:String =""

    val refreshCommand = BindingCommand<Any>(object : BindingAction {
        override fun call() {
           getSystems()

        }
    })

     fun getSystems() {
        rxLifeScope.launch ({
            val systems = selectSystemsModel.getSystems()
            items.clear()
            systems.forEach {
                items.add(SelectSystemsItemViewModel(this@SelectSystemsViewModel,it,currentUrl))
            }
            finishRefreshingEvent.call()
        },{
            finishRefreshingEvent.call()
            showToast(it.message.toString())
        })
    }

    fun setResultOk(url: String) {
        finishResultActivityEvent.postValue(url)

    }
}