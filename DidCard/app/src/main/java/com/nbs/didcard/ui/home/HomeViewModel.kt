package com.nbs.didcard.ui.home

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import com.nbs.android.lib.base.BaseViewModel
import com.nbs.didcard.BR
import com.nbs.didcard.DidCardApp
import com.nbs.didcard.R
import com.nbs.didcard.bean.ServiceBean
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class HomeViewModel : BaseViewModel() {
    val id = ObservableField<String>("130423199000900934")

    val items: ObservableList<HomeItemViewModel> = ObservableArrayList()
    val itemBinding = ItemBinding.of<HomeItemViewModel>(BR.item, R.layout.item_service)

    init {

        items.add(
            HomeItemViewModel(
                this,
                ServiceBean(
                    DidCardApp.instance.getString(R.string.home_notice),
                    R.drawable.notice_icon
                )
            )
        )
        items.add(
            HomeItemViewModel(
                this,
                ServiceBean(
                    DidCardApp.instance.getString(R.string.home_registered),
                    R.drawable.hospital_icon
                )
            )
        )
        items.add(
            HomeItemViewModel(
                this,
                ServiceBean(
                    DidCardApp.instance.getString(R.string.home_hotel),
                    R.drawable.hotel_icon
                )
            )
        )
    }

}