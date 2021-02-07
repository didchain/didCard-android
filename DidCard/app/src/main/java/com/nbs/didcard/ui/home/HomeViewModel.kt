package com.nbs.didcard.ui.home

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import com.nbs.android.lib.base.BaseViewModel
import com.nbs.android.lib.command.BindingAction
import com.nbs.android.lib.command.BindingCommand
import com.nbs.android.lib.event.SingleLiveEvent
import com.nbs.didcard.BR
import com.nbs.didcard.Constants
import com.nbs.didcard.DidCardApp
import com.nbs.didcard.R
import com.nbs.didcard.bean.ServiceBean
import com.nbs.didcard.provider.context
import com.nbs.didcard.utils.SharedPref
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
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
class HomeViewModel : BaseViewModel(), KoinComponent {
    private val model: HomeModel by inject()
    private val itemTitles =
        arrayListOf(R.string.home_notice, R.string.home_registered, R.string.home_hotel)
    private val itemIcons =
        arrayListOf(R.drawable.notice_icon, R.drawable.hospital_icon, R.drawable.hotel_icon)
    val showPasswordEvent = SingleLiveEvent<Boolean>()
    private var openNoScret: Boolean by SharedPref(context(), Constants.KEY_OPEN_NO_SCRET, false)
    val id = ObservableField<String>()
    val showLock = ObservableField<Boolean>(true)
    val items: ObservableList<HomeItemViewModel> = ObservableArrayList()
    val itemBinding = ItemBinding.of<HomeItemViewModel>(BR.item, R.layout.item_service)

    init {

        showLock.set(!openNoScret)
        itemTitles.forEachIndexed { index, i ->
            items.add(
                HomeItemViewModel(
                    this,
                    ServiceBean(DidCardApp.instance.getString(itemTitles[index]), itemIcons[index])
                )
            )
        }

        MainScope().launch {
            id.set(model.getId())
        }


    }

    val clickUnLock = BindingCommand<Any>(object : BindingAction {
        override fun call() {
            showPasswordEvent.call()
        }
    })
}