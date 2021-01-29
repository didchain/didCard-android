package com.nbs.didcard.ui.home

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import com.nbs.android.lib.base.BaseViewModel
import com.nbs.android.lib.base.ItemViewModel
import com.nbs.didcard.bean.ServiceBean
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
class ItemViewModel(viewModel: HomeViewModel,val serviceBean: ServiceBean) : ItemViewModel<HomeViewModel>(viewModel) {
}