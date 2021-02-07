package com.nbs.didcard.ui.home

import com.nbs.android.lib.base.ItemViewModel
import com.nbs.didcard.bean.ServiceBean
import org.koin.core.component.KoinApiExtension

/**
 *Author:Mr'x
 *Time:
 *Description:
 */
@KoinApiExtension
class HomeItemViewModel(viewModel: HomeViewModel, val serviceBean: ServiceBean) :
    ItemViewModel<HomeViewModel>(viewModel)