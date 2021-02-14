package com.didchain.android.lib.base

import androidx.lifecycle.ViewModel


open class ItemViewModel<VM : BaseViewModel>(protected var viewModel: VM):ViewModel()